package com.example.athleticore.mapper;

import com.example.athleticore.dto.user.UserDto;
import com.example.athleticore.entity.users.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDto dto);
    UserDto toDto(User user);
}
