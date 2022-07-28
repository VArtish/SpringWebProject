package com.example.webapplication.controller;

import com.example.webapplication.model.entity.Bank;
import com.example.webapplication.model.service.CustomCacheService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/bank")
public class BankController {
    private CustomCacheService<Long, Bank> inMemoryCache;
    private CustomCacheService<Long, Bank> distributedCache;

    public BankController(@Qualifier("bankInMemoryCache") CustomCacheService<Long, Bank> inMemoryCache, @Qualifier("bankDistributedCache") CustomCacheService<Long, Bank> distributedCache) {
        this.inMemoryCache = inMemoryCache;
        this.distributedCache = distributedCache;
    }

    @GetMapping
    public String bank() {
        return "bank";
    }

    @GetMapping("/distributed")
    public String distributed(Model model) {
        List<Bank> banks = distributedCache.getAll();
        model.addAttribute("banks", banks);
        return "distributed";
    }

    @GetMapping("/inMemory")
    public String inMemory(Model model) {
        return "in_memory";
    }
}
