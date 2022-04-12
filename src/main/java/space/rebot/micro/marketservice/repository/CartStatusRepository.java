package space.rebot.micro.marketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import space.rebot.micro.marketservice.model.CartStatus;

public interface CartStatusRepository extends JpaRepository<CartStatus, Long> {

    @Query(value = "select cs.id from cart_status cs where cs.name = :name", nativeQuery = true)
    int getCartStatusId(@Param("name") String name);

    @Query(value = "select * from cart_status cs where cs.name = :name", nativeQuery = true)
    CartStatus getCartStatus(@Param("name") String name);
}
