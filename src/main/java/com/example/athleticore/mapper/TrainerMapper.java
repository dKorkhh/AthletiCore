package com.example.athleticore.mapper;

import com.example.athleticore.dto.user.TrainerDto;
import com.example.athleticore.entity.users.Trainer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TrainerMapper {
    Trainer toEntity(TrainerDto trainer);
    TrainerDto toTrainer(Trainer entity);
}
