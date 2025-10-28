package com.example.athleticore.dto.user;

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
    private String role;
    private String phoneNumber;
}
