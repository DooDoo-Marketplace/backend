package space.rebot.micro.reviewservice.mapper;

import org.springframework.stereotype.Component;
import space.rebot.micro.reviewservice.dto.ReviewDTO;
import space.rebot.micro.reviewservice.model.Review;
import space.rebot.micro.userservice.dto.user.UserDto;
import space.rebot.micro.userservice.factory.UserFactory;

@Component
public class ReviewMapper {

    public ReviewDTO mapToReviewDto(Review review) {
        UserFactory userFactory = new UserFactory();
        UserDto userDto = userFactory.fromEntity(review.getUser());
        return ReviewDTO.builder()
                .skuId(review.getSku().getId())
                .userDto(userDto)
                .text(review.getText())
                .photoUrl(review.getPhotoUrl())
                .rating(review.getRating())
                .createdAt(review.getCreatedAt())
                .build();
    }

}
