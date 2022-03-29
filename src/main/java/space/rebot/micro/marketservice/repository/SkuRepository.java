package space.rebot.micro.marketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import space.rebot.micro.marketservice.model.Sku;

import java.util.List;

public interface SkuRepository extends JpaRepository<Sku, Long> {

    @Query(value = "select * from sku s where s.id in :ids", nativeQuery = true)
    List<Sku> getSkusByIds(@Param("ids") List<Long> ids);

    Sku getSkuById(Long id);
}
