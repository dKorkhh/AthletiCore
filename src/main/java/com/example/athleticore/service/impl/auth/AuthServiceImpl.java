package com.example.athleticore.service.impl.auth;

import com.example.athleticore.dto.auth.CredentialDto;
import com.example.athleticore.dto.user.UserDto;
import com.example.athleticore.entity.users.User;
import com.example.athleticore.enums.Role;
import com.example.athleticore.exception.data.InvalidRefreshTokenException;
import com.example.athleticore.exception.user.NoSuchUserException;
import com.example.athleticore.mapper.UserMapper;
import com.example.athleticore.security.JwtAuthDTO;
import com.example.athleticore.security.JwtService;
import com.example.athleticore.security.RefreshTokenDto;
import com.example.athleticore.service.impl.user.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl {
    private final UserServiceImpl userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public JwtAuthDTO singIn(@Valid CredentialDto credentialDto) {
        User user = findByCredentials(credentialDto);
        return jwtService.generateAuthToken(userMapper.toDto(user));
    }

    public JwtAuthDTO refreshToken(RefreshTokenDto refreshTokenDTO) {
        String refreshToken = refreshTokenDTO.getRefreshToken();
        if (refreshToken == null) {
            throw new InvalidRefreshTokenException("Missing refresh token");
        }

        try {
            jwtService.validateJwtToken(refreshToken);
            String email = jwtService.getEmailFromToken(refreshToken);
            return userService.getUserByEmail(email)
                    .map(user -> jwtService.refreshBaseToken(userMapper.toDto(user), refreshToken))
                    .orElseThrow(() -> new NoSuchUserException("No such user with email: " + email));
        } catch (Exception e) {
            throw new InvalidRefreshTokenException("Invalid refresh token " + e.getMessage());
        }
    }

    private User findByCredentials(CredentialDto credentialDto) {
        return userService.getUserByEmail(credentialDto.getEmail())
                .filter(user -> passwordEncoder.matches(credentialDto.getPassword(), user.getPassword()))
                .orElseThrow(() -> new NoSuchUserException(String.format("No user with email: %s", credentialDto.getEmail())));
    }

    public User addUser(UserDto userDto) {
        return userService.addUser(userDto);
    }
}
