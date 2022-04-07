package space.rebot.micro.marketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import space.rebot.micro.marketservice.model.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {

    @Query(value = "select * from groups g where g.sku_id = :skuId and region = :region", nativeQuery = true)
    Group getGroup(@Param("skuId") Long skuId, @Param("region") String region);

    @Query(value = "select * from groups g where g.id = :id", nativeQuery = true)
    Group getGroup(@Param("id") Long groupId);

    @Query(value = "select group_id from groups_users where group_id = :groupId and user_id = :userId", nativeQuery = true)
    Long existsUserInGroup(@Param("groupId") Long groupId, @Param("userId") Long userId);

    @Query(value = "update groups set count = :count where id = :groupId", nativeQuery = true)
    @Modifying
    @Transactional
    int updateGroupCount(@Param("count") int count, @Param("groupId") Long groupId);

}
