package com.example.webapplication.model.mapper;

import com.example.webapplication.model.entity.Currency;
import com.example.webapplication.model.entity.type.CurrencyType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CurrencyJsonMapper {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static List<Currency> map(String json) throws JsonProcessingException {
        JsonNode tree = mapper.readTree(json);
        JsonNode dataNode = tree.get("data");
        Iterator<String> fields = dataNode.fieldNames();
        List<Currency> currencies = new ArrayList<>();

        while (fields.hasNext()) {
            Currency currency = new Currency();
            String currencyType = fields.next();
            String currencyName = currencyType.substring(0, 3);
            String currencyPriceName = currencyType.substring(3, 6);
            Double currencyPrice = Double.valueOf(dataNode.get(currencyType).asText());
            currency.setPrice(currencyPrice);
            currency.setType(CurrencyType.valueOf(currencyName));
            currency.setPriceType(CurrencyType.valueOf(currencyPriceName));
            currencies.add(currency);
        }

        return currencies;
    }
}
