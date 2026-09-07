package com.jobportal.config;

import com.jobportal.entity.Role;
import com.jobportal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration @RequiredArgsConstructor
public class DataInitializer {
    private final UserRepository users; private final PasswordEncoder encoder;
    @Bean CommandLineRunner seedAdmin(){
        return args -> {
            if(!users.existsByEmailIgnoreCase("admin@jobportal.com")){
                com.jobportal.entity.User u=new com.jobportal.entity.User();
                u.setName("System Admin"); u.setEmail("admin@jobportal.com");
                u.setPassword(encoder.encode("Admin@123")); u.setRole(Role.ADMIN); users.save(u);
            }
        };
    }
}
