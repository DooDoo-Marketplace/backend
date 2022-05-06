package space.rebot.micro.marketservice.repository;

import org.springframework.data.domain.Example;
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

    @Query(value = "select * from sku s where s.id in (select sku_id from users_favorite uf where uf.user_id = :userId)", nativeQuery = true)
    List<Sku> getFavoriteSkusByUserId(@Param("userId") Long userId);

    @Query(value = "select case when count(uf) > 0 then true else false end from users_favorite uf where uf.user_id = :userId and uf.sku_id = :skuId", nativeQuery = true)
    boolean existsFavoriteBySkuId(@Param("userId") Long userId, @Param("skuId") Long skuId);

    Sku getSkuById(Long id);

    @Query(value = "select * from sku s where s.name like :name and s.region like :region and s.rating >= :rate and ((s.group_price >= :lPrice and s.group_price <= :uPrice) or (s.retail_price >= :lPrice and s.retail_price <= :uPrice)) limit :limit offset :offset", nativeQuery = true)
    List<Sku> getSkusByNameAndFilters(@Param("name") String name, @Param("region") String region, @Param("lPrice") Double lPrice, @Param("uPrice") Double uPrice,
                                      @Param("rate") Double rate,  @Param("limit") int limit, @Param("offset") int offset);
}
