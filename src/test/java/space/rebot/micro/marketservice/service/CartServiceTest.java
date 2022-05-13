package space.rebot.micro.marketservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import space.rebot.micro.marketservice.enums.CartStatusEnum;
import space.rebot.micro.marketservice.exception.InvalidSkuCountException;
import space.rebot.micro.marketservice.exception.SkuIsOverException;
import space.rebot.micro.marketservice.exception.SkuNotFoundException;
import space.rebot.micro.marketservice.model.Cart;
import space.rebot.micro.marketservice.model.Sku;
import space.rebot.micro.marketservice.repository.CartRepository;
import space.rebot.micro.marketservice.repository.SkuRepository;
import space.rebot.micro.userservice.model.Session;
import space.rebot.micro.userservice.model.User;

import javax.servlet.http.HttpServletRequest;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private SkuRepository skuRepository;

    @Mock
    private HttpServletRequest context;

    @InjectMocks
    private CartService cartService;

    @Test
    void addSkuToCart_shouldThrowSkuNotFoundException() {
        Long skuId = 1L;
        int cnt = 0;
        boolean isRetail = true;

        Mockito.when(skuRepository.getSkuById(skuId)).thenReturn(null);

        assertThrows(SkuNotFoundException.class, () -> cartService.addSkuToCart(skuId, cnt, isRetail));
    }

    @Test
    void addSkuToCart_shouldThrowInvalidSkuCountException() {
        Long skuId = 1L;
        int cnt = 0;
        boolean isRetail = true;
        Sku sku = Mockito.mock(Sku.class);

        Mockito.when(skuRepository.getSkuById(skuId)).thenReturn(sku);

        assertThrows(InvalidSkuCountException.class, () -> cartService.addSkuToCart(skuId, cnt, isRetail));
    }

    @Test
    void addSkuToCart_shouldThrowSkuIsOverException_whenCntLessThanOrEqual0() {
        Long skuId = 1L;
        int cnt = 2;
        boolean isRetail = true;
        Sku sku = Mockito.mock(Sku.class);
        int skuCnt = 1;

        Mockito.when(skuRepository.getSkuById(skuId)).thenReturn(sku);
        Mockito.when(sku.getCount()).thenReturn(skuCnt);

        assertThrows(SkuIsOverException.class, () -> cartService.addSkuToCart(skuId, cnt, isRetail));
    }

    @Test
    void addSkuToCart_shouldThrowSkuIsOverException_whenCntPlusCartCountLessThanOrEqual0() {
        Long skuId = 1L;
        int cnt = 2;
        boolean isRetail = true;
        Sku sku = Mockito.mock(Sku.class);
        int skuCnt = 3;
        User user = Mockito.mock(User.class);
        Session session = Mockito.mock(Session.class);
        Long userId = 2L;
        Cart cart = Mockito.mock(Cart.class);
        int cartCnt = 3;

        Mockito.when(skuRepository.getSkuById(skuId)).thenReturn(sku);
        Mockito.when(sku.getCount()).thenReturn(skuCnt);
        Mockito.when(context.getAttribute(Mockito.any())).thenReturn(session);
        Mockito.when(session.getUser()).thenReturn(user);
        Mockito.when(user.getId()).thenReturn(userId);
        Mockito.when(cartRepository.getCartIdBySkuCartStatus(userId, skuId, CartStatusEnum.ACTIVE.getId(), isRetail)).thenReturn(cart);
        Mockito.when(cart.getCount()).thenReturn(cartCnt);

        assertThrows(SkuIsOverException.class, () -> cartService.addSkuToCart(skuId, cnt, isRetail));
    }

    @Test
    void updateCart_shouldThrowSkuNotFoundException() {
        Long skuId = 1L;
        int cnt = 2;
        boolean isRetail = true;
        Session session = Mockito.mock(Session.class);
        User user = Mockito.mock(User.class);
        Long userId = 2L;

        Mockito.when(context.getAttribute(Mockito.any())).thenReturn(session);
        Mockito.when(session.getUser()).thenReturn(user);
        Mockito.when(user.getId()).thenReturn(userId);
        Mockito.when(skuRepository.getSkuById(skuId)).thenReturn(null);

        assertThrows(SkuNotFoundException.class, () -> cartService.updateCart(skuId, cnt, isRetail));
    }

    @Test
    void updateCart_shouldThrowSkuIsOverException() {
        Long skuId = 1L;
        int cnt = 2;
        boolean isRetail = true;
        Session session = Mockito.mock(Session.class);
        User user = Mockito.mock(User.class);
        Long userId = 2L;
        Sku sku = Mockito.mock(Sku.class);
        int skuCnt = 1;

        Mockito.when(context.getAttribute(Mockito.any())).thenReturn(session);
        Mockito.when(session.getUser()).thenReturn(user);
        Mockito.when(user.getId()).thenReturn(userId);
        Mockito.when(skuRepository.getSkuById(skuId)).thenReturn(sku);
        Mockito.when(sku.getCount()).thenReturn(skuCnt);

        assertThrows(SkuIsOverException.class, () -> cartService.updateCart(skuId, cnt, isRetail));
    }

    @Test
    void getUserCart_shouldReturnEmptyList() {
        Session session = Mockito.mock(Session.class);
        User user = Mockito.mock(User.class);
        Long userId = 1L;

        Mockito.when(context.getAttribute(Mockito.any())).thenReturn(session);
        Mockito.when(session.getUser()).thenReturn(user);
        Mockito.when(user.getId()).thenReturn(userId);
        Mockito.when(cartRepository.getCartByUserIdAndCartStatus(userId, CartStatusEnum.ACTIVE.getId())).thenReturn(null);

        assertEquals(cartService.getUserCart(), Collections.emptyList());
    }
}