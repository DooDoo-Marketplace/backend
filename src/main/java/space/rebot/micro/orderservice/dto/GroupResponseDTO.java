package space.rebot.micro.orderservice.dto;

import lombok.Builder;
import lombok.Data;
import space.rebot.micro.marketservice.dto.SkuDTO;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class GroupResponseDTO {

    private UUID id;

    private SkuDTO sku;

    private Date createdAt;

    private Date expiredAt;

    private int count;

    private String region;

}
