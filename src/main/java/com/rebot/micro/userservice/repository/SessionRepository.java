package com.rebot.micro.userservice.repository;

import com.rebot.micro.userservice.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Integer> {
    Session getSessionByToken(String token);
}
