package com.example.webapplication.model.service.impl;

import com.example.webapplication.model.service.UserService;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OAuth2UserService extends DefaultOAuth2UserService {
    private final UserService userService;

    public OAuth2UserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User =  super.loadUser(oAuth2UserRequest);
        Optional<String> email = oAuth2User.getAttribute("email");
        if(email.isEmpty()) {
            saveUserInfo(oAuth2User);
        }

        return oAuth2User;
    }

    private void saveUserInfo(OAuth2User oAuth2User) {
        //сохранить username
    }
}