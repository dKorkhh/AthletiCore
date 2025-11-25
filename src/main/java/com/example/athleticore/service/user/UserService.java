package com.example.athleticore.service.user;

import com.example.athleticore.dto.patch.PatchDto;
import com.example.athleticore.dto.user.UserDto;
import com.example.athleticore.entity.users.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User addUser(UserDto dto);
    User getUserById(Long id);
    List<User> getAllUser();
    Optional<User> getUserByEmail(String email);
    void deleteUserById(Long id);
    User updateUser(PatchDto dto);
}
