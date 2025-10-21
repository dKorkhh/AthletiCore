package com.example.athleticore.service.impl.user;

import com.example.athleticore.dto.patch.PatchDto;
import com.example.athleticore.dto.user.TrainerDto;
import com.example.athleticore.entity.users.Trainer;
import com.example.athleticore.enums.Role;
import com.example.athleticore.exception.user.NoSuchUserException;
import com.example.athleticore.mapper.TrainerMapper;
import com.example.athleticore.repository.TrainerRepository;
import com.example.athleticore.service.user.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {
    private final TrainerRepository trainerRepository;
    private final TrainerMapper trainerMapper;

    @Override
    public Trainer addUser(TrainerDto trainerDto) {
        Trainer trainer = trainerMapper.toEntity(trainerDto);
        trainer.setRole(Role.TRAINER);
        trainerRepository.save(trainer);

        return trainer;
    }

    @Override
    public Trainer getUserById(Long id) {
        return trainerRepository.findById(id)
                .orElseThrow(() -> new NoSuchUserException("No trainer with id: " + id));
    }

    @Override
    public List<Trainer> getAllUser() {
        return trainerRepository.findAll();
    }

    @Override
    public Trainer getUserByEmail(String email) {
        return trainerRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchUserException("No trainer with email: " + email));
    }

    @Override
    public void deleteUserById(Long id) {

    }

    @Override
    public Trainer updateUser(PatchDto dto) {
        return null;
    }
}
