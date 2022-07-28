package com.example.webapplication.model.service.impl;

import com.example.webapplication.model.entity.Bank;
import com.example.webapplication.model.service.CustomCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
public class BankDistributedCacheServiceImpl implements CustomCacheService<Long, Bank> {
    private static final String KEY = "Bank";
    private RedisTemplate<String, Object> redisTemplate;
    private HashOperations hashOperations;
    @Autowired
    public BankDistributedCacheServiceImpl(RedisTemplate<String, Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }
    @PostConstruct
    private void init(){
        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void add(Long id, Bank bank) {
        hashOperations.put(KEY, bank.getId(), bank);
    }

    @Override
    public void remove(Long id) {
        hashOperations.delete(KEY, id);
    }

    @Override
    public Optional<Bank> find(Long id) {
        Bank bank = (Bank) hashOperations.get(KEY, id);
        return Optional.ofNullable(bank);
    }
}
