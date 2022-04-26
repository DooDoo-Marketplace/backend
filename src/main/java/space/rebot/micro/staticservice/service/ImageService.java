package space.rebot.micro.staticservice.service;

import liquibase.pro.packaged.F;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import space.rebot.micro.staticservice.repository.ImagesRepository;
import space.rebot.micro.userservice.model.Session;
import space.rebot.micro.userservice.model.User;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class ImageService implements FilesStorageService {
    @Autowired
    private ImagesRepository imagesRepository;

    private final Path root = Paths.get("static");

    @Value("${upload.path.dev}")
    private String uploadPath;

    public String addImage(
            MultipartFile file
    ) {
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
        {
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "_" + file.getOriginalFilename();

            try {
                file.transferTo(new File(uploadPath + "/" + resultFilename));
            } catch (IOException e) {
                e.printStackTrace();
            }

            return resultFilename;
            //message.setFilename(resultFilename);
        }
//        return "ERROR";
    }

    public void deleteImage(UUID id) {
        //User user = ((Session) context.getAttribute(Session.SESSION)).getUser();
        imagesRepository.deleteById(id);
    }

//    private final Path root = Paths.get("uploads");
    @Override
    public void init() {
        try {
            Files.createDirectory(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }
    @Override
    public String save(MultipartFile file) {
        try {
            file.transferTo(new File(uploadPath + "/" + file.getOriginalFilename()));
            return file.getOriginalFilename();
        } catch (Exception e) {
           return "ERROR";
        }
    }

    @Override
    public Resource load(String filename) {
        return null;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public Stream<Path> loadAll() {
        return null;
    }

    public void test() {
//        List<UUID> app = imagesRepository.getUUIDByName();
//
//        System.out.println("biba");
//        int a = 10;
//        a++;
    }
}
