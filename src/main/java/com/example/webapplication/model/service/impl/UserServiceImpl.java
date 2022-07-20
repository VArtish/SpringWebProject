package com.example.webapplication.model.service.impl;

import com.example.webapplication.exception.WrongAuthorizationException;
import com.example.webapplication.model.dto.UserSignUpDto;
import com.example.webapplication.model.entity.CustomUser;
import com.example.webapplication.model.entity.UserRole;
import com.example.webapplication.model.entity.type.UserRoleType;
import com.example.webapplication.model.mapper.UserSignUpDtoMapper;
import com.example.webapplication.model.repository.UserRepository;
import com.example.webapplication.model.service.UserService;
import com.example.webapplication.util.EncryptedPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private static final String PASSWORD_ERROR = "passwordError";
    private static final String PASSWORD_ERROR_MESSAGE = "Password do not match!";

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean saveUser(UserSignUpDto userDto, Errors errors) throws WrongAuthorizationException {
        if (!userDto.getPassword().equals(userDto.getRepPassword())) {
            throw new WrongAuthorizationException(PASSWORD_ERROR, PASSWORD_ERROR_MESSAGE);
        }
        CustomUser user = UserSignUpDtoMapper.mapDto(userDto);
        CustomUser userFromDb = userRepository.findByUsername(user.getUsername());
        if (userFromDb != null) {
            return false;
        }
        String encryptionPassword = EncryptedPassword.encrytePassword(user.getPassword());
        user.setPassword(encryptionPassword);
        UserRole userRole = new UserRole();
        userRole.setUserRole(UserRoleType.GUEST);
        userRole.setRoleId(UserRoleType.GUEST.getRoleId());
        user.setRoles(Set.of(userRole));
        userRepository.save(user);
        return true;
    }

    @Override
    public Optional<CustomUser> getUserByLogin(String username) {
        CustomUser user = userRepository.findByUsername(username);

        return Optional.of(user);
    }

    @Override
    public Optional<CustomUser> getUserById(Long userId) {
        Optional<CustomUser> user = userRepository.findById(userId);

        return user;
    }

    @Override
    public List<CustomUser> getAllUser() {
        Iterable<CustomUser> users = userRepository.findAll();

        return (List<CustomUser>) users;
    }
}
