package com.example.webapplication.model.service.impl;

import com.example.webapplication.model.entity.Bank;
import com.example.webapplication.model.repository.BanksRepository;
import com.example.webapplication.model.service.CustomCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("bankDistributedCache")
public class BankDistributedCacheServiceImpl implements CustomCacheService<Long, Bank> {
    private static final String KEY = "Bank";
    private RedisTemplate<String, Object> redisTemplate;
    private HashOperations hashOperations;
    private BanksRepository banksRepository;

    @Autowired
    public BankDistributedCacheServiceImpl(RedisTemplate<String, Object> redisTemplate, BanksRepository banksRepository) {
        this.redisTemplate = redisTemplate;
        this.banksRepository = banksRepository;
    }

    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
        List<Bank> banks = (List) banksRepository.findAll();
        for(int i = 0; i < banks.size(); i++) {
            Bank bank = banks.get(i);
            add(bank.getId(), bank);
        }
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

    @Override
    public List<Bank> getAll() {
        return hashOperations.values(KEY);
    }
}
