package space.rebot.micro.userservice.repository;

import space.rebot.micro.userservice.model.Session;
import space.rebot.micro.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionsRepository extends JpaRepository<Session, Long> {

    @Query(value = "select * from sessions where token = ?1", nativeQuery = true)
    Session getByToken(String token);

    @Query(value = "update sessions set expired = true where id = ?1", nativeQuery = true)
    void setExpirationById(Long id);

    @Query(value = "update sessions set expired = true where user = ?1", nativeQuery = true)
    void setExpirationByUser(User id);
}
