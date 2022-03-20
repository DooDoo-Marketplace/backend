package com.rebot.micro.userservice.repository;

import com.rebot.micro.userservice.model.Session;
import com.rebot.micro.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionsRepository extends JpaRepository<Session, Long> {
    Session getSessionByToken(String token);

    @Query(value = "update sessions set expired = true where id = ?1", nativeQuery = true)
    void setExpirationById(Long id);

    @Query(value = "update sessions set expired = true where user = ?1", nativeQuery = true)
    void setExpirationByUser(User id);
}
