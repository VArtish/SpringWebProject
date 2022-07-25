package com.example.webapplication.model.service.impl;

import com.example.webapplication.model.entity.SimpleEmailContext;
import com.example.webapplication.model.service.SimpleEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class DefaultEmailService implements SimpleEmailService {
    private JavaMailSender emailSender;
    private static final String MESSAGE_SENDER = "lubimyjtvoj2@gmail.com";

    @Autowired
    public DefaultEmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    @EventListener(SimpleEmailContext.class)
    public void sendSuccessMessage(SimpleEmailContext email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(MESSAGE_SENDER);
        message.setTo(email.getTo());
        message.setSubject(email.getSubject());
        message.setText(email.getMessage());

        emailSender.send(message);
    }
}