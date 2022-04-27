package space.rebot.micro.staticservice.service;

import liquibase.pro.packaged.A;
import liquibase.pro.packaged.F;
import liquibase.pro.packaged.I;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.postgresql.util.MD5Digest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import space.rebot.micro.staticservice.model.Image;
import space.rebot.micro.staticservice.repository.ImagesRepository;
import space.rebot.micro.userservice.model.Session;
import space.rebot.micro.userservice.model.User;
import space.rebot.micro.userservice.service.DateService;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class ImageService/* implements FilesStorageService*/ {
    private final Logger logger = LogManager.getLogger("MyLogger");

    @Autowired
    private HttpServletRequest context;

    @Autowired
    private DateService dateService;

    @Autowired
    private ImagesRepository imagesRepository;

    @Value("${upload.path.dev}")
    private String uploadPath;

//    public String addImage(
//            MultipartFile file
//    ) {
//        try {
//            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
//            file.transferTo(new File(uploadPath + "/" + file.getOriginalFilename()));
//        } catch (Exception e) {
//            return "ERROR";
//        }
//        return "SUCCESS";

//        if (file != null && !file.getOriginalFilename().isEmpty()) {
//            File uploadDir = new File(uploadPath);
//
//            if (!uploadDir.exists()) {
//                uploadDir.mkdir();
//            }
//
//        {
//            String uuidFile = UUID.randomUUID().toString();
//            String resultFilename = uuidFile + "_" + file.getOriginalFilename();
//
//            try {
//                file.transferTo(new File(uploadPath + "/" + resultFilename));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            return resultFilename;
//        }
//    }

    public void deleteImage(UUID id) {
        //User user = ((Session) context.getAttribute(Session.SESSION)).getUser();
        imagesRepository.deleteById(id);
    }

//    private final Path root = Paths.get("uploads");
//    @Override
//    public void init() {
//
//    }

//    @Override
    public String addImage(MultipartFile file) {
        User user = ((Session) context.getAttribute(Session.SESSION)).getUser();
        String hashSum = "123";//null;
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
                user.getId() + "_" + file.getName(),
                hashSum,
                file.getSize(),
                file.getContentType(),
                dateService.utcNow(),
                dateService.utcNow()
                );
        try {
            file.transferTo(new File(uploadPath + "/" + user.getId() + "_" + file.getName()));
            Image saved = imagesRepository.save(image);
            return saved.getId().toString();
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            e.printStackTrace();
        }
        return "ERROR";
    }

//    @Override
//    public Resource load(String filename) {
//        return null;
//    }
//
//    @Override
//    public void deleteAll() {
//
//    }
//
//    @Override
//    public Stream<Path> loadAll() {
//        return null;
//    }

}
