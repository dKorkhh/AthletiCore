package com.example.athleticore.controller.auth;

import com.example.athleticore.dto.auth.CredentialDto;
import com.example.athleticore.dto.user.FullName;
import com.example.athleticore.dto.user.UserDto;
import com.example.athleticore.security.JwtAuthDTO;
import com.example.athleticore.security.RefreshTokenDto;
import com.example.athleticore.service.impl.auth.AuthServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Duration;

@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthServiceImpl authService;

    @PostMapping("/login")
    public String processLogin(
            @ModelAttribute("credentialDto") CredentialDto credentialDto,
            HttpServletResponse response,
            RedirectAttributes redirectAttributes) {
        try {
            JwtAuthDTO jwt = authService.singIn(credentialDto);

            ResponseCookie cookie = ResponseCookie.from("accessToken", jwt.getToken())
                    .httpOnly(true)
                    .secure(false)
                    .sameSite("Lax")
                    .path("/")
                    .maxAge(Duration.ofHours(2))
                    .build();

            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            return "redirect:/api/sessions";
        } catch (Exception e) {
            return "redirect:/api/auth/login";
        }
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        if (!model.containsAttribute("credentialDto")) {
            model.addAttribute("credentialDto", new CredentialDto());
        }
        return "auth/login";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        if (!model.containsAttribute("userDto")) {
            UserDto userDto = UserDto.builder()
                    .fullName(FullName.builder().build())
                    .build();
            model.addAttribute("userDto", userDto);
        }
        return "auth/register";
    }

    @PostMapping("/register")
    @ResponseStatus(code = HttpStatus.CREATED)
    public String register(@ModelAttribute UserDto userDto,
                           RedirectAttributes redirectAttributes) {
        authService.addUser(userDto);
        redirectAttributes.addFlashAttribute("success", "User registered successfully!");
        return "redirect:/api";
    }

    @PostMapping("/refresh")
    public JwtAuthDTO refreshToken(@RequestBody RefreshTokenDto refreshTokenDTO) throws Exception{
        return authService.refreshToken(refreshTokenDTO);
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("accessToken", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        SecurityContextHolder.clearContext();

        return "redirect:/";
    }
}
