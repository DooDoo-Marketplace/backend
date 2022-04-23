package space.rebot.micro.marketservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SkuIdDTO {
    private Long skuId;

    public SkuIdDTO(Long skuId) {
        this.skuId = skuId;
    }

    public SkuIdDTO() {
        skuId = 0L;
    }
}
