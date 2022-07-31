package com.example.webapplication.model.repository;

import com.example.webapplication.model.entity.UserRole;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<UserRole, Long> {
}
