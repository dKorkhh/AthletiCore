package com.example.athleticore.service.impl.user;

import com.example.athleticore.dto.PageResponse;
import com.example.athleticore.dto.user.UserDto;
import com.example.athleticore.entity.Session;
import com.example.athleticore.entity.users.User;
import com.example.athleticore.enums.Role;
import com.example.athleticore.exception.user.NoSuchUserException;
import com.example.athleticore.mapper.UserMapper;
import com.example.athleticore.repository.SessionRepository;
import com.example.athleticore.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;
    @Mock
    UserMapper userMapper;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    SessionRepository sessionRepository;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    void addUser_success() {
        UserDto dto = new UserDto();
        dto.setPassword("12345");

        User user = new User();
        when(userMapper.toEntity(dto)).thenReturn(user);
        when(passwordEncoder.encode("12345")).thenReturn("encoded");
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.addUser(dto);

        assertThat(result.getRole()).isEqualTo(Role.CLIENT);
        assertThat(result.getPassword()).isEqualTo("encoded");
    }

    @Test
    void getUserById_found() {
        User user = new User();
        user.setId(1L);

        when(userRepository.getUserById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);

        assertThat(result).isEqualTo(user);
    }

    @Test
    void getUserById_notFound() {
        when(userRepository.getUserById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserById(1L))
                .isInstanceOf(NoSuchUserException.class);
    }

    @Test
    void findAllTrainers_success() {
        User u1 = new User();
        u1.setRole(Role.TRAINER);

        User u2 = new User();
        u2.setRole(Role.CLIENT);

        when(userRepository.findAll()).thenReturn(List.of(u1, u2));

        List<User> result = userService.findAllTrainers();

        assertThat(result).containsExactly(u1);
    }

    @Test
    void deleteUserById_removesTrainerFromSessions() {
        Long id = 5L;

        Session s1 = new Session();
        s1.setId(10L);
        s1.setTrainer(new User());

        Session s2 = new Session();
        s2.setId(11L);
        s2.setTrainer(new User());

        when(sessionRepository.getSessionsByTrainerId(id))
                .thenReturn(List.of(s1, s2));

        userService.deleteUserById(id);

        assertThat(s1.getTrainer()).isNull();
        assertThat(s2.getTrainer()).isNull();

        verify(sessionRepository).saveAll(anyList());
        verify(userRepository).deleteById(id);
    }

    @Test
    void getClientsPage_success() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));

        User u1 = new User();
        u1.setId(1L);

        Page<User> page = new PageImpl<>(List.of(u1), pageable, 1);

        when(userRepository.findAllByRole(eq(Role.CLIENT), any(Pageable.class)))
                .thenReturn(page);

        PageResponse<User> response = userService.getClientsPage(0, 10, "id,asc");

        assertThat(response.getContent()).hasSize(1);
        assertThat(response.getTotalPages()).isEqualTo(1);
        assertThat(response.getTotalElements()).isEqualTo(1);
        assertThat(response.getPage()).isEqualTo(0);
    }

    @Test
    void getTrainersPage_success() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));

        User t = new User();
        t.setId(2L);

        Page<User> page = new PageImpl<>(List.of(t), pageable, 1);

        when(userRepository.findAllByRole(eq(Role.TRAINER), any(Pageable.class)))
                .thenReturn(page);

        PageResponse<User> response = userService.getTrainersPage(0, 10, "id,asc");

        assertThat(response.getContent()).containsExactly(t);
    }
}