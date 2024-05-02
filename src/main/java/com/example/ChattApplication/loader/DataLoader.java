package com.example.ChattApplication.loader;

import com.example.ChattApplication.entity.Role;
import com.example.ChattApplication.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String init;

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {


        try {
            if (init.equalsIgnoreCase("create")) {
                Role roleAdmin = new Role();
                roleAdmin.setId(1L);
                roleAdmin.setName("ROLE_ADMIN");


                Role roleUser = new Role();
                roleUser.setId(2L);
                roleUser.setName("ROLE_USER");

                List<Role> roleList = new ArrayList<>(List.of(roleAdmin,roleUser));
                roleRepository.saveAll(roleList);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
