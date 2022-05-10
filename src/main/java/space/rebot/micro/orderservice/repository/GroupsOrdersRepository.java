package space.rebot.micro.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import space.rebot.micro.orderservice.model.GroupsOrders;

import java.util.List;
import java.util.UUID;

public interface GroupsOrdersRepository extends JpaRepository<GroupsOrders, UUID> {
    @Query(value = "select * from groups_orders" +
            " where groups_orders_status_id in :listStatus limit :batch", nativeQuery = true)
    List<GroupsOrders> getOrderStatus(@Param("listStatus") List<Integer> listStatus, @Param("batch") int batch);
}
