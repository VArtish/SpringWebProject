package com.example.webapplication.model.entity.type;

public enum UserRoleType {
    ADMIN(1),
    GUEST(2);

    private int roleId;

    UserRoleType(int id) {
        this.roleId = id;

    }

    public int getRoleId() {
        return roleId;
    }
}
