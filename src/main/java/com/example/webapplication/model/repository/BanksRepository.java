package com.example.webapplication.model.repository;

import com.example.webapplication.model.entity.Bank;
import org.springframework.data.repository.CrudRepository;

public interface BanksRepository extends CrudRepository<Bank, Long> {
}
