package com.example.webapplication.model.entity;

import com.example.webapplication.model.entity.type.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "currencies")
public class Currency implements Serializable {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "currency_id")
    private Integer currencyId;
    @Column(name = "currency_type")
    @Enumerated(EnumType.STRING)
    private CurrencyType type;
    @Column(name = "currency_price_type")
    @Enumerated(EnumType.STRING)
    private CurrencyType priceType;
    @Column(name = "currency_price")
    private double price;
}
