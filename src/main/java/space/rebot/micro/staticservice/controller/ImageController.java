package space.rebot.micro.staticservice.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import space.rebot.micro.staticservice.exception.FileIsAlreadyExistException;
import space.rebot.micro.staticservice.exception.ImageTransferException;
import space.rebot.micro.staticservice.service.ImageService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/static")
public class ImageController {
    private final Logger logger = LogManager.getLogger("MyLogger");

    @Autowired
    private ImageService imageService;

    @PostMapping(value = "add-image")
    private ResponseEntity<?> add(
            @RequestParam("file") MultipartFile file
    ) {
        Map<Object, Object> model = new HashMap<>();
        try {
            String response = imageService.addImage(file);
            model.put("file_id", response);
        } catch (FileIsAlreadyExistException e) {
            model.put("error", e.getMessage());
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            logger.error(e.getStackTrace());
            e.printStackTrace();
            ImageTransferException exception = new ImageTransferException();
            model.put("error", exception.getMessage());
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @PostMapping(value = "delete-by-id", produces = "application/json")
    private ResponseEntity<?> delete(
            @RequestParam("id") UUID id
    ) {
        Map<Object, Object> model = new HashMap<>();
        try {
            imageService.deleteImage(id);
        } catch (Exception e) {
            model.put("error", e.getMessage());
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

}
