package space.rebot.micro.marketservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import space.rebot.micro.marketservice.dto.SkuDTO;
import space.rebot.micro.marketservice.exception.SkuIsAlreadyFavoriteException;
import space.rebot.micro.marketservice.exception.SkuNotFoundException;
import space.rebot.micro.marketservice.mapper.SkuMapper;
import space.rebot.micro.marketservice.service.FavoriteService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/favorite")
public class FavoriteController {
    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private SkuMapper skuMapper;

    @GetMapping(value = "get", produces = "application/json")
    public ResponseEntity<?> getFavorite() {
        Map<Object, Object> model = new HashMap<>();
        List<SkuDTO> skuDTOList = favoriteService.getUserFavorite();
        model.put("success", true);
        model.put("favorite", skuDTOList);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @PostMapping(value = "add", produces = "application/json")
    public ResponseEntity<?> addFavorite(@RequestParam("skuId") Long skuId) {
        Map<Object, Object> model = new HashMap<>();
        try {
            favoriteService.addFavorite(skuId);
        }catch (SkuIsAlreadyFavoriteException e) {
            model.put("success", false);
            model.put("message", "SKU_IS_ALREADY_FAVORITE");
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        }catch (SkuNotFoundException e) {
            model.put("success", false);
            model.put("message", "SKU_NOT_FOUND");
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        }
        model.put("success", true);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @PostMapping(value = "delete", produces = "application/json")
    public ResponseEntity<?> deleteFavorite(@RequestParam("skuId") Long skuId) {
        Map<Object, Object> model = new HashMap<>();
        try {
            favoriteService.deleteFavorite(skuId);
        } catch (SkuNotFoundException e) {
            model.put("success", false);
            model.put("message", "SKU_NOT_FOUND");
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        }
        model.put("success", true);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}