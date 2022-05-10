package space.rebot.micro.staticservice.service;

import liquibase.pro.packaged.F;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import space.rebot.micro.staticservice.exception.ImageCantBeDeleted;
import space.rebot.micro.staticservice.exception.ImageNotFoundException;
import space.rebot.micro.staticservice.model.Image;
import space.rebot.micro.staticservice.repository.ImagesRepository;
import space.rebot.micro.userservice.model.Session;
import space.rebot.micro.userservice.model.User;
import space.rebot.micro.userservice.service.DateService;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.UUID;

@Service
public class ImageService {
    private final Logger logger = LogManager.getLogger("MyLogger");

    @Autowired
    private HttpServletRequest context;

    @Autowired
    private DateService dateService;

    @Autowired
    private ImagesRepository imagesRepository;

    @Value("${upload.path}")
    private String uploadPath;

    public void deleteImage(UUID id) throws ImageNotFoundException, ImageCantBeDeleted {
        try {
            Image current = imagesRepository.getImageById(id);
            File file = new File(uploadPath + "/" + current.getFilename());
            if (file.delete()) {
                imagesRepository.deleteById(id);
            } else {
                throw new ImageCantBeDeleted();
            }
        } catch (ImageCantBeDeleted e) {
            throw e;
        } catch (Exception e)  {
            throw new ImageNotFoundException();
        }
    }

    public String addImage(MultipartFile file) {
        User user = ((Session) context.getAttribute(Session.SESSION)).getUser();
        String hashSum = "123";
//        try {
            // TODO CREATE HASH SUM
//            MessageDigest md = MessageDigest.getInstance("SHA-256");
//            md.update(Byte.parseByte(user.getId() + "_" + file.getName()));
//            byte[] digest = md.digest();
//            hashSum = DatatypeConverter.printHexBinary(digest);
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
        Image image = new Image(
                user.getId() + "_" + file.getOriginalFilename(),
                hashSum,
                file.getSize(),
                file.getContentType(),
                dateService.utcNow(),
                dateService.utcNow()
                );
        try {
            file.transferTo(new File(uploadPath + "/" + user.getId() + "_" + file.getOriginalFilename()));
            Image saved = imagesRepository.save(image);
            return saved.getId().toString();
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            e.printStackTrace();
        }
        return "ERROR";
    }

}
