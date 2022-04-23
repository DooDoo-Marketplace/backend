package space.rebot.micro.marketservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import space.rebot.micro.marketservice.dto.SkuDTO;
import space.rebot.micro.marketservice.dto.SkuIdDTO;
import space.rebot.micro.marketservice.exception.SkuNotFoundException;
import space.rebot.micro.marketservice.mapper.SkuMapper;
import space.rebot.micro.marketservice.service.FavoriteService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/")
public class FavoriteController {
    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private SkuMapper skuMapper;

    @GetMapping(value = "favorite", produces = "application/json")
    public ResponseEntity<?> getFavorite() {
        Map<Object, Object> model = new HashMap<>();
        List<SkuDTO> skuDTOList = favoriteService.getUserFavorite().stream()
                .map(sku -> skuMapper.mapToSkuDto(sku))
                .collect(Collectors.toList());
        model.put("success", true);
        model.put("favorite", skuDTOList);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @PostMapping(value = "favorite", produces = "application/json")
    public ResponseEntity<?> addFavorite(@RequestBody SkuIdDTO skuIdDTO) {
        Map<Object, Object> model = new HashMap<>();
        try {
            favoriteService.addFavorite(skuIdDTO.getSkuId());
        } catch (SkuNotFoundException e) {
            model.put("success", false);
            model.put("message", "Sku not found");
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        }
        model.put("success", true);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @DeleteMapping(value = "favorite", produces = "application/json")
    public ResponseEntity<?> deleteFavorite(@RequestBody SkuIdDTO skuIdDTO) {
        Map<Object, Object> model = new HashMap<>();
        favoriteService.deleteFavorite(skuIdDTO.getSkuId());
        model.put("success", true);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}