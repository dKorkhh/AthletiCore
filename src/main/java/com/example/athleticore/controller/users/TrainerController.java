package com.example.athleticore.controller.users;

import com.example.athleticore.dto.user.TrainerDto;
import com.example.athleticore.entity.users.Client;
import com.example.athleticore.entity.users.Trainer;
import com.example.athleticore.service.impl.user.TrainerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/trainers")
@RequiredArgsConstructor
public class TrainerController {
    private final TrainerServiceImpl trainerService;

    @GetMapping("/")
    public List<Client> getAllTrainers(){
        return Collections.emptyList();
    }

    @DeleteMapping("/{id}")
    public void deleteTrainerById(@PathVariable Long id){

    }

    @PostMapping("/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Trainer addTrainer(@RequestBody TrainerDto trainerDto){
        return trainerService.addUser(trainerDto);
    }
}
