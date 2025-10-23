package com.example.athleticore.controller.users;

import com.example.athleticore.dto.patch.PatchDto;
import com.example.athleticore.dto.user.ClientDto;
import com.example.athleticore.entity.users.Client;
import com.example.athleticore.service.impl.user.ClientServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor()
public class ClientController {
    private final ClientServiceImpl clientService;

    @GetMapping("/")
    public List<Client> getAllClients(){
        return clientService.getAllUser();
    }

    @PostMapping("/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Client addClient(@RequestBody ClientDto client){
        return clientService.addUser(client);
    }

    @DeleteMapping("/")
    @ResponseStatus(code = HttpStatus.OK)
    public void deleteClientById(@RequestParam Long id){
        clientService.deleteUserById(id);
    }

    @PatchMapping("/")
    @ResponseStatus(code = HttpStatus.OK)
    public Client updateClient(@RequestBody PatchDto patchDto){
        return clientService.updateUser(patchDto);
    }
}
