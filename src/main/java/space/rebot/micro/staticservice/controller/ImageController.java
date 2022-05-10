package space.rebot.micro.staticservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import space.rebot.micro.staticservice.exception.ImageNotFoundException;
import space.rebot.micro.staticservice.service.ImageService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/static")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping(value = "add-image")
    private ResponseEntity<?> add(
            @RequestParam("file") MultipartFile file
    ) {
        Map<Object, Object> model = new HashMap<>();
        String response = imageService.addImage(file);
        model.put("file_id", response);
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
