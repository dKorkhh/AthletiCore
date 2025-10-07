package com.example.athleticore.controller.users;

import com.example.athleticore.dto.user.ClientDto;
import com.example.athleticore.entity.users.Client;
import com.example.athleticore.service.impl.user.ClientServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor()
public class ClientController {
    private final ClientServiceImpl clientService;

    @GetMapping("/")
    public List<Client> getAllClients(){
        return Collections.emptyList();
    }

    @PostMapping("/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Client addClient(@RequestBody ClientDto client){
        return clientService.addUser(client);
    }

    @DeleteMapping("/{id}")
    public void deleteClientById(@PathVariable Long id){

    }
}
