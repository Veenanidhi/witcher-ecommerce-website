package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "category")
public class Category {

    @Id
    @Column(name = "cart_id")
    private int cart_id;

    @Column(name = "product_id")
    private int product_id;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "user_id")
    private int user_id;



}
