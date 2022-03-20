package com.rebot.micro.userservice.repository;

import com.rebot.micro.userservice.model.AuthRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRequestsRepository extends JpaRepository<AuthRequest, Long> {
    AuthRequest getAuthRequestByPhone(String phone);
    @Query(value = "update auth_requests set attempts = ?2 where id = ?1", nativeQuery = true)
    void setAttemptsById(long id, int attempts);
}
