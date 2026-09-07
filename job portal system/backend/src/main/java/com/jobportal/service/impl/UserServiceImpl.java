package com.jobportal.service.impl;

import com.jobportal.dto.*;
import com.jobportal.entity.User;
import com.jobportal.repository.UserRepository;
import com.jobportal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service @RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public UserDto createUser(RegisterRequest r) {
        if (repo.existsByEmailIgnoreCase(r.getEmail())) throw new IllegalArgumentException("Email already registered");
        User u = new User();
        u.setName(r.getName().trim()); u.setEmail(r.getEmail().trim().toLowerCase());
        u.setPassword(encoder.encode(r.getPassword())); u.setRole(r.getRole());
        return toDto(repo.save(u));
    }
    public UserDto login(LoginRequest r) {
        User u = repo.findByEmailIgnoreCase(r.getEmail().trim())
            .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        if (!encoder.matches(r.getPassword(), u.getPassword())) throw new IllegalArgumentException("Invalid email or password");
        return toDto(u);
    }
    public UserDto getUserById(Long id) { return toDto(getRequiredUser(id)); }
    public List<UserDto> getAllUsers() { return repo.findAll().stream().map(this::toDto).toList(); }
    public User getRequiredUser(Long id) { return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found: "+id)); }
    public void deleteUser(Long id) { if(!repo.existsById(id)) throw new IllegalArgumentException("User not found: "+id); repo.deleteById(id); }
    private UserDto toDto(User u) { return UserDto.builder().id(u.getId()).name(u.getName()).email(u.getEmail()).role(u.getRole()).build(); }
}
