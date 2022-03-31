package space.rebot.micro.marketservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

//it's example
@Data
@Builder
public class SkuDTO {

    private UUID id;

    private String name;

    private String description;

    private String region;

    private double retailPrice;

    private double groupPrice;

    private int minCount;

    private double rating;
}
