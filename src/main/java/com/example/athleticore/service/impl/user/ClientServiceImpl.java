package com.example.athleticore.service.impl.user;

import com.example.athleticore.dto.user.ClientDto;
import com.example.athleticore.entity.users.Client;
import com.example.athleticore.service.ClientService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {
    @Override
    public void addClient(ClientDto clientDto) {

    }

    @Override
    public Optional<Client> getClientById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Client> getAllClients() {
        return List.of();
    }
}
