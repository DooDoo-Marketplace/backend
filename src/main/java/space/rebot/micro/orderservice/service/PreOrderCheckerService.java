package space.rebot.micro.orderservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.rebot.micro.marketservice.enums.CartStatusEnum;
import space.rebot.micro.orderservice.enums.GroupStatusEnum;
import space.rebot.micro.orderservice.exception.CartCheckException;
import space.rebot.micro.orderservice.exception.SkuGroupMatchException;
import space.rebot.micro.marketservice.model.Cart;
import space.rebot.micro.orderservice.model.Group;
import space.rebot.micro.marketservice.model.Sku;
import space.rebot.micro.marketservice.repository.CartRepository;
import space.rebot.micro.orderservice.repository.GroupRepository;
import space.rebot.micro.marketservice.repository.SkuRepository;
import space.rebot.micro.userservice.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PreOrderCheckerService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private SkuRepository skuRepository;

    //проверяем валидные ли пришли данные
    public List<Cart> check(String region, User user, Map<Long, UUID> skuGroups) throws CartCheckException, SkuGroupMatchException {
        List<Long> invalidGroupSkuId = checkSkuGroups(skuGroups);
        List<Cart> carts = checkUserCart(region, user);
        if (invalidGroupSkuId.isEmpty()) {
            freezeSkus(carts);
            return carts;
        }
        throw new SkuGroupMatchException(invalidGroupSkuId);
    }

    @Transactional
    public void freezeSkus(List<Cart> carts) {
        carts.forEach(cart -> skuRepository.updateSkuCount(-1 * cart.getCount(), cart.getSku().getId()));
    }


    // проверка, что все группы существуют и состоят из данного товара
    public List<Long> checkSkuGroups(Map<Long, UUID> skuGroups) {
        List<Long> invalidGroupSkuId = new ArrayList<>();
        for (Map.Entry<Long, UUID> skuGroup : skuGroups.entrySet()) {
            Group group = groupRepository.getGroup(skuGroup.getValue());
            if (group == null) {
                invalidGroupSkuId.add(skuGroup.getKey());
                continue;
            }
            Sku sku = group.getSku();
            if (!skuGroup.getKey().equals(sku.getId()) ||
                    group.getGroupStatus().getId() != GroupStatusEnum.ACTIVE.getId() &&
                            group.getGroupStatus().getId() != GroupStatusEnum.EXTRA.getId()) {
                invalidGroupSkuId.add(skuGroup.getKey());
            }
        }
        return invalidGroupSkuId;
    }

    // проверка корзины
    private List<Cart> checkUserCart(String region, User user) throws CartCheckException {
        List<Cart> carts = cartRepository.getCartByUserIdAndCartStatus(user.getId(), CartStatusEnum.ACTIVE.getId());
        List<Long> invalidRegionSkuId = checkUserCartRegions(carts, region);
        List<Long> invalidCountSkuId = checkUserCartSkuCount(carts);
        if (invalidCountSkuId.isEmpty() && invalidRegionSkuId.isEmpty()) {
            return carts;
        }
        if (!invalidCountSkuId.isEmpty()) {
            cartRepository.updateCartStatus(user.getId(), invalidCountSkuId,
                    CartStatusEnum.DELETED.getId(), CartStatusEnum.ACTIVE.getId(), false);
        }
        throw new CartCheckException(invalidRegionSkuId, invalidCountSkuId);
    }

    // проверка, что все товары в корзине в регионе ску
    public List<Long> checkUserCartRegions(List<Cart> carts, String region) {
        List<Long> invalidRegionSkuId = new ArrayList<>();
        carts.forEach(cart -> {
            Sku sku = cart.getSku();
            if (!sku.getRegion().equals(region)) {
                invalidRegionSkuId.add(sku.getId());
            }
        });
        return invalidRegionSkuId;
    }

    // проверка, что на складе есть такое количество товара
    public List<Long> checkUserCartSkuCount(List<Cart> carts) {
        List<Long> invalidCountSkuId = new ArrayList<>();
        carts.forEach(cart -> {
            Sku sku = cart.getSku();
            if (sku.getCount() < cart.getCount()) {
                invalidCountSkuId.add(sku.getId());
            }
        });
        return invalidCountSkuId;
    }
}
