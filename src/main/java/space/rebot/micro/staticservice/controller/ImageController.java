package space.rebot.micro.staticservice.controller;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import org.hibernate.annotations.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import space.rebot.micro.staticservice.service.ImageService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/static")
public class ImageController {

    @Autowired
    ImageService imageService;

    public ImageController() {
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
}
