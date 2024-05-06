package com.example.ChattApplication.service;

import com.example.ChattApplication.entity.User;
import com.example.ChattApplication.payload.UserPayload;
import com.example.ChattApplication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public User saveUser(String email, String userName, String password) {
        try {

            User user = new User();
            user.setUsername(userName);
            user.setEmail(email);
            user.setPassword(password);

            userRepository.save(user);
            return user;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public User findUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return null;
        }
        if (!user.getPassword().equals(password)) {
            return null;
        }

        return user;
    }

    public boolean deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }
}
