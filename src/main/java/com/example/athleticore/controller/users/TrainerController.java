package com.example.athleticore.controller.users;

import com.example.athleticore.entity.users.Client;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/trainers")
public class TrainerController {
    @GetMapping("/")
    public List<Client> getAllTrainers(){
        return Collections.emptyList();
    }

    @DeleteMapping("/{id}")
    public void deleteTrainerById(@PathVariable Long id){

    }
}
