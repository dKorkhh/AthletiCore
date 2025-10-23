package com.example.athleticore.service.user;

import com.example.athleticore.dto.patch.PatchDto;

import java.util.List;
import java.util.Optional;

public interface UserService<T, E> {
    T addUser(E dto);
    T getUserById(Long id);
    List<T> getAllUser();
    T getUserByEmail(String email);
    void deleteUserById(Long id);
    T updateUser(PatchDto dto);
}
