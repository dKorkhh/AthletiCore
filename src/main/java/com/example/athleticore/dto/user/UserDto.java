package com.example.athleticore.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private FullName fullName;
    private String email;
    private String password;
    private String role;
}
