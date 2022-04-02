package space.rebot.micro.staticservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import space.rebot.micro.staticservice.model.Image;

import java.util.UUID;

@Repository
public interface ImagesRepository extends JpaRepository<Image, UUID> {

}
