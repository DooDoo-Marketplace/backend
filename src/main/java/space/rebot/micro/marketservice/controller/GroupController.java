package space.rebot.micro.marketservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import space.rebot.micro.marketservice.dto.GroupDTO;
import space.rebot.micro.marketservice.exception.CartCheckException;
import space.rebot.micro.marketservice.exception.SkuGroupMatchException;
import space.rebot.micro.marketservice.model.Cart;
import space.rebot.micro.marketservice.service.PreGroupCheckerService;
import space.rebot.micro.marketservice.service.GroupService;
import space.rebot.micro.marketservice.service.PaymentService;
import space.rebot.micro.userservice.model.Session;
import space.rebot.micro.userservice.model.User;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/group/")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private PreGroupCheckerService preGroupCheckerService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private HttpServletRequest context;

    @PostMapping(value = "join", produces = "application/json")
    public ResponseEntity<?> joinGroup(@RequestBody GroupDTO groupDTOS) {
        Map<Object, Object> model = new HashMap<>();
        User user = ((Session) context.getAttribute(Session.SESSION)).getUser();
        if (groupDTOS.getSkuGroup() == null) {
            groupDTOS.setSkuGroup(Collections.emptyMap());
        }
        try {
            List<Cart> carts = preGroupCheckerService.check(groupDTOS.getRegion(), user, groupDTOS.getSkuGroup());
            model.put("skuGroups", groupService.findGroups(carts, groupDTOS.getSkuGroup(), user, groupDTOS.getRegion()));
        } catch (CartCheckException e) {
            model.put("success", false);
            model.put("invalidRegionSkuId", e.getInvalidRegionSkuId());
            model.put("invalidCountSkuId", e.getInvalidCountSkuId());
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        } catch (SkuGroupMatchException e) {
            model.put("success", false);
            model.put("getInvalidGroupSkuId", e.getInvalidGroupSkuId());
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        } catch (ParseException e) {
            model.put("success", false);
            model.put("message", "Payment problems");
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        }
        model.put("success", true);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
