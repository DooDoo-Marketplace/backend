package space.rebot.micro.marketservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.rebot.micro.marketservice.enums.CartStatusEnum;
import space.rebot.micro.marketservice.exception.SkuIsOverException;
import space.rebot.micro.marketservice.exception.SkuNotFoundException;
import space.rebot.micro.marketservice.model.Cart;
import space.rebot.micro.marketservice.model.CartStatus;
import space.rebot.micro.marketservice.model.Sku;
import space.rebot.micro.marketservice.repository.CartRepository;
import space.rebot.micro.marketservice.repository.CartStatusRepository;
import space.rebot.micro.marketservice.repository.SkuRepository;
import space.rebot.micro.userservice.model.Session;
import space.rebot.micro.userservice.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private SkuRepository skuRepository;

    @Autowired
    private CartStatusRepository cartStatusRepository;

    @Autowired
    private HttpServletRequest context;

    public void addSkuToCart(Long skuId, int cnt) throws SkuIsOverException, SkuNotFoundException {
        Sku sku = skuRepository.getSkuById(skuId);
        if (sku == null) {
            throw new SkuNotFoundException();
        }
        int skuCnt = sku.getCount();
        if (skuCnt >= cnt) {
            User user = ((Session) context.getAttribute(Session.SESSION)).getUser();
            Cart cart = new Cart(user, sku, cnt, cartStatusRepository.getCartStatus(CartStatusEnum.ACTIVE.getName()));
            cartRepository.save(cart);
        } else {
            throw new SkuIsOverException(skuCnt);
        }
    }

    public void updateCart(Long skuId, int cnt) throws SkuIsOverException, SkuNotFoundException {
        User user = ((Session) context.getAttribute(Session.SESSION)).getUser();
        Long userId = user.getId();
        if (cnt == 0) {
            cartRepository.updateCartStatus(userId, skuId, CartStatusEnum.DELETED.getName(), CartStatusEnum.ACTIVE.getName());
            return;
        }
        Sku sku = skuRepository.getSkuById(skuId);
        if (sku == null) {
            throw new SkuNotFoundException();
        }
        int skuCnt = sku.getCount();
        if (skuCnt >= cnt) {
            cartRepository.updateSkuCnt(userId, skuId, cnt, CartStatusEnum.ACTIVE.getName());
        } else {
            cartRepository.updateCartStatus(userId, skuId, CartStatusEnum.DELETED.getName(),
                    CartStatusEnum.ACTIVE.getName());
            throw new SkuIsOverException(skuCnt);
        }
    }

    public void deleteUserSkuInCart(Long skuId) {
        User user = ((Session) context.getAttribute(Session.SESSION)).getUser();
        cartRepository.updateCartStatus(user.getId(), skuId, CartStatusEnum.DELETED.getName(),
                CartStatusEnum.ACTIVE.getName());
    }

    public List<Cart> getUserCart() {
        User user = ((Session) context.getAttribute(Session.SESSION)).getUser();
        Long cartStatusId = cartStatusRepository.getCartStatusId(CartStatusEnum.ACTIVE.getName());
        List<Cart> userCarts = cartRepository.getCartByUserIdAndCartStatus(user.getId(), cartStatusId);
        if (userCarts == null) {
            return Collections.emptyList();
        }
        return userCarts;
    }
}
