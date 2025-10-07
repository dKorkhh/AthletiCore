package com.example.athleticore.mapper;

import com.example.athleticore.dto.user.ClientDto;
import com.example.athleticore.entity.users.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "id", ignore = true)
    Client toEntity(ClientDto dto);
    ClientDto toClient(Client entity);
}
