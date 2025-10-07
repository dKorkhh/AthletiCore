package com.example.athleticore.service.user;

import java.util.List;
import java.util.Optional;

public interface UserService<T, E> {
    T addUser(E dto);
    Optional<T> getUserById(Long id);
    List<T> getAllUser();
}
