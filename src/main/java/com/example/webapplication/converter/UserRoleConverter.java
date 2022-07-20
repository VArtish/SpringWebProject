package com.example.webapplication.converter;

import com.example.webapplication.model.entity.type.UserRoleType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class UserRoleConverter implements AttributeConverter<UserRoleType, String> {
    @Override
    public String convertToDatabaseColumn(UserRoleType attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.toString();
    }

    @Override
    public UserRoleType convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        try {
            return UserRoleType.valueOf(dbData);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}