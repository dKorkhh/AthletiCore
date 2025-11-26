package com.example.athleticore.service.impl.auth;

import static org.junit.jupiter.api.Assertions.*;

import com.example.athleticore.dto.auth.CredentialDto;
import com.example.athleticore.dto.user.UserDto;
import com.example.athleticore.entity.users.User;
import com.example.athleticore.exception.data.InvalidRefreshTokenException;
import com.example.athleticore.exception.user.NoSuchUserException;
import com.example.athleticore.mapper.UserMapper;
import com.example.athleticore.security.JwtAuthDTO;
import com.example.athleticore.security.JwtService;
import com.example.athleticore.security.RefreshTokenDto;
import com.example.athleticore.service.impl.user.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserServiceImpl userService;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthServiceImpl authService;

    private User user;
    private UserDto userDto;
    private CredentialDto credentials;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setEmail("john@example.com");
        user.setPassword("encodedPass");

        userDto = new UserDto();
        userDto.setEmail("john@example.com");

        credentials = new CredentialDto();
        credentials.setEmail("john@example.com");
        credentials.setPassword("rawPass");
    }

    @Test
    void signIn_shouldReturnJwtAuthDTO_whenCredentialsValid() {
        JwtAuthDTO expectedJwt = new JwtAuthDTO("access", "refresh");

        when(userService.getUserByEmail("john@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("rawPass", "encodedPass")).thenReturn(true);
        when(userMapper.toDto(user)).thenReturn(userDto);
        when(jwtService.generateAuthToken(userDto)).thenReturn(expectedJwt);

        JwtAuthDTO result = authService.singIn(credentials);

        assertEquals(expectedJwt, result);
        verify(jwtService).generateAuthToken(userDto);
    }

    @Test
    void signIn_shouldThrowNoSuchUserException_whenUserNotFound() {
        when(userService.getUserByEmail("john@example.com")).thenReturn(Optional.empty());

        assertThrows(NoSuchUserException.class, () -> authService.singIn(credentials));
        verify(jwtService, never()).generateAuthToken(any());
    }

    @Test
    void signIn_shouldThrowNoSuchUserException_whenPasswordDoesNotMatch() {
        when(userService.getUserByEmail("john@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("rawPass", "encodedPass")).thenReturn(false);

        assertThrows(NoSuchUserException.class, () -> authService.singIn(credentials));
    }

    @Test
    void refreshToken_shouldReturnJwtAuthDTO_whenValidRefreshToken() {
        String refresh = "refresh123";
        RefreshTokenDto dto = RefreshTokenDto.builder().refreshToken(refresh).build();
        JwtAuthDTO expected = new JwtAuthDTO("accessNew", "refreshNew");

        when(jwtService.getEmailFromToken(refresh)).thenReturn("john@example.com");
        when(userService.getUserByEmail("john@example.com")).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto);
        when(jwtService.refreshBaseToken(userDto, refresh)).thenReturn(expected);

        JwtAuthDTO result = authService.refreshToken(dto);

        assertEquals(expected, result);
        verify(jwtService).refreshBaseToken(userDto, refresh);
    }

    @Test
    void refreshToken_shouldThrowException_whenTokenMissing() {
        RefreshTokenDto dto = RefreshTokenDto.builder().build();
        assertThrows(InvalidRefreshTokenException.class, () -> authService.refreshToken(dto));
    }

    @Test
    void refreshToken_shouldThrowException_whenTokenInvalid() {
        String badToken = "invalid";
        RefreshTokenDto dto = RefreshTokenDto.builder().refreshToken(badToken).build();

        doThrow(new RuntimeException("bad")).when(jwtService).validateJwtToken(badToken);

        assertThrows(InvalidRefreshTokenException.class, () -> authService.refreshToken(dto));
    }

    @Test
    void refreshToken_shouldThrowNoSuchUserException_whenUserNotFound() {
        String token = "token";
        RefreshTokenDto dto = RefreshTokenDto.builder().refreshToken(token).build();

        when(jwtService.getEmailFromToken(token)).thenReturn("missing@example.com");
        when(userService.getUserByEmail("missing@example.com")).thenReturn(Optional.empty());

        assertThrows(InvalidRefreshTokenException.class, () -> authService.refreshToken(dto));
    }
}
