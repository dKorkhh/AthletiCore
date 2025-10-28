package com.example.athleticore.service.impl.user;

import com.example.athleticore.dto.patch.PatchDto;
import com.example.athleticore.dto.user.UserDto;
import com.example.athleticore.entity.users.User;
import com.example.athleticore.enums.Role;
import com.example.athleticore.mapper.UserMapper;
import com.example.athleticore.repository.UserRepository;
import com.example.athleticore.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.*;
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

    @Override
    public User addUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        user.setRole(Role.CLIENT);
        userRepository.save(user);

        return user;
    }

    @Override
    public User getUserById(Long id) {
        return null;
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
    public Optional<User> getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    @Override
    public void deleteUserById(Long id) {

    }

    @Override
    public User updateUser(PatchDto dto) {
        return null;
    }
}
