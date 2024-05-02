package com.example.ChattApplication.payload;

import lombok.Data;

@Data
public class UserPayload {
    private String username;
    private String email;
    private String password;
}
