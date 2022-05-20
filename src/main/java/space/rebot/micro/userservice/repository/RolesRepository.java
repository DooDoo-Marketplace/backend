package space.rebot.micro.userservice.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import space.rebot.micro.userservice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Role, Long> {
    @Query(value = "select * from roles where name = :name", nativeQuery = true)
    Role getRoleByName(@Param("name") String name);
}
