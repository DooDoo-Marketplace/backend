package space.rebot.micro.marketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import space.rebot.micro.marketservice.model.Cart;
import space.rebot.micro.marketservice.model.Sku;
import space.rebot.micro.userservice.model.User;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository <Cart, Long> {
    List<Cart> getCartByUser(User user);
    List<Cart> getCartBySku(Sku sku);
}
