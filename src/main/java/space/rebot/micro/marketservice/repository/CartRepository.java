package space.rebot.micro.marketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import space.rebot.micro.marketservice.model.Cart;
import space.rebot.micro.marketservice.model.Sku;
import space.rebot.micro.userservice.model.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository <Cart, Long> {

    @Query(value = "select sku_id from cart c " +
            "where c.user_id = :userId and c.is_deleted = false", nativeQuery = true)
    List<UUID> getSkuIdsByUserId(@Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query(value = "update cart c set count = :cnt " +
            "where c.user_id = :userId and c.sku_id = :skuId and c.is_deleted = false", nativeQuery = true)
    int updateSkuCnt(@Param("userId") Long userId, @Param("skuId") UUID skuId, @Param("cnt") int cnt);

    @Modifying
    @Transactional
    @Query(value = "update cart c set is_deleted = true " +
            "where c.user_id = :userId and c.sku_id = :skuId and c.is_deleted = false", nativeQuery = true)
    int markDelete(@Param("userId") Long userId, @Param("skuId") UUID skuId);
}
