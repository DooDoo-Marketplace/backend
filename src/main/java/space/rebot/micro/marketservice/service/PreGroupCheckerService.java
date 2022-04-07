package space.rebot.micro.marketservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.rebot.micro.marketservice.enums.CartStatusEnum;
import space.rebot.micro.marketservice.exception.CartCheckException;
import space.rebot.micro.marketservice.exception.SkuGroupMatchException;
import space.rebot.micro.marketservice.model.Cart;
import space.rebot.micro.marketservice.model.Group;
import space.rebot.micro.marketservice.model.Sku;
import space.rebot.micro.marketservice.repository.CartRepository;
import space.rebot.micro.marketservice.repository.GroupRepository;
import space.rebot.micro.userservice.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class PreGroupCheckerService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private GroupRepository groupRepository;

    public List<Cart> check(String region, User user, Map<Long, Long> skuGroups) throws CartCheckException, SkuGroupMatchException {
        //govnocode
        List<Cart> carts = checkUserCart(region, user);
        List<Long> invalidGroupSkuId = checkSkuGroups(skuGroups);
        if (invalidGroupSkuId.isEmpty()) {
            return carts;
        }
        throw new SkuGroupMatchException(invalidGroupSkuId);
    }

    private List<Long> checkSkuGroups(Map<Long, Long> skuGroups) {
        List<Long> invalidGroupSkuId = new ArrayList<>();
        for (Map.Entry<Long, Long> skuGroup : skuGroups.entrySet()) {
            Group group = groupRepository.getGroup(skuGroup.getValue());
            if (group == null) {
                invalidGroupSkuId.add(skuGroup.getKey());
                continue;
            }
            Sku sku = group.getSku();
            if (sku.getId() != skuGroup.getKey()) {
                invalidGroupSkuId.add(skuGroup.getKey());
            }
        }
        return invalidGroupSkuId;
    }

    private List<Cart> checkUserCart(String region, User user) throws CartCheckException {
        List<Cart> carts = cartRepository.getCartByUserIdAndCartStatus(user.getId(), CartStatusEnum.ACTIVE.getId());
        List<Long> invalidRegionSkuId = checkUserCartRegions(carts, region);
        List<Long> invalidCountSkuId = checkUserCartSkuCount(carts);
        if (invalidCountSkuId.isEmpty() && invalidRegionSkuId.isEmpty()) {
            return carts;
        }
        if (!invalidCountSkuId.isEmpty()) {
            cartRepository.updateCartStatus(user.getId(), invalidCountSkuId,
                    CartStatusEnum.DELETED.getId(), CartStatusEnum.ACTIVE.getId());
        }
        throw new CartCheckException(invalidRegionSkuId, invalidCountSkuId);
    }

    private List<Long> checkUserCartRegions(List<Cart> carts, String region) {
        List<Long> invalidRegionSkuId = new ArrayList<>();
        carts.forEach(cart -> {
            Sku sku = cart.getSku();
            if (!sku.getRegion().equals(region)) {
                invalidRegionSkuId.add(sku.getId());
            }
        });
        return invalidRegionSkuId;
    }

    private List<Long> checkUserCartSkuCount(List<Cart> carts) {
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
