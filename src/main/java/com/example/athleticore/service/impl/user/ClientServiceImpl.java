package com.example.athleticore.service.impl.user;

import com.example.athleticore.dto.user.ClientDto;
import com.example.athleticore.entity.users.Client;
import com.example.athleticore.mapper.ClientMapper;
import com.example.athleticore.repository.ClientRepository;
import com.example.athleticore.service.user.ClientService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private static final Logger logger = LogManager.getLogger(ClientServiceImpl.class);
    private static final Marker USER_OPS = MarkerManager.getMarker("USER_OPERATION");

    @Override
    public Client addUser(ClientDto clientDto) {
        ThreadContext.put("operation", "addUser");
        try {
            Client client = clientMapper.toEntity(clientDto);
            Client savedClient = clientRepository.save(client);
            ThreadContext.put("clientId", savedClient.getId().toString());
            logger.info(USER_OPS, "Successfully added user with ID: {}", savedClient.getId());
            return savedClient;
        } catch (Exception e) {
            logger.error(USER_OPS, "Failed to add user with email: {}. Error: {}", clientDto.getEmail(), e.getMessage());
            throw e;
        } finally {
            ThreadContext.clearMap();
        }
    }

    @Override
    public Optional<Client> getUserById(Long id) {
        ThreadContext.put("operation", "getUserById");
        ThreadContext.put("clientId", id.toString());
        try {
            Optional<Client> client = clientRepository.findById(id);
            if (client.isPresent()) {
                logger.info(USER_OPS, "Found user with ID: {}", id);
            } else {
                logger.warn(USER_OPS, "No user found with ID: {}", id);
            }
            return client;
        } catch (Exception e) {
            logger.error(USER_OPS, "Error fetching user with ID: {}. Error: {}", id, e.getMessage());
            throw e;
        } finally {
            ThreadContext.clearMap();
        }
    }

    @Override
    public List<Client> getAllUser() {
        ThreadContext.put("operation", "getAllUser");
        try {
            List<Client> clients = clientRepository.findAll();
            logger.info(USER_OPS, "Retrieved {} users", clients.size());
            return clients;
        } catch (Exception e) {
            logger.error(USER_OPS, "Error fetching all users: {}", e.getMessage());
            throw e;
        } finally {
            ThreadContext.clearMap();
        }
    }
}