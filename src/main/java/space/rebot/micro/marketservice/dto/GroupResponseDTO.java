package space.rebot.micro.marketservice.dto;

import lombok.Builder;
import lombok.Data;

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
