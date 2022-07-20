package com.example.webapplication.model.entity;

import com.example.webapplication.converter.UserRoleConverter;
import com.example.webapplication.model.entity.type.UserRoleType;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "roles")
public class UserRole implements GrantedAuthority {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "role_varchar")
    @Convert(converter = UserRoleConverter.class)
    private UserRoleType userRole;

    @ManyToMany
    private List<CustomUser> users;

    @Override
    public String getAuthority() {
        return getUserRole().name();
    }
}
