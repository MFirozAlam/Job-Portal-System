package com.jobportal.controller;

import com.jobportal.dto.LoginRequest;
import com.jobportal.dto.RegisterRequest;
import com.jobportal.dto.UserDto;
import com.jobportal.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService users;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(users.createUser(request));
    }

    @PostMapping("/login")
    public UserDto login(
            @Valid @RequestBody LoginRequest request,
            HttpSession session
    ) {
        UserDto user = users.login(request);

        session.setAttribute("userId", user.getId());
        session.setAttribute("role", user.getRole().name());

        return user;
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.invalidate();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public UserDto me(HttpSession session) {
        return users.getUserById(currentId(session));
    }

    static Long currentId(HttpSession session) {
        Object id = session.getAttribute("userId");

        if (id == null) {
            throw new IllegalStateException("Not logged in");
        }

        return ((Number) id).longValue();
    }
}