package space.rebot.micro.userservice.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import space.rebot.micro.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    @Query(value = "select * from users where phone = :phone", nativeQuery = true)
    User getUserByPhone(@Param("phone") String phone);

    User getUserById(Long id);

    @Modifying
    @Transactional
    @Query(value = "insert into users_favorite (user_id, sku_id) values (:userId, :skuId)", nativeQuery = true)
    void addFavoriteByUserIdAndSkuId(@Param("userId") Long userId, @Param("skuId") Long skuId);

    @Modifying
    @Transactional
    @Query(value = "delete from users_favorite uf where uf.user_id = :userId and sku_id = :skuId", nativeQuery = true)
    int deleteFavoriteByUserIdAndFavoriteId(@Param("userId") Long userId, @Param("skuId") Long skuId);
}
