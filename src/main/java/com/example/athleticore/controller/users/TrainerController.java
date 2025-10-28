package com.example.athleticore.controller.users;

import com.example.athleticore.dto.user.UserDto;
import com.example.athleticore.entity.users.User;
import com.example.athleticore.service.impl.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/trainers")
@RequiredArgsConstructor
public class TrainerController {
    private UserServiceImpl userService;

    @GetMapping("/")
    public List<User> getAllTrainers(){
        return Collections.emptyList();
    }

    @DeleteMapping("/{id}")
    public void deleteTrainerById(@PathVariable Long id){

    }

    @PostMapping("/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public User addTrainer(@RequestBody UserDto trainerDto){
        return userService.addUser(trainerDto);
    }
}
