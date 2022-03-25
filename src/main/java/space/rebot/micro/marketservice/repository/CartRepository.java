package space.rebot.micro.marketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import space.rebot.micro.marketservice.model.Cart;

public interface CartRepository extends JpaRepository <Cart, Long> {
}
