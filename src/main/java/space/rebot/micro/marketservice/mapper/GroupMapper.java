package space.rebot.micro.marketservice.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import space.rebot.micro.marketservice.dto.OrderResponseDTO;
import space.rebot.micro.marketservice.model.Group;

@Component
public class GroupMapper {

    @Autowired
    private SkuMapper skuMapper;

    public OrderResponseDTO mapToDTO(Group group) {
        return OrderResponseDTO.builder()
                .id(group.getId())
                .sku(skuMapper.mapToSkuDto(group.getSku()))
                .createdAt(group.getCreatedAt())
                .expiredAt(group.getExpiredAt())
                .count(group.getCount())
                .region(group.getRegion())
                .build();
    }
}
