package com.example.ChattApplication.entity;

import javax.persistence.*;

import com.example.ChattApplication.payload.UserPayload;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToOne(fetch = FetchType.EAGER)
    Role role;

    public User (UserPayload userPayload){
        this.username = userPayload.getUsername();
        this.email = userPayload.getEmail();
        this.password = userPayload.getPassword();
    }

}

