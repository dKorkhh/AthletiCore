package com.example.athleticore.security;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtAuthDTO {
    private String token;
    private String refreshToken;
}