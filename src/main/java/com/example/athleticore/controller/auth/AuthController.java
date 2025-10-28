package com.example.athleticore.controller.auth;

import com.example.athleticore.dto.auth.CredentialDto;
import com.example.athleticore.dto.user.UserDto;
import com.example.athleticore.entity.users.User;
import com.example.athleticore.security.JwtAuthDTO;
import com.example.athleticore.security.RefreshTokenDto;
import com.example.athleticore.service.impl.auth.AuthServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthServiceImpl authService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public JwtAuthDTO login(@Valid @RequestBody CredentialDto credentialDto){
        return authService.singIn(credentialDto);
    }

    @PostMapping("/register")
    @ResponseStatus(code = HttpStatus.CREATED)
    public User register(@RequestBody UserDto userDto){
        return authService.addUser(userDto);
    }

    @PostMapping("/refresh")
    public JwtAuthDTO refreshToken(@RequestBody RefreshTokenDto refreshTokenDTO) throws Exception{
        return authService.refreshToken(refreshTokenDTO);
    }
}
