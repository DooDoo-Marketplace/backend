package space.rebot.micro.marketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import space.rebot.micro.marketservice.model.Review;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query(value = "select * from reviews where sku_id = :skuId", nativeQuery = true)
    List<Review> getReviewBySkuId(@Param("skuId") Long skuId);

    @Modifying
    @Transactional
    @Query(value = "update reviews r set text = :text, photo_url = :photoUrl, " +
            "rating = :rating where r.id = :id", nativeQuery = true)
    int updateReview(@Param("text") String text, @Param("photoUrl") String photoUrl,
                                 @Param("rating") Double rating, @Param("id") UUID id);

    @Modifying
    @Transactional
    @Query(value = "delete from reviews r where r.id = :id", nativeQuery = true)
    int deleteById(@Param("id") UUID id);


    @Query(value = "select * from reviews where id = :id", nativeQuery = true)
    Review getReviewById(@Param("id") UUID id);
}
