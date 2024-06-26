package entity;

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
    private int id;

    @Column(name = "username")
    @NotNull(message = "is required")
    @Size(min = 4, message = "is required")
    private String username;

    @Column(name = "firstname")
    @NotNull(message = "is required")
    @Size(min = 4, message = "is required")
    private String firstname;

    @Column(name = "lastname")
    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String lastname;

    @Column(name = "dob")
    @NotNull(message = "is required")
    private Date dob;

    @Column(name = "email")
    @NotNull(message = "is required")
    private String email;

    @Column(name = "lastname")
    @NotNull(message = "is required")
    @Size(min = 6, message = "is required")
    private String password;

    @Column(name = "phone_no")
    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String phone_no;

    @Column(name = "pincode")
    @NotNull(message = "is required")
    @Size(min = 6, message = "is required")
    private int pincode;

    @Column(name = "role")
    private String role;

    @Column(name = "status")
    private String status;


}
