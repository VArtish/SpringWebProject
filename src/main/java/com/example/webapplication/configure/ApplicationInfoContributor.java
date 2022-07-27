package com.example.webapplication.configure;

import com.example.webapplication.model.entity.type.UserRoleType;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ApplicationInfoContributor implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        List<UserRoleType> userRoles = Arrays.stream(UserRoleType.values()).toList();
        builder.withDetail("application roles", userRoles);
    }
}
