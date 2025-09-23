package com.example.athleticore.controller.users;

import com.example.athleticore.entity.users.Client;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/clients")
public class ClientController {
    @GetMapping("/")
    public List<Client> getAllClients(){
        return Collections.emptyList();
    }

    @DeleteMapping("/{id}")
    public void deleteClientById(@PathVariable Long id){

    }
}
