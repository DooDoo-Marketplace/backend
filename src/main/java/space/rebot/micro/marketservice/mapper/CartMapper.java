package space.rebot.micro.marketservice.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import space.rebot.micro.marketservice.dto.CartDTO;
import space.rebot.micro.marketservice.model.Cart;

@Component
public class CartMapper {

    @Autowired
    private SkuMapper skuMapper;

    public CartDTO mapToCartDto(Cart cart) {
        return CartDTO.builder()
                .skuDTO(skuMapper.mapToSkuDto(cart.getSku()))
                .cnt(cart.getCount())
                .build();
    }

}
