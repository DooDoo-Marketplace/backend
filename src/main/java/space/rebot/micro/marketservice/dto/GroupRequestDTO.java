package space.rebot.micro.marketservice.dto;

import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
public class GroupRequestDTO {

    private String region;

    private Map<Long, UUID> skuGroup;

}
