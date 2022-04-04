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

@Repository
public interface CartRepository extends JpaRepository <Cart, Long> {

    @Query(value = "select sku_id from cart c " +
            "where c.user_id = :userId and c.is_deleted = false", nativeQuery = true)
    List<Long> getSkuIdsByUserId(@Param("userId") Long userId);

    @Query(value = "select * from cart where user_id = :userId and sku_id = :skuId and cart_status_id = :cartStatusId", nativeQuery = true)
    Cart getCartIdBySkuCartStatus(@Param("userId") Long userId, @Param("skuId") Long skuId,
                                          @Param("cartStatusId") int cartStatusId);

    @Query(value = "select * from cart c where c.user_id = :userId and c.cart_status_id = :cartStatusId", nativeQuery = true)
    List<Cart> getCartByUserIdAndCartStatus(@Param("userId") Long userId, @Param("cartStatusId") int cartStatusId);

    @Modifying
    @Transactional
    @Query(value = "update cart c set count = :cnt " +
            "where c.user_id = :userId and c.sku_id = :skuId and c.cart_status_id = :cartStatusId", nativeQuery = true)
    int updateSkuCnt(@Param("userId") Long userId, @Param("skuId") Long skuId, @Param("cnt") int cnt,
                             @Param("cartStatusId") int cartStatusId);

    @Transactional
    @Query(value = "select c.id from cart c where c.cart_status_id = (select cs.id from cart_status cs where cs.name = :cartStatus) limit :count", nativeQuery = true)
    List<Long> getCartByDeletedStatus(@Param("cartStatus") String status, @Param("count") int count);

    @Modifying
    @Transactional
    @Query(value = "delete from cart c where c.id in :ids", nativeQuery = true)
    int deleteCartByIdList(@Param("ids") List<Long> ids);
    @Query(value = "update cart c set cart_status_id = :exposedStatusId " +
            "where c.sku_id = :skuId and c.user_id = :userId and c.cart_status_id = :updatedStatusId", nativeQuery = true)
    int updateCartStatus(@Param("userId") Long userId, @Param("skuId") Long skuId, @Param("exposedStatusId") int exposedStatusId,
                         @Param("updatedStatusId") int updatedStatusId);
}
