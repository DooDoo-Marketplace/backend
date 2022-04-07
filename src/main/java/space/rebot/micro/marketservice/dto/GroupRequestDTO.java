package space.rebot.micro.marketservice.dto;

import lombok.Data;

import java.util.Map;

@Data
public class GroupRequestDTO {

    private String region;

    private Map<Long, Long> skuGroup;

}
