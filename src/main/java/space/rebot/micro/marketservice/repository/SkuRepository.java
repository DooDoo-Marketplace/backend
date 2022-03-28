package space.rebot.micro.marketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import space.rebot.micro.marketservice.model.Sku;

public interface SkuRepository extends JpaRepository<Sku, Long> {
    Sku getSkuById(Long id);
}
