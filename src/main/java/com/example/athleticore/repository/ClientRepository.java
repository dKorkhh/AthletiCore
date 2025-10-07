package com.example.athleticore.repository;

import com.example.athleticore.entity.users.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
