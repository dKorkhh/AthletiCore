package com.example.athleticore.security;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefreshTokenDto {
    private String refreshToken;
}
