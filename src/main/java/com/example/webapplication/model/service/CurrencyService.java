package com.example.webapplication.model.service;

import com.example.webapplication.model.entity.Currency;

import java.util.List;

public interface CurrencyService {
    List<Currency> getAll();

    void updateAll(List<Currency> currencies);

    void updateCurrencyScheduled();
}
