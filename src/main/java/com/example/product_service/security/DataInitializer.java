package com.example.product_service.security;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.product_service.entity.User;
import com.example.product_service.repository.UserRepository;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (!userRepository.findByUsername("Srinath").isPresent()) {
                User u = new User();
                u.setUsername("Srinath");
                u.setPassword(passwordEncoder.encode("Srinath@123"));
                u.setRoles("ROLE_USER");
                userRepository.save(u);
            }
        };
    }
}
