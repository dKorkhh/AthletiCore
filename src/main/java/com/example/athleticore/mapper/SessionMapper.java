package com.example.athleticore.mapper;

import com.example.athleticore.dto.sessions.SessionDto;
import com.example.athleticore.entity.Session;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SessionMapper {
    SessionDto toSessionDto(Session session);
    Session toSessionEntity(SessionDto sessionDto);
}
