package com.example.athleticore.dto.user;

import com.example.athleticore.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserDto {
    private FullName fullName;
    private String email;
    private String password;
    private Role role;
    private String phoneNumber;
}
