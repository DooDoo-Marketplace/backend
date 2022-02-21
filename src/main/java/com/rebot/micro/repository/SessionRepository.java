package com.rebot.micro.repository;

import com.rebot.micro.model.Session;
import com.rebot.micro.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface SessionRepository extends JpaRepository<Session, Integer> {
    Session getSessionByToken(String token);
}
