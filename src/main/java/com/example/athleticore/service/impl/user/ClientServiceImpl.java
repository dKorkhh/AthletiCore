package com.example.athleticore.service.impl.user;

import com.example.athleticore.dto.user.ClientDto;
import com.example.athleticore.entity.users.Client;
import com.example.athleticore.entity.users.Trainer;
import com.example.athleticore.mapper.ClientMapper;
import com.example.athleticore.repository.ClientRepository;
import com.example.athleticore.service.user.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    public Client addUser(ClientDto clientDto) {
        Client client = clientMapper.toEntity(clientDto);
        clientRepository.save(client);

        return client;
    }

    @Override
    public Optional<Client> getUserById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Client> getAllUser() {
        return clientRepository.findAll();
    }
}
