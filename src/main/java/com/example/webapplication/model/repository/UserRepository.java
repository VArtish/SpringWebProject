package com.example.webapplication.model.repository;

import com.example.webapplication.model.entity.CustomUser;
import io.micrometer.core.annotation.Timed;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<CustomUser, Long> {
    @Timed
    CustomUser findByUsername(String username);
}
