package com.example.athleticore.Entity;

import com.example.athleticore.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class User {
    private Long id;
    private FullName fullName;
    private String email;
    private String password;
    private Role role;
}
