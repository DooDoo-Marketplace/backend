package space.rebot.micro.reviewservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import space.rebot.micro.userservice.dto.user.UserDto;

import java.util.Date;

@Data
@Builder
public class ReviewDTO {
    @NonNull
    private Long skuId;

    private UserDto userDto;

    @NonNull
    private String text;

    private String photoUrl;

    @NonNull
    private Double rating;

    private Date createdAt;
}

