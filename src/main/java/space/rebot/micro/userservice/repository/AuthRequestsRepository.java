package space.rebot.micro.userservice.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import space.rebot.micro.userservice.model.AuthRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRequestsRepository extends JpaRepository<AuthRequest, Long> {

    @Query(value = "select * from auth_requests where phone = :phone", nativeQuery = true)
    AuthRequest getAuthRequestByPhone(@Param("phone") String phone);

    @Modifying
    @Transactional
    @Query(value = "update auth_requests set attempts = :attempts where id = :id", nativeQuery = true)
    void setAttemptsById(@Param("id") long id, @Param("attempts") int attempts);
}
