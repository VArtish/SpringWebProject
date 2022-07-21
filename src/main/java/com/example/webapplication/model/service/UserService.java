package com.example.webapplication.model.service;

import com.example.webapplication.exception.WrongAuthorizationException;
import com.example.webapplication.model.dto.UserSignUpDto;
import com.example.webapplication.model.entity.CustomUser;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Optional;

public interface UserService {
    boolean saveUser(UserSignUpDto user, Errors errors) throws WrongAuthorizationException;

    Optional<CustomUser> getUserByLogin(String login);

    Optional<CustomUser> getUserById(Long userId);

    List<CustomUser> getAllUser();

}
