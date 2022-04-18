package space.rebot.micro.marketservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import space.rebot.micro.marketservice.dto.CartDTO;
import space.rebot.micro.marketservice.exception.InvalidSkuCountException;
import space.rebot.micro.marketservice.exception.SkuIsOverException;
import space.rebot.micro.marketservice.exception.SkuNotFoundException;
import space.rebot.micro.marketservice.mapper.CartMapper;
import space.rebot.micro.marketservice.service.CartService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/cart/")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartMapper cartMapper;

    @PostMapping(value = "add", produces = "application/json")
    public ResponseEntity<?> addSkuToCart(@RequestParam("skuId") Long skuId, @RequestParam("cnt") int cnt,
                                          @RequestParam("isRetail") boolean isRetail) {
        Map<Object, Object> model = new HashMap<>();
        try {
            cartService.addSkuToCart(skuId, cnt, isRetail);
        } catch (SkuIsOverException e) {
            model.put("success", false);
            model.put("message", "Sku is over");
            model.put("cnt", e.getCnt());
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        } catch (InvalidSkuCountException e) {
            model.put("success", false);
            model.put("message", "Sku count must be more than 0");
            model.put("cnt", e.getCnt());
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        } catch (SkuNotFoundException e) {
            model.put("success", false);
            model.put("message", "Sku not found");
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        }
        model.put("success", true);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @PostMapping(value = "update", produces = "application/json")
    //cnt is a new number of sku
    public ResponseEntity<?> updateSkuCnt(@RequestParam("skuId") Long skuId, @RequestParam("cnt") int cnt,
                                          @RequestParam("isRetail") boolean isRetail) {
        Map<Object, Object> model = new HashMap<>();
        try {
            cartService.updateCart(skuId, cnt, isRetail);
        } catch (SkuIsOverException e) {
            model.put("success", false);
            model.put("message", "Sku is over");
            model.put("cnt", e.getCnt());
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        } catch (SkuNotFoundException e) {
            model.put("success", false);
            model.put("message", "Sku not found");
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        }
        model.put("success", true);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @PostMapping(value = "delete", produces = "application/json")
    public ResponseEntity<?> deleteUserSkuInCart(@RequestParam("skuId") Long skuId,
                                                 @RequestParam("isRetail") boolean isRetail) {
        Map<Object, Object> model = new HashMap<>();
        cartService.deleteUserSkuInCart(skuId, isRetail);
        model.put("success", true);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @GetMapping(value = "get", produces = "application/json")
    public ResponseEntity<?> getUserCart() {
        Map<Object, Object> model = new HashMap<>();
        List<CartDTO> cartDTOList = cartService.getUserCart().stream()
                .map(cart -> cartMapper.mapToCartDto(cart))
                .collect(Collectors.toList());
        model.put("success", true);
        model.put("cart", cartDTOList);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

}
