package com.example.athleticore.service.impl.user;

import com.example.athleticore.dto.PageResponse;
import com.example.athleticore.dto.patch.PatchDto;
import com.example.athleticore.dto.user.UserDto;
import com.example.athleticore.entity.Session;
import com.example.athleticore.entity.users.User;
import com.example.athleticore.enums.Role;
import com.example.athleticore.exception.user.NoSuchUserException;
import com.example.athleticore.mapper.UserMapper;
import com.example.athleticore.repository.SessionRepository;
import com.example.athleticore.repository.UserRepository;
import com.example.athleticore.service.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private static final Marker USER_OPS = MarkerManager.getMarker("USER_OPERATION");
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final SessionRepository sessionRepository;

    @Override
    public User addUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        user.setRole(Role.CLIENT);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);

        return user;
    }

    @Override
    public User addTrainer(UserDto dto) {
        User user = userMapper.toEntity(dto);
        user.setRole(Role.TRAINER);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(user);

        return user;
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.getUserById(id)
                .orElseThrow(() -> new NoSuchUserException("Don't found user with id #" + id));
    }

    @Override
    public List<User> getAllUser() {
        ThreadContext.put("operation", "getAllUser");
        try {
            List<User> clients = userRepository.findAll();
            logger.info(USER_OPS, "Retrieved {} users", clients.size());
            return clients;
        } catch (Exception e) {
            logger.error(USER_OPS, "Error fetching all users: {}", e.getMessage());
            throw e;
        } finally {
            ThreadContext.clearMap();
        }
    }

    @Override
    public List<User> findAllTrainers() {
        return userRepository.findAll().stream()
                .filter(user -> user.getRole().equals(Role.TRAINER))
                .toList();
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        List<Session> sessions = sessionRepository.getSessionsByTrainerId(id);

        sessions.forEach(s -> s.setTrainer(null));
        sessionRepository.saveAll(sessions);

        userRepository.deleteById(id);
    }

    @Override
    public User updateUser(PatchDto dto) {
        return null;
    }

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("User is not authenticated");
        }

        String email = auth.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public Role getRoleOfCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("User is not authenticated");
        }

        String email = auth.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"))
                .getRole();
    }

    public PageResponse<User> getClientsPage(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, parseSort(sort));

        Page<User> result = userRepository.findAllByRole(Role.CLIENT, pageable);

        return new PageResponse<>(
                result.getContent(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages(),
                result.isFirst(),
                result.isLast()
        );
    }

    public PageResponse<User> getTrainersPage(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, parseSort(sort));

        Page<User> result = userRepository.findAllByRole(Role.TRAINER, pageable);

        return new PageResponse<>(
                result.getContent(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages(),
                result.isFirst(),
                result.isLast()
        );
    }

    private Sort parseSort(String sort) {
        String[] data = sort.split(",");
        return Sort.by(Sort.Direction.fromString(data[1]), data[0]);
    }

}
