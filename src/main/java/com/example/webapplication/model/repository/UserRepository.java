package com.example.webapplication.model.repository;

import com.example.webapplication.model.entity.CustomUser;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<CustomUser, Long> {
    CustomUser findByUsername(String username);
}
