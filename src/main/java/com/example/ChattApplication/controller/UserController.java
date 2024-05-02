package com.example.ChattApplication.controller;

import com.example.ChattApplication.entity.User;
import com.example.ChattApplication.payload.UserPayload;
import com.example.ChattApplication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@CrossOrigin(maxAge = 3600, originPatterns = "*",origins = "*")
public class UserController {
    private final UserService userService;

    @PostMapping
    public User createUser(@RequestBody UserPayload userPayload) {
        return userService.saveUser(userPayload);
    }

    @DeleteMapping("/{id}")
    public boolean deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @GetMapping
    public List<User> getUser() {
        return userService.getUsers();
    }

}
