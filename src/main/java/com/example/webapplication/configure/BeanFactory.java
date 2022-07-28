package com.example.webapplication.configure;

import com.example.webapplication.model.entity.Bank;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class BeanFactory {
    private static final String CURRATE_URL = "https://currate.ru";

    @Bean
    public RestTemplate currateRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(CURRATE_URL));
        return restTemplate;
    }

    @Bean(destroyMethod = "close")
    public CacheManager simpleCacheManager() {
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("bankCache",
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, Bank.class,
                                        ResourcePoolsBuilder.heap(50))
                                .build()).build(true);

        return cacheManager;
    }

    @Bean
    public Cache<Long, Bank> simpleBankCache() {
        Cache<Long, Bank> bankCache = simpleCacheManager().getCache("bankCache", Long.class, Bank.class);

        return bankCache;
    }
}
