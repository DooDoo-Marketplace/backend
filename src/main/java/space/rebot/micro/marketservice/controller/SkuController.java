package space.rebot.micro.marketservice.controller;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import space.rebot.micro.marketservice.model.Sku;
import space.rebot.micro.marketservice.service.DictionaryService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/sku")
public class SkuController {

    @Autowired
    private DictionaryService dictionaryService;

    @GetMapping(value = "get", produces = "application/json")
    private ResponseEntity<?> getByName(@NonNull @RequestParam("name") String name) {
        Map<Object, Object> model = new HashMap<>();
        List<Sku> skus = dictionaryService.findSimilarWords(name);
        model.put("success", true);
        model.put("sku", skus);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
