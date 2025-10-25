package com.example.athleticore.controller.auth;

import com.example.athleticore.dto.auth.CredentialDto;
import com.example.athleticore.dto.user.FullName;
import com.example.athleticore.dto.user.UserDto;
import com.example.athleticore.service.impl.auth.AuthServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthServiceImpl authService;

    @PostMapping("/login")
    public void login(@Valid @RequestBody CredentialDto credentialDto){

    }
}
