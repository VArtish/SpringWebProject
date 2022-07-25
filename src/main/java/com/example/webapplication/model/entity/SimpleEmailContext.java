package com.example.webapplication.model.entity;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

public class SimpleEmailContext extends ApplicationEvent {
    private String message;
    private String to;
    private String subject;

    public SimpleEmailContext(Object source, String message, String to, String subject) {
        super(source);
        this.message = message;
        this.to = to;
        this.subject = subject;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public String getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }
}
