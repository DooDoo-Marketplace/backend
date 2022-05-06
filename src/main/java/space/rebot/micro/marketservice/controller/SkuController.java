package space.rebot.micro.marketservice.controller;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import space.rebot.micro.marketservice.dto.SkuDTO;
import space.rebot.micro.marketservice.mapper.SkuMapper;
import space.rebot.micro.marketservice.model.Sku;
import space.rebot.micro.marketservice.service.SkuService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/sku")
public class SkuController {

    @Autowired
    private SkuService skuService;

    @Autowired
    private SkuMapper skuMapper;

    @GetMapping(value = "get", produces = "application/json")
    private ResponseEntity<?> getByName(@NonNull @RequestParam("name") String name, @RequestParam(value = "region", required = false) String region,
                                        @RequestParam(value = "lowPrice", required = false) String lowPrice,
                                        @RequestParam(value = "upperPrice", required = false) String upperPrice,
                                        @RequestParam(value = "rating", required = false) String rating,
                                        @RequestParam(value = "page") String page) {
        Map<Object, Object> model = new HashMap<>();
        try {
            List<SkuDTO> skuDTOList = skuService.getSku(name, region, lowPrice, upperPrice, rating, page).stream()
                    .map(sku -> skuMapper.mapToSkuDto(sku))
                    .collect(Collectors.toList());
            model.put("sku", skuDTOList);
        } catch (NumberFormatException e) {
            model.put("success", false);
            model.put("message", "Invalid request parameters");
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        }
        model.put("success", true);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
