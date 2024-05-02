package com.example.ChattApplication.service;

import com.example.ChattApplication.entity.User;
import com.example.ChattApplication.payload.UserPayload;
import com.example.ChattApplication.repository.RoleRepository;
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
    private final RoleRepository roleRepository;

    public User saveUser(UserPayload userPayload){
        try {
            User user = new User(userPayload);
            user.setRole(roleRepository.findByName("ROLE_USER"));
            userRepository.save(user);
            return user;
        }catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }

    public boolean deleteUser(Long id){
        try {
            userRepository.deleteById(id);
            return true;
        }catch (Exception e){
            log.error(e.getMessage());
            return false;
        }
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

}
