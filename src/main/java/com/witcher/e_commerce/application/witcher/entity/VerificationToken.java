package com.witcher.e_commerce.application.witcher.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name ="verification_token")
@Data
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @Column(name = "expiry_date")
    private Timestamp expiryDate;
    @OneToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name ="user_id", referencedColumnName = "id")
    private User user;

    public VerificationToken(String token, User user) {
    }

    public void setExpiryDate(Timestamp timestamp) {
    }
}
