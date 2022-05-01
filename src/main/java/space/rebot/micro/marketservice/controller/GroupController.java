package space.rebot.micro.marketservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import space.rebot.micro.marketservice.dto.OrderRequestDTO;
import space.rebot.micro.marketservice.exception.*;
import space.rebot.micro.marketservice.model.Cart;
import space.rebot.micro.marketservice.service.GroupService;
import space.rebot.micro.marketservice.service.PreOrderCheckerService;
import space.rebot.micro.userservice.model.Session;
import space.rebot.micro.userservice.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("api/v1/group/")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private PreOrderCheckerService preOrderCheckerService;

    @Autowired
    private HttpServletRequest context;

    //пользователь передает регион и мапу с ску из корзины и номер группы, которой хочет присоединиться
    // это иммитация код приглашения в группу, надо будет переделать под uuid
    @PostMapping(value = "join", produces = "application/json")
    public ResponseEntity<?> joinGroup(@RequestBody OrderRequestDTO orderRequestDTOS) {
        Map<Object, Object> model = new HashMap<>();
        User user = ((Session) context.getAttribute(Session.SESSION)).getUser();
        if (orderRequestDTOS.getSkuGroup() == null) {
            orderRequestDTOS.setSkuGroup(Collections.emptyMap());
        }
        try {
            List<Cart> carts = preOrderCheckerService.check(orderRequestDTOS.getRegion(), user, orderRequestDTOS.getSkuGroup());
            model.put("groups", groupService.findGroups(carts, orderRequestDTOS.getSkuGroup(), user, orderRequestDTOS.getRegion()));
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
        } catch (GroupSearchException e) {
            model.put("success", false);
            model.put("message", "GROUP_SEARCH_ERROR");
            return new ResponseEntity<>(model, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        model.put("success", true);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @PostMapping(value = "leave", produces = "application/json")
    public ResponseEntity<?> leaveGroup(@RequestParam("groupId") UUID groupId) {
        Map<Object, Object> model = new HashMap<>();
        User user = ((Session) context.getAttribute(Session.SESSION)).getUser();
        try {
            groupService.leaveGroup(groupId, user);
        } catch (InvalidGroupException e) {
            model.put("success", false);
            model.put("message", e.getMessage());
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        }

        model.put("success", true);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

}
