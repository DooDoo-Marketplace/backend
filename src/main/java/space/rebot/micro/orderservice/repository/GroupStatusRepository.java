package space.rebot.micro.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import space.rebot.micro.orderservice.model.GroupStatus;

public interface GroupStatusRepository extends JpaRepository<GroupStatus, Long> {
    @Query(value = "select * from group_status gs where gs.name = :name", nativeQuery = true)
    GroupStatus getGroupStatus(@Param("name") String name);
}
