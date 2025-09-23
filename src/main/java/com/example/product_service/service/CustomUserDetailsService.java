package com.example.product_service.service;

import com.example.product_service.entity.User;
import com.example.product_service.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // Use the 'roles' field from your entity
        String role = u.getRoles() != null ? u.getRoles() : "ROLE_USER";

        return new org.springframework.security.core.userdetails.User(
                u.getUsername(),
                u.getPassword(),  // plain password from DB
                Collections.singletonList(new SimpleGrantedAuthority(role))
        );

    }
}
