package com.example.athleticore.config;

import com.example.athleticore.entity.users.User;
import com.example.athleticore.enums.Role;
import com.example.athleticore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInit implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        User admin = new User();
        admin.setEmail("admin@admin.com");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setRole(Role.ADMIN);

        userRepository.save(admin);
    }
}
