package com.example.webapplication.model.mapper;

import com.example.webapplication.model.dto.UserSignInDto;
import com.example.webapplication.model.entity.CustomUser;

public class UserSignInDtoMapper {
    public static CustomUser mapDto(UserSignInDto userDto) {
        String username = userDto.getUsername();
        String password = userDto.getPassword();

        CustomUser user = new CustomUser();
        user.setPassword(password);
        user.setUsername(username);

        return user;
    }
}
