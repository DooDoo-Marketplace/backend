package space.rebot.micro.marketservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartDTO {

    private SkuDTO skuDTO;

    private int cnt;
}
