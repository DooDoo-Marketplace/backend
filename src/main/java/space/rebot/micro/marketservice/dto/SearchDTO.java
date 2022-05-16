package space.rebot.micro.marketservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchDTO {
    private String name;

    private String region = "%";

    private Double minPrice = -1.0;

    private Double maxPrice = Integer.MAX_VALUE * 1.0;

    private Double rating = 0.0;

    private Integer limit;

    private Integer page;
}
