package space.rebot.micro.marketservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private SkuRepository skuRepository;

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
            Cart cart = new Cart(user, sku, cnt, false);
            cartRepository.save(cart);
        } else {
            throw new SkuIsOverException(skuCnt);
        }
    }

    public void updateCart(Long skuId, int cnt) throws SkuIsOverException, SkuNotFoundException {
        User user = ((Session) context.getAttribute(Session.SESSION)).getUser();
        Long userId = user.getId();
        if (cnt == 0) {
            cartRepository.markDelete(userId, skuId);
            return;
        }
        Sku sku = skuRepository.getSkuById(skuId);
        if (sku == null) {
            throw new SkuNotFoundException();
        }
        int skuCnt = sku.getCount();
        if (skuCnt >= cnt) {
            cartRepository.updateSkuCnt(userId, skuId, cnt);
        } else {
            throw new SkuIsOverException(skuCnt);
        }
    }

    public void deleteUserSkuInCart(Long skuId) {
        User user = ((Session) context.getAttribute(Session.SESSION)).getUser();
        cartRepository.markDelete(user.getId(), skuId);
    }

    public List<Sku> getUsersSku() {
        User user = ((Session) context.getAttribute(Session.SESSION)).getUser();
        List<Long> ids = cartRepository.getSkuIdsByUserId(user.getId());
        if (ids == null) {
            return Collections.emptyList();
        }
        return skuRepository.getSkusByIds(ids);
    }
}
