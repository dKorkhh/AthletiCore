package com.example.athleticore.service.impl.user;

import com.example.athleticore.dto.user.TrainerDto;
import com.example.athleticore.entity.users.Trainer;
import com.example.athleticore.mapper.TrainerMapper;
import com.example.athleticore.repository.TrainerRepository;
import com.example.athleticore.service.user.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {
    private final TrainerRepository trainerRepository;
    private final TrainerMapper trainerMapper;

    @Override
    public Trainer addUser(TrainerDto trainerDto) {
        Trainer trainer = trainerMapper.toEntity(trainerDto);
        trainerRepository.save(trainer);

        return trainer;
    }

    @Override
    public Optional<Trainer> getUserById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Trainer> getAllUser() {
        return trainerRepository.findAll();
    }
}
