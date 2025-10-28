package com.example.athleticore.controller.users;

import com.example.athleticore.entity.users.User;
import com.example.athleticore.service.impl.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/trainers")
@RequiredArgsConstructor
public class TrainerController {
    private final UserServiceImpl userService;

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllTrainers(){
        return Collections.emptyList();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteTrainerById(@PathVariable Long id){

    }
}
