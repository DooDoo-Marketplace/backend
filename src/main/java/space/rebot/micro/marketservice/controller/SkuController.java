package space.rebot.micro.marketservice.controller;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import space.rebot.micro.marketservice.dto.SkuDTO;
import space.rebot.micro.marketservice.service.SkuService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/sku")
public class SkuController {

    @Autowired
    private SkuService skuService;

    @GetMapping(value = "get", produces = "application/json")
    private ResponseEntity<?> getByName(@NonNull @RequestParam("name") String name, @RequestParam(value = "region", required = false) String region,
                                        @RequestParam(value = "lowPrice") Double lowPrice,
                                        @RequestParam(value = "upperPrice") Double upperPrice,
                                        @RequestParam(value = "rating") Double rating,
                                        @RequestParam(value = "limit") Integer limit,
                                        @RequestParam(value = "page") Integer page) {
        Map<Object, Object> model = new HashMap<>();
        List<SkuDTO> skuDTOList = skuService.getSku(name, region, lowPrice, upperPrice, rating, limit, page);
        model.put("sku", skuDTOList);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
