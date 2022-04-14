package space.rebot.micro.reviewservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.rebot.micro.marketservice.exception.SkuNotFoundException;
import space.rebot.micro.marketservice.model.Sku;
import space.rebot.micro.marketservice.repository.SkuRepository;
import space.rebot.micro.reviewservice.exception.InvalidTextException;
import space.rebot.micro.reviewservice.exception.WrongUserException;
import space.rebot.micro.reviewservice.model.Review;
import space.rebot.micro.reviewservice.repository.ReviewRepository;
import space.rebot.micro.userservice.model.Session;
import space.rebot.micro.userservice.model.User;
import space.rebot.micro.userservice.service.DateService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

    public List<Review> getSkuReview(Long skuId) {
        List<Review> skuReviews = reviewRepository.getReviewBySkuId(skuId);
        if (skuReviews == null) {
            return Collections.emptyList();
        }
        return skuReviews;
    }

    public UUID addReviewToSku(Long skuId, String text, String photoUrl) throws SkuNotFoundException, InvalidTextException {
        Sku sku = skuRepository.getSkuById(skuId);
        if (sku == null) {
            throw new SkuNotFoundException();
        }

        if (text == null || text.equals("")) {
            throw new InvalidTextException();
        }
        Date now = dateService.utcNow();
        User user = ((Session) context.getAttribute(Session.SESSION)).getUser();
        Review review = new Review(user, sku, text, photoUrl, now);
        reviewRepository.save(review);
        return review.getId();
    }

    public void deleteReview(UUID uuid) throws WrongUserException {
        User user = ((Session) context.getAttribute(Session.SESSION)).getUser();
        Review review = reviewRepository.getReviewById(uuid);
        Long userId = review.getUser().getId();
        if (userId != user.getId()) {
            throw new WrongUserException();
        }

        reviewRepository.deleteById(uuid);
    }

    public void updateReview(UUID uuid, String text, String photoUrl) throws WrongUserException {
        User user = ((Session) context.getAttribute(Session.SESSION)).getUser();
        Review review = reviewRepository.getReviewById(uuid);
        Long userId = review.getUser().getId();
        if (userId != user.getId()) {
            throw new WrongUserException();
        }
        reviewRepository.updateReviewTextAndPhoto(text, photoUrl, uuid);
    }
}
