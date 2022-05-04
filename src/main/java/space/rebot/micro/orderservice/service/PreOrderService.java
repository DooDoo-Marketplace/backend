package space.rebot.micro.orderservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.rebot.micro.orderservice.dto.OrderRequestDTO;
import space.rebot.micro.orderservice.exception.CartCheckException;
import space.rebot.micro.orderservice.exception.PaymentException;
import space.rebot.micro.orderservice.exception.SkuGroupMatchException;
import space.rebot.micro.marketservice.model.Cart;
import space.rebot.micro.orderservice.enums.OrdersStatusEnum;
import space.rebot.micro.orderservice.model.GroupsOrders;
import space.rebot.micro.orderservice.model.Orders;
import space.rebot.micro.orderservice.model.OrdersStatus;
import space.rebot.micro.orderservice.repository.GroupsOrdersRepository;
import space.rebot.micro.orderservice.repository.OrdersRepository;
import space.rebot.micro.orderservice.repository.OrdersStatusRepository;
import space.rebot.micro.userservice.model.Session;
import space.rebot.micro.userservice.model.User;
import space.rebot.micro.userservice.service.DateService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PreOrderService {

    @Autowired
    private PreOrderCheckerService preOrderCheckerService;

    @Autowired
    private OrdersStatusRepository ordersStatusRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private DateService dateService;

    @Autowired
    private GroupsOrdersRepository groupsOrdersRepository;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private HttpServletRequest context;

    @Transactional(rollbackFor = {CartCheckException.class, SkuGroupMatchException.class, PaymentException.class})
    public void createOrders(OrderRequestDTO orderRequestDTOS) throws CartCheckException, SkuGroupMatchException, PaymentException {
        //check Cart
        Map<Object, Object> model = new HashMap<>();
        User user = ((Session) context.getAttribute(Session.SESSION)).getUser();
        if (orderRequestDTOS.getSkuGroup() == null) {
            orderRequestDTOS.setSkuGroup(Collections.emptyMap());
        }
        String region = orderRequestDTOS.getRegion();
        List<Cart> carts = preOrderCheckerService.check(region, user,
                orderRequestDTOS.getSkuGroup());

        // create orders
        OrdersStatus waitingPaymentStatus = ordersStatusRepository.getOrdersStatus(OrdersStatusEnum.WAITING_PAYMENT.getId());
        carts.stream()
                .filter(Cart::isRetail)
                .forEach((cart) -> ordersRepository.save(new Orders(cart, waitingPaymentStatus,
                        dateService.utcNow(), dateService.utcNow(), user, region)));
        carts.stream()
                .filter(cart -> !cart.isRetail())
                .forEach((cart) -> groupsOrdersRepository.save(new GroupsOrders(null, waitingPaymentStatus,
                        cart, dateService.utcNow(), dateService.utcNow(), user, region)));
        // pay
        paymentService.spend();
    }
}
