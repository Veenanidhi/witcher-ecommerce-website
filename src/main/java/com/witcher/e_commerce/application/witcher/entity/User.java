package com.witcher.e_commerce.application.witcher.entity;

import com.witcher.e_commerce.application.witcher.service.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;

@Entity
@Data
@Table(name = "users")
public class User{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Getter
    @Column(name = "username")
    @NotNull(message = "is required")
    private String username;


    @Column(name = "email")
    @NotNull(message = "is required")
    @Pattern(regexp="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    private String email;

    @Getter
    @Column
    @NotNull(message = "is required")
    private String password;

    @Column(name = "phone_no")
    private String phone_no;


    @Getter
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "status")
    private Boolean status;

    public void setRole(Role role) {
        this.role = role;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return true;
    }

    public void setEnabled(boolean b) {

    }
}
