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

    @Query(value = "select * from groups g where g.sku_id = :skuId and region = :region" +
            " and g.group_status_id = :status", nativeQuery = true)
    Group getGroup(@Param("skuId") Long skuId, @Param("region") String region, @Param("status") int status);

    @Transactional
    @Query(value = "select * from groups g where g.id in (select gs.group_id from groups_users gs where gs.user_id = :userId)", nativeQuery = true)
    List<Group> getUserGroups(@Param("userId") Long userId);

    @Transactional
    @Query(value = "select * from groups g where g.sku_id = :skuId and " +
            "g.id in (select gs.group_id from groups_users gs where gs.user_id = :userId)", nativeQuery = true)
    Group getUserGroupBySkuId(@Param("userId") Long userId, @Param("skuId") Long skuId);

    @Query(value = "select * from groups g where g.id = :id", nativeQuery = true)
    Group getGroup(@Param("id") UUID groupId);

    @Query(value = "select cast(group_id as varchar) from groups_users where group_id = :groupId and user_id = :userId", nativeQuery = true)
    String existsUserInGroup(@Param("groupId") UUID groupId, @Param("userId") Long userId);

    @Query(value = "update groups set count = :count where id = :groupId", nativeQuery = true)
    @Modifying
    @Transactional
    int updateGroupCount(@Param("count") int count, @Param("groupId") UUID groupId);

    @Modifying
    @Transactional
    @Query(value = "delete from groups_users where group_id = :groupId and user_id = :userId", nativeQuery = true)
    int deleteUserFromGroup(@Param("groupId") UUID groupId, @Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query(value = "update groups set group_status_id = :exposedStatusId " +
            "where id = :groupId", nativeQuery = true)
    int updateGroupStatus(@Param("groupId") UUID groupId, @Param("exposedStatusId") int exposedStatusId);

    @Query(value = "select * from groups g where g.group_status_id = :groupStatus limit :count", nativeQuery = true)
    List<Group> getGroupByStatus(@Param("groupStatus") int status, @Param("count") int count);

    @Modifying
    @Transactional
    @Query(value = "delete from groups_users where group_id in :ids " , nativeQuery = true)
    int deleteGroupsFromGroupsUsersByIds(@Param("ids") List<UUID> ids);

    @Modifying
    @Transactional
    @Query(value = "delete from groups where id in :ids ", nativeQuery = true)
    int deleteGroupsFromGroupsByIds(@Param("ids") List<UUID> ids);
}
