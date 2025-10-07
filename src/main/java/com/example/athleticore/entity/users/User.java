package com.example.athleticore.entity.users;

import com.example.athleticore.dto.user.FullName;
import com.example.athleticore.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

@MappedSuperclass
@Data
public abstract class User {
    @Embedded
    private FullName fullName;

    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}

