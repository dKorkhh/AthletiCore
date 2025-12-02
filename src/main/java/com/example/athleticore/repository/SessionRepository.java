package com.example.athleticore.repository;

import com.example.athleticore.dto.sessions.SessionDto;
import com.example.athleticore.entity.Session;
import com.example.athleticore.entity.users.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Optional<Session> findSessionByTrainer(User trainer);

    List<Session> findSessionsByTrainer(User trainer);

    Optional<Session> findSessionById(Long id);
}
