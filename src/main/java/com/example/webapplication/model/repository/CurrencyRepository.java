package com.example.webapplication.model.repository;

import com.example.webapplication.model.entity.Currency;
import org.springframework.data.repository.CrudRepository;

public interface CurrencyRepository extends CrudRepository<Currency, Long> {
}
