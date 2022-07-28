package com.example.webapplication.model.service.impl;

import com.example.webapplication.model.entity.Bank;
import com.example.webapplication.model.service.CustomCacheService;
import org.ehcache.Cache;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("bankInMemoryCache")
public class BankInMemoryCacheServiceImpl implements CustomCacheService<Long, Bank> {
    private Cache<Long, Bank> cache;

    public BankInMemoryCacheServiceImpl(@Qualifier("simpleBankCache") Cache<Long, Bank> cache) {
        this.cache = cache;
    }

    @PostConstruct
    private void init() {
        List<Bank> banks = new ArrayList<>();
        for(int i = 0; i < banks.size(); i++) {
            Bank bank = banks.get(i);
            add(bank.getId(), bank);
        }
    }

    @Override
    public void add(Long id, Bank bank) {
        cache.putIfAbsent(id, bank);
    }

    @Override
    public void remove(Long id) {
        if(cache.containsKey(id)) {
            cache.remove(id);
        }
    }

    @Override
    public Optional<Bank> find(Long id) {
        Bank bank = null;
        if(cache.containsKey(id)) {
            bank = cache.get(id);
        }

        return Optional.ofNullable(bank);
    }

    @Override
    public List<Bank> getAll() {
        throw new UnsupportedOperationException();
    }
}
