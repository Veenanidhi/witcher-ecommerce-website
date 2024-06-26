package com.witcher.e_commerce.application.witcher.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "product")
public class Product {

    @Id
    @Column(name = "product_id")
    private int product_id;

    @Column(name = "product")
    private String product;

    @Column(name = "brand_id")
    private String brand_id;

    @Column(name = "category")
    private String category;

    @Column(name = "category_id")
    private String category_id;

    @Column(name = "price")
    private String price;

    @Column(name = "status")
    private String status;

    @Column(name = "stock")
    private String stock;

}
