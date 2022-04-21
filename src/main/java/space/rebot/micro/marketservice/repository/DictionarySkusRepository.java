package space.rebot.micro.marketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import space.rebot.micro.marketservice.model.WordSkus;
import space.rebot.micro.marketservice.model.WordSkusPk;

import java.util.List;

public interface DictionarySkusRepository extends JpaRepository<WordSkus, WordSkusPk> {

    @Query(value = "select sku_id from dictionary_skus where dictionary_skus.dictionary_id = :dictionaryId", nativeQuery = true)
    List<Long> getSkuIdsByDictionaryId(@Param("dictionaryId") Long dictionaryId);
}
