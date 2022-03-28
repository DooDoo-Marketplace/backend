package space.rebot.micro.marketservice.mapper;

import org.springframework.stereotype.Component;
import space.rebot.micro.marketservice.dto.SkuDTO;
import space.rebot.micro.marketservice.model.Sku;

@Component
public class SkuMapper {

//    method is not needed i think. We always got sku id from front
//    public Sku mapToSKu(SkuDTO) {
//    }

    public SkuDTO mapToSkuDto(Sku sku) {
        return SkuDTO.builder()
                .id(sku.getId())
                .name(sku.getName())
                .description(sku.getDescription())
                .region(sku.getRegion())
                .retailPrice(sku.getRetailPrice())
                .groupPrice(sku.getGroupPrice())
                .minCount(sku.getMinCount())
                .rating(sku.getRating())
                .build();
    }

}
