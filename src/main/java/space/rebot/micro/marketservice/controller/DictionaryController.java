package space.rebot.micro.marketservice.controller;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import space.rebot.micro.marketservice.repository.DictionarySkusRepository;
import space.rebot.micro.marketservice.service.DictionaryService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/dict")
public class DictionaryController {

    @Autowired
    private DictionaryService dictionaryService;

    @PostMapping(value = "add", produces = "application/json")
    private ResponseEntity<?> addToDictionary(@RequestParam("sku_id") Long skuId, @RequestParam("name") String name) {
        Map<Object, Object> model = new HashMap<>();
        dictionaryService.addWordsToDictionary(name, skuId);
        model.put("success", true);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
