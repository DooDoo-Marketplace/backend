package space.rebot.micro.marketservice.dto;

import lombok.Data;

import java.util.Map;

@Data
public class GroupDTO {

    private String region;

    private Map<Long, Long> skuGroup;

}
