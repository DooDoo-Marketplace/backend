package space.rebot.micro.staticservice.controller;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import org.hibernate.annotations.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import space.rebot.micro.staticservice.service.ImageService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("api/v1/static")
public class ImageController {

    @Autowired
    ImageService imageService;

    public ImageController() {
    }

    @PostMapping(value = "addImage"/*, produces=MediaType.IMAGE_JPEG_VALUE/*, produces = "application/json"/*, consumes = MediaType.APPLICATION_JSON_VALUEconsumes = {MULTIPART_FORM_DATA_VALUE}*/)
    private ResponseEntity<?> add(
            @RequestParam("file") MultipartFile file
    ) {
//        System.out.println(file.getOriginalFilename());
        Map<Object, Object> model = new HashMap<>();
        String response = imageService.save(file);
        model.put("filename uploading", response);
        model.put("success", true);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @DeleteMapping(value = "deleteById", produces = "application/json")
    private ResponseEntity<?> delete(
            @RequestParam(name = "id")
            UUID id
    ) {
        Map<Object, Object> model = new HashMap<>();
        imageService.deleteImage(id);
        model.put("success", true);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @GetMapping(value = "testing")
    private ResponseEntity<?> testingUUID() {
        Map<Object, Object> model = new HashMap<>();
        imageService.test();

        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
