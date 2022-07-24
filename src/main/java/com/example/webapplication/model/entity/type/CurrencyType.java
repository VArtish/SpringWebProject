package com.example.webapplication.model.entity.type;

public enum CurrencyType {
    USD(1),
    EUR(2),
    RUB(3),
    BYN(4),
    KZT(5);

    private Integer id;

    CurrencyType(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return id;
    }
}
