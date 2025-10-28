package com.example.athleticore.dto.auth;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CredentialDto {
    private String password;
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
    private String email;
}
