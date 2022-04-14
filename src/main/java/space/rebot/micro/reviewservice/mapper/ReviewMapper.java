package space.rebot.micro.reviewservice.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import space.rebot.micro.marketservice.mapper.SkuMapper;
import space.rebot.micro.reviewservice.dto.ReviewDTO;
import space.rebot.micro.reviewservice.model.Review;
import space.rebot.micro.userservice.dto.user.UserDto;
import space.rebot.micro.userservice.model.User;

@Component
public class ReviewMapper {
    @Autowired
    private SkuMapper skuMapper;

    public ReviewDTO mapToReviewDto(Review review) {
        User user = review.getUser();
        UserDto userDto = new UserDto();
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setPhone(user.getPhone());
        userDto.setEmail(user.getEmail());
        return ReviewDTO.builder()
                //.skuDTO(skuMapper.mapToSkuDto(review.getSku()))
                .userDto(userDto)
                .text(review.getText())
                .photoUrl(review.getPhotoUrl())
                .createdAt(review.getCreatedAt())
                .build();
    }
}
