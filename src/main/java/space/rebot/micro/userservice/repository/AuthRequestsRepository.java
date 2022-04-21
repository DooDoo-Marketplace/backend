package space.rebot.micro.userservice.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import space.rebot.micro.userservice.model.AuthRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRequestsRepository extends JpaRepository<AuthRequest, Long> {
    AuthRequest getAuthRequestByPhone(String phone);


    @Modifying
    @Transactional
    @Query(value = "update auth_requests set attempts = ?2 where id = ?1", nativeQuery = true)
    void setAttemptsById(long id, int attempts);
}
