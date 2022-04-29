package space.rebot.micro.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import space.rebot.micro.orderservice.model.OrdersStatus;

public interface OrdersStatusRepository extends JpaRepository<OrdersStatus, Long> {

    @Query(value = "select * from orders_status where id = :id", nativeQuery = true)
    OrdersStatus getOrdersStatus(@Param("id") int id);
}
