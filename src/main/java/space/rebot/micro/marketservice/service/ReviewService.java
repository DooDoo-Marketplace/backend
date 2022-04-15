package space.rebot.micro.marketservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.rebot.micro.marketservice.exception.SkuNotFoundException;
import space.rebot.micro.marketservice.model.Sku;
import space.rebot.micro.marketservice.repository.SkuRepository;
import space.rebot.micro.marketservice.dto.ReviewDTO;
import space.rebot.micro.marketservice.exception.InvalidRatingException;
import space.rebot.micro.marketservice.exception.WrongUserException;
import space.rebot.micro.marketservice.mapper.ReviewMapper;
import space.rebot.micro.marketservice.model.Review;
import space.rebot.micro.marketservice.repository.ReviewRepository;
import space.rebot.micro.userservice.model.Session;
import space.rebot.micro.userservice.model.User;
import space.rebot.micro.userservice.service.DateService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private SkuRepository skuRepository;

    @Autowired
    private HttpServletRequest context;

    @Autowired
    private DateService dateService;

    @Autowired
    private ReviewMapper reviewMapper;

    public List<ReviewDTO> getSkuReview(Long skuId) {
        List<Review> skuReviews = reviewRepository.getReviewBySkuId(skuId);
        if (skuReviews == null) {
            return Collections.emptyList();
        }

        return skuReviews.stream().map(review -> reviewMapper.mapToReviewDto(review))
                .collect(Collectors.toList());
    }

    public UUID addReviewToSku(ReviewDTO reviewDTO) throws SkuNotFoundException, InvalidRatingException {
        Sku sku = skuRepository.getSkuById(reviewDTO.getSkuId());
        if (sku == null) {
            throw new SkuNotFoundException();
        }
        if (reviewDTO.getRating() < 0 || reviewDTO.getRating() > 5){
            throw new InvalidRatingException("INVALID_RATING");
        }
        Date now = dateService.utcNow();
        User user = ((Session) context.getAttribute(Session.SESSION)).getUser();
        Review review = new Review(user, sku, reviewDTO.getText(),
                reviewDTO.getPhotoUrl(), reviewDTO.getRating(), now);
        reviewRepository.save(review);
        return review.getId();
    }

    public void deleteReview(UUID uuid) throws WrongUserException {
        User user = ((Session) context.getAttribute(Session.SESSION)).getUser();
        Review review = reviewRepository.getReviewById(uuid);
        long userId = review.getUser().getId();
        if (userId != user.getId()) {
            throw new WrongUserException("INVALID_USER");
        }

        reviewRepository.deleteById(uuid);
    }

    public void updateReview(ReviewDTO reviewDTO) throws WrongUserException, InvalidRatingException {
        if (reviewDTO.getRating() < 0 || reviewDTO.getRating() > 5){
            throw new InvalidRatingException("INVALID_RATING");
        }
        User user = ((Session) context.getAttribute(Session.SESSION)).getUser();
        Review review = reviewRepository.getReviewById(reviewDTO.getId());
        long userId = review.getUser().getId();
        if (userId != user.getId()) {
            throw new WrongUserException("INVALID_USER");
        }
        reviewRepository.updateReview(reviewDTO.getText(), reviewDTO.getPhotoUrl(),
                reviewDTO.getRating(), reviewDTO.getId());
    }
}
