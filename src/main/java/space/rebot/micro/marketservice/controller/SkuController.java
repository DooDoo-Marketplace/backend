package space.rebot.micro.marketservice.controller;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import space.rebot.micro.marketservice.dto.SearchDTO;
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
    private ResponseEntity<?> get(@RequestBody SearchDTO searchDTO) {
        Map<Object, Object> model = new HashMap<>();
        List<SkuDTO> skuDTOList = skuService.getSku(searchDTO.getName(), searchDTO.getRegion(), searchDTO.getMinPrice(),
                searchDTO.getMaxPrice(), searchDTO.getRating(), searchDTO.getLimit(), searchDTO.getPage());
        model.put("sku", skuDTOList);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
