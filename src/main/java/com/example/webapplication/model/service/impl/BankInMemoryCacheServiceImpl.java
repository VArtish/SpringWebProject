package com.example.webapplication.model.service.impl;

import com.example.webapplication.model.entity.Bank;
import com.example.webapplication.model.service.CustomCacheService;
import org.ehcache.Cache;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BankInMemoryCacheServiceImpl implements CustomCacheService<Long, Bank> {
    private Cache<Long, Bank> cache;

    public BankInMemoryCacheServiceImpl(@Qualifier("simpleBankCache") Cache<Long, Bank> cache) {
        this.cache = cache;
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
}
