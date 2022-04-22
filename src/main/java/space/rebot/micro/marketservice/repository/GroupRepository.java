package space.rebot.micro.marketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import space.rebot.micro.marketservice.model.Group;

import java.util.List;
import java.util.UUID;

public interface GroupRepository extends JpaRepository<Group, UUID> {

    @Query(value = "select * from groups g where g.sku_id = :skuId and region = :region", nativeQuery = true)
    Group getGroup(@Param("skuId") Long skuId, @Param("region") String region);

    @Transactional
    @Query(value = "select * from groups g where g.id in (select gs.group_id from groups_users gs where gs.user_id = :userId)", nativeQuery = true)
    List<Group> getUserGroups(@Param("userId") Long userId);

    @Transactional
    @Query(value = "select * from groups g where g.sku_id = :skuId and " +
            "g.id in (select gs.group_id from groups_users gs where gs.user_id = :userId)", nativeQuery = true)
    Group getUserGroupBySkuId(@Param("userId") Long userId, @Param("skuId") Long skuId);

    @Query(value = "select * from groups g where g.id = :id", nativeQuery = true)
    Group getGroup(@Param("id") UUID groupId);

    @Query(value = "select * from groups_users where group_id = :groupId and user_id = :userId", nativeQuery = true)
    Group existsUserInGroup(@Param("groupId") UUID groupId, @Param("userId") Long userId);

    @Query(value = "update groups set count = :count where id = :groupId", nativeQuery = true)
    @Modifying
    @Transactional
    int updateGroupCount(@Param("count") int count, @Param("groupId") UUID groupId);

    @Modifying
    @Transactional
    @Query(value = "delete from groups_users where group_id = :groupId and user_id = :userId", nativeQuery = true)
    int deleteUserFromGroup(@Param("groupId") UUID groupId, @Param("userId") Long userId);

}
