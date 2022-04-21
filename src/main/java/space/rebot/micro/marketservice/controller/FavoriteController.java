package space.rebot.micro.marketservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import space.rebot.micro.marketservice.dto.SkuDTO;
import space.rebot.micro.marketservice.mapper.SkuMapper;
import space.rebot.micro.marketservice.service.FavoriteService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/")
@Transactional
public class FavoriteController {
    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private SkuMapper skuMapper;

    @GetMapping(value = "favorite", produces = "application/json")
    @Transactional
    public ResponseEntity<?> favorite() {
        Map<Object, Object> model = new HashMap<>();
        List<SkuDTO> skuDTOList = favoriteService.getUserFavorite().stream()
                .map(sku -> skuMapper.mapToSkuDto(sku))
                .collect(Collectors.toList());
        model.put("success", true);
        model.put("favorite", skuDTOList);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
