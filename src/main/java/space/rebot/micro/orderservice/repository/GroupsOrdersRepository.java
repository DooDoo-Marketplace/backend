package space.rebot.micro.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import space.rebot.micro.orderservice.model.GroupsOrders;

import java.util.UUID;

public interface GroupsOrdersRepository extends JpaRepository<GroupsOrders, UUID> {
}
