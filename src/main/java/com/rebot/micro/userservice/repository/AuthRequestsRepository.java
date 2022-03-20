package com.rebot.micro.userservice.repository;

import com.rebot.micro.userservice.model.AuthRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRequestRepository extends JpaRepository<AuthRequest, Integer> {
    AuthRequest getAuthRequestByPhone(String phone);
    @Query("update AuthRequest set attempts = ?2 where id = ?1")
    void setAttemptsById(long id, int attempts);
}
