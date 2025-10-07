package com.example.athleticore.mapper;

import com.example.athleticore.dto.user.ClientDto;
import com.example.athleticore.dto.user.TrainerDto;
import com.example.athleticore.dto.user.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    default ClientDto toClientDto(UserDto user) {
        if (user == null) return null;

        ClientDto dto = new ClientDto();
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setRole(user.getRole());
        return dto;
    }

    default TrainerDto toTrainerDto(UserDto user) {
        if (user == null) return null;

        TrainerDto dto = new TrainerDto();
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setRole(user.getRole());
        return dto;
    }
}
