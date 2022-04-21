package space.rebot.micro.marketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import space.rebot.micro.marketservice.model.Sku;

import java.util.List;

public interface SkuRepository extends JpaRepository<Sku, Long> {

    @Modifying
    @Transactional
    @Query(value = "update sku set count = count + :count where id = :skuId", nativeQuery = true)
    int updateSkuCount(@Param("count") int count, @Param("skuId") Long skuId);

    @Query(value = "select * from sku s where s.id in :ids", nativeQuery = true)
    List<Sku> getSkusByIds(@Param("ids") List<Long> ids);

    Sku getSkuById(Long id);
}
