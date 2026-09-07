package com.jobportal.service;
import com.jobportal.dto.*;
import com.jobportal.entity.User;
import java.util.List;

public interface UserService {
    UserDto createUser(RegisterRequest request);

    UserDto login(LoginRequest request);

    UserDto getUserById(Long id);

    List<UserDto> getAllUsers();

    User getRequiredUser(Long id);

    void deleteUser(Long id);
}
