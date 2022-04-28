package space.rebot.micro.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import space.rebot.micro.orderservice.model.Orders;

import java.util.UUID;

public interface OrdersRepository extends JpaRepository<Orders, UUID> {

}
