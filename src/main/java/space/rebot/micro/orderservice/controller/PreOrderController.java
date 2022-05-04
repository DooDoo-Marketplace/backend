package space.rebot.micro.orderservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import space.rebot.micro.orderservice.dto.OrderRequestDTO;
import space.rebot.micro.orderservice.exception.CartCheckException;
import space.rebot.micro.orderservice.exception.PaymentException;
import space.rebot.micro.orderservice.exception.SkuGroupMatchException;
import space.rebot.micro.orderservice.service.PreOrderService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/order/")
public class PreOrderController {

    @Autowired
    private PreOrderService preOrderService;

    @PostMapping(value = "create", produces = "application/json")
    public ResponseEntity<?> createOrders(@RequestBody OrderRequestDTO orderRequestDTO) {
        Map<Object, Object> model = new HashMap<>();
        try {
            preOrderService.createOrders(orderRequestDTO);
        } catch (CartCheckException e) {
            model.put("success", false);
            model.put("invalidRegionSkuId", e.getInvalidRegionSkuId());
            model.put("invalidCountSkuId", e.getInvalidCountSkuId());
            model.put("message", "INVALID_CART");
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        } catch (SkuGroupMatchException e) {
            model.put("success", false);
            model.put("getInvalidGroupSkuId", e.getInvalidGroupSkuId());
            model.put("message", "INVALID_GROUPS");
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        } catch (PaymentException e) {
            model.put("success", false);
            model.put("message", "PAYMENT_PROBLEMS");
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        }
        model.put("success", true);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
