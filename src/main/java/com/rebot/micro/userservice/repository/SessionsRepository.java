package com.rebot.micro.userservice.repository;

import com.rebot.micro.userservice.model.Session;
import com.rebot.micro.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Integer> {
    Session getSessionByToken(String token);

    @Query("update Session set  expired = true where id = ?1")
    void setExpirationById(Long id);

    @Query("update Session set  expired = true where user = ?1")
    void setExpirationByUser(User id);
}
