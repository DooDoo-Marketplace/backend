package space.rebot.micro.staticservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import space.rebot.micro.staticservice.model.Image;

import java.util.List;
import java.util.UUID;

@Repository
public interface ImagesRepository extends JpaRepository<Image, UUID> {

    @Query(value = "SELECT images.id from images", nativeQuery = true)
    public List<UUID> getUUIDByName();
}
