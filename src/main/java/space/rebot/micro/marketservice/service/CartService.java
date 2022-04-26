package space.rebot.micro.marketservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.rebot.micro.marketservice.enums.CartStatusEnum;
import space.rebot.micro.marketservice.exception.InvalidSkuCountException;
import space.rebot.micro.marketservice.exception.SkuIsOverException;
import space.rebot.micro.marketservice.exception.SkuNotFoundException;
import space.rebot.micro.marketservice.model.Cart;
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

    public void addSkuToCart(Long skuId, int cnt, boolean isRetail) throws SkuIsOverException, SkuNotFoundException, InvalidSkuCountException{
        Sku sku = skuRepository.getSkuById(skuId);
        if (sku == null) {
            throw new SkuNotFoundException();
        }
        if (cnt <= 0) {
            throw new InvalidSkuCountException(cnt);
        }
        int skuCnt = sku.getCount();
        if (skuCnt >= cnt) {
            User user = ((Session) context.getAttribute(Session.SESSION)).getUser();
            Cart cart = cartRepository.getCartIdBySkuCartStatus(user.getId(), skuId, CartStatusEnum.ACTIVE.getId(), isRetail);
            if (cart == null) {
                cart = new Cart(user, sku, cnt, cartStatusRepository.getCartStatus(CartStatusEnum.ACTIVE.getName()), isRetail);
                cartRepository.save(cart);
            } else {
                if (skuCnt >= cnt + cart.getCount()) {
                    cartRepository.setSkuCnt(user.getId(), skuId, cnt + cart.getCount(),
                            CartStatusEnum.ACTIVE.getId(), isRetail);
                } else {
                    throw new SkuIsOverException(skuCnt);
                }
            }
        } else {
            throw new SkuIsOverException(skuCnt);
        }
    }

    public void updateCart(Long skuId, int cnt, boolean isRetail) throws SkuIsOverException, SkuNotFoundException {
        User user = ((Session) context.getAttribute(Session.SESSION)).getUser();
        Long userId = user.getId();
        if (cnt <= 0) {
            deleteUserSkuInCart(skuId, isRetail);
            return;
        }
        Sku sku = skuRepository.getSkuById(skuId);
        if (sku == null) {
            throw new SkuNotFoundException();
        }
        int skuCnt = sku.getCount();
        if (skuCnt >= cnt) {
            cartRepository.setSkuCnt(userId, skuId, cnt, CartStatusEnum.ACTIVE.getId(), isRetail);
        } else {
            deleteUserSkuInCart(skuId, isRetail);
            throw new SkuIsOverException(skuCnt);
        }
    }

    public void deleteUserSkuInCart(Long skuId, boolean isRetail) {
        User user = ((Session) context.getAttribute(Session.SESSION)).getUser();
        cartRepository.updateCartStatus(user.getId(), skuId, CartStatusEnum.DELETED.getId(),
                CartStatusEnum.ACTIVE.getId(), isRetail);
    }

    public List<Cart> getUserCart() {
        User user = ((Session) context.getAttribute(Session.SESSION)).getUser();
        List<Cart> userCarts = cartRepository.getCartByUserIdAndCartStatus(user.getId(), CartStatusEnum.ACTIVE.getId());
        if (userCarts == null) {
            return Collections.emptyList();
        }
        return userCarts;
    }
}
