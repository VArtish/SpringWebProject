package com.example.webapplication.model.service.impl;

import com.example.webapplication.model.entity.Currency;
import com.example.webapplication.model.entity.type.CurrencyType;
import com.example.webapplication.model.mapper.CurrencyJsonMapper;
import com.example.webapplication.model.repository.CurrencyRepository;
import com.example.webapplication.model.service.CurrencyService;
import com.example.webapplication.model.service.RestTemplateService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.example.webapplication.model.entity.RestTemplateParameterData.*;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    private CurrencyRepository currencyRepository;
    private RestTemplateService restTemplateService;

    @Autowired
    public CurrencyServiceImpl(CurrencyRepository currencyRepository, RestTemplateService restTemplateService) {
        this.currencyRepository = currencyRepository;
        this.restTemplateService = restTemplateService;
    }

    @Override
    public List<Currency> getAll() {
        List<Currency> currencies = (List<Currency>) currencyRepository.findAll();
        return currencies;
    }

    @Override
    public void updateAll(List<Currency> currencies) {
        currencyRepository.saveAll(currencies);
    }

    @Override
    @Scheduled(cron = "0 0/30 * * * *")
    @Timed
    public void updateCurrencyScheduled() {
        List<Currency> currencies = (List<Currency>) currencyRepository.findAll();
        Map<String, String> parameters = formParameters();
        String urn = restTemplateService.buildUrn(PATH_CURRATE, parameters);
        String json = restTemplateService.sendRequest(urn);
        try {
            List<Currency> updateCurrenciesJson = CurrencyJsonMapper.map(json);
            updateCurrencyByJson(currencies, updateCurrenciesJson);
        } catch(JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void updateCurrencyByJson(List<Currency> currencies, List<Currency> updateCurrenciesJson) {
        for(Currency currentCurrency: currencies) {
            Optional<Currency> optionalCurrency = updateCurrenciesJson.stream()
                    .filter(c -> c.getType().getRoleId().equals(currentCurrency.getType().getRoleId()))
                    .findFirst();

            if(optionalCurrency.isPresent()) {
                Currency jsonCurrency = optionalCurrency.get();
                currentCurrency.setPrice(jsonCurrency.getPrice());
            }
        }

        currencyRepository.saveAll(currencies);
    }

    private Map<String, String> formParameters() {
        List<CurrencyType> currencyTypes = Arrays.stream(CurrencyType.values()).toList();
        Map<String, String> parameters = new HashMap<>();
        StringBuilder parameter = new StringBuilder();
        for(int i = 0; i < currencyTypes.size(); i++) {
            CurrencyType currencyType = currencyTypes.get(i);
            parameter.append(currencyType.name().toUpperCase(Locale.ROOT));
            parameter.append(USD);
            if(i != currencyTypes.size() - 1) {
                parameter.append(",");
            }
        }
        parameters.put(PAIRS, parameter.toString());
        parameters.put(GET, RATES);
        parameters.put(KEY, API_CURRATE_KEY);

        return parameters;
    }
}
