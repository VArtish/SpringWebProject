package com.example.webapplication.model.service.impl;

import com.example.webapplication.exception.WrongAuthorizationException;
import com.example.webapplication.model.dto.UserSignUpDto;
import com.example.webapplication.model.entity.CustomUser;
import com.example.webapplication.model.entity.SimpleEmailContext;
import com.example.webapplication.model.entity.UserRole;
import com.example.webapplication.model.entity.type.UserRoleType;
import com.example.webapplication.model.mapper.UserSignUpDtoMapper;
import com.example.webapplication.model.repository.UserRepository;
import com.example.webapplication.model.service.SimpleEmailService;
import com.example.webapplication.model.service.UserService;
import com.example.webapplication.util.EncryptedPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private static final String PASSWORD_ERROR = "passwordError";
    private static final String PASSWORD_ERROR_MESSAGE = "Password do not match!";
    private static final String MESSAGE_SUBJECT = "Success authorization!";
    private static final String MESSAGE_TEXT = "Congratulation!";
    private SimpleEmailService emailService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, SimpleEmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @Override
    public boolean saveUser(UserSignUpDto userDto, Errors errors) throws WrongAuthorizationException {
        if(errors.hasErrors()) {
            return false;
        }

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
        sendSuccessMessage(user);

        return true;
    }

    private void sendSuccessMessage(CustomUser user) {
        SimpleEmailContext emailContext = new SimpleEmailContext();
        emailContext.setTo(user.getEmail());
        emailContext.setSubject(MESSAGE_SUBJECT);
        emailContext.setMessage(MESSAGE_TEXT);
        emailService.sendMail(emailContext);
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
