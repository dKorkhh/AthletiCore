package com.example.athleticore.service.impl.auth;

import com.example.athleticore.dto.user.ClientDto;
import com.example.athleticore.dto.user.TrainerDto;
import com.example.athleticore.dto.user.UserDto;
import com.example.athleticore.mapper.UserMapper;
import com.example.athleticore.service.impl.user.ClientServiceImpl;
import com.example.athleticore.service.impl.user.TrainerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl {
    private final ClientServiceImpl clientService;
    private final TrainerServiceImpl trainerService;
    private final UserMapper userMapper;

    public void saveUser(UserDto userDto) {
        if (Objects.equals(userDto.getRole(), "CLIENT")) {
            ClientDto clientDto = userMapper.toClientDto(userDto);
            clientService.addUser(clientDto);
        }
        else if (Objects.equals(userDto.getRole(), "TRAINER")) {
            TrainerDto trainerDto = userMapper.toTrainerDto(userDto);
            trainerService.addUser(trainerDto);
        }
    }
}
