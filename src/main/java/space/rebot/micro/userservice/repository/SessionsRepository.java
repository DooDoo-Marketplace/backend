package space.rebot.micro.userservice.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import space.rebot.micro.userservice.model.Session;
import space.rebot.micro.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionsRepository extends JpaRepository<Session, Long> {

    @Query(value = "select * from sessions where token = :token", nativeQuery = true)
    Session getByToken(@Param("token") String token);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update sessions set expired = true where id = :id", nativeQuery = true)
    void setExpirationById(@Param("id") long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update sessions set expired = true where user_id = :id",
            nativeQuery = true)
    void setExpirationByUser(@Param("id") long id);
}
