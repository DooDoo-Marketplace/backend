package space.rebot.micro.userservice.repository;

import space.rebot.micro.userservice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Role, Long> {
    public Role getRoleByName(String name);
}