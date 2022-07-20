package com.example.webapplication.model.mapper;

import com.example.webapplication.model.dto.UserSignUpDto;
import com.example.webapplication.model.entity.CustomUser;

public class UserSignUpDtoMapper {
    public static CustomUser mapDto(UserSignUpDto userDto) {
        String username = userDto.getUsername();
        String password = userDto.getPassword();
        String email = userDto.getEmail();

        CustomUser user = new CustomUser();
        user.setEmail(email);
        user.setPassword(password);
        user.setUsername(username);

        return user;
    }
}
