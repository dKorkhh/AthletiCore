package com.example.athleticore.repository;

import com.example.athleticore.entity.users.User;
import com.example.athleticore.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {
    Optional<User> getUserByEmail(String email);
    Optional<User> getUserById(Long id);

    Optional<User> findByEmail(String email);
    Page<User> findAllByRole(Role role, Pageable pageable);
}
