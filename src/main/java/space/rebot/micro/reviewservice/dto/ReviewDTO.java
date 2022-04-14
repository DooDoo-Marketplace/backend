package space.rebot.micro.reviewservice.dto;

import lombok.Builder;
import lombok.Data;
import space.rebot.micro.userservice.dto.user.UserDto;

import java.util.Date;

@Data
@Builder
public class ReviewDTO {

    private UserDto userDto;

    private String text;

    private String photoUrl;

    private Date createdAt;
}

