package com.witcher.e_commerce.application.witcher.entity;

import com.witcher.e_commerce.application.witcher.service.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "username")
    @NotNull(message = "is required")
    @Size(min = 4, message = "is required")
    private String username;


    @Column(name = "email")
    @NotNull(message = "is required")
    private String email;

    @Column
    @NotNull(message = "is required")
    @Size(min = 6, message = "is required")
    private String password;

    @Column(name = "phone_no")
    @Size(min = 10,max = 10 ,message = "is required")
    private String phone_no;


    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "status")
    private Boolean status;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    private boolean enabled;

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
