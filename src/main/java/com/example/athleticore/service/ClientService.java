package com.example.athleticore.service;

import com.example.athleticore.dto.user.ClientDto;
import com.example.athleticore.entity.users.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    void addClient(ClientDto clientDto);
    Optional<Client> getClientById(Long id);
    List<Client> getAllClients();
}
