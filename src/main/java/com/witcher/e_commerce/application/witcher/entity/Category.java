package com.witcher.e_commerce.application.witcher.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id")
    private int id;

    @Column(name = "product_id")
    private int product_id;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "user_id")
    private int user_id;

    @Column(name = "category_name")
    private String name;

    @Column(name = "sold")
    private String sold;

    @Column(name = "stock")
    private String stock;

    @Column(name = "added")
    private String added;

    @Column(name = "action")
    private Boolean action;

}
