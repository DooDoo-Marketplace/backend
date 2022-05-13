package space.rebot.micro.marketservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import space.rebot.micro.marketservice.dto.ReviewDTO;
import space.rebot.micro.marketservice.exception.InvalidRatingException;
import space.rebot.micro.marketservice.exception.SkuNotFoundException;
import space.rebot.micro.marketservice.exception.WrongUserException;
import space.rebot.micro.marketservice.model.Review;
import space.rebot.micro.marketservice.model.Sku;
import space.rebot.micro.marketservice.repository.ReviewRepository;
import space.rebot.micro.marketservice.repository.SkuRepository;
import space.rebot.micro.userservice.model.Session;
import space.rebot.micro.userservice.model.User;

import javax.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private SkuRepository skuRepository;

    @Mock
    private HttpServletRequest context;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    void getSkuReview_shouldReturnEmptyList() {
        Long skuId = 1L;

        Mockito.when(reviewRepository.getReviewBySkuId(skuId)).thenReturn(null);

        assertEquals(reviewService.getSkuReview(skuId), Collections.emptyList());
    }

    @Test
    void addReviewToSku_shouldThrowSkuNotFoundException() {
        ReviewDTO reviewDTO = Mockito.mock(ReviewDTO.class);
        Long skuId = 1L;

        Mockito.when(reviewDTO.getSkuId()).thenReturn(skuId);
        Mockito.when(skuRepository.getSkuById(skuId)).thenReturn(null);

        assertThrows(SkuNotFoundException.class, () -> reviewService.addReviewToSku(reviewDTO));
    }

    @Test
    void addReviewToSku_shouldThrowInvalidRatingException() {
        ReviewDTO reviewDTO = Mockito.mock(ReviewDTO.class);
        Long skuId = 1L;
        Sku sku = Mockito.mock(Sku.class);
        double invalidRating = 6;

        Mockito.when(reviewDTO.getSkuId()).thenReturn(skuId);
        Mockito.when(skuRepository.getSkuById(skuId)).thenReturn(sku);
        Mockito.when(reviewDTO.getRating()).thenReturn(invalidRating);

        assertThrows(InvalidRatingException.class, () -> reviewService.addReviewToSku(reviewDTO));
    }

    @Test
    void deleteReview_shouldThrowWrongUserException() {
        Session session = Mockito.mock(Session.class);
        User user1 = Mockito.mock(User.class);
        UUID uuid = UUID.randomUUID();
        Review review = Mockito.mock(Review.class);
        Long userId1 = 1L;
        User user2 = Mockito.mock(User.class);
        Long userId2 = 2L;

        Mockito.when(context.getAttribute(Mockito.any())).thenReturn(session);
        Mockito.when(session.getUser()).thenReturn(user1);
        Mockito.when(reviewRepository.getReviewById(uuid)).thenReturn(review);
        Mockito.when(review.getUser()).thenReturn(user2);
        Mockito.when(user2.getId()).thenReturn(userId2);
        Mockito.when(user1.getId()).thenReturn(userId1);

        assertThrows(WrongUserException.class, () -> reviewService.deleteReview(uuid));
    }


    @Test
    void updateReview_shouldThrowInvalidRatingException() {
        ReviewDTO reviewDTO = Mockito.mock(ReviewDTO.class);
        double invalidRating = 6;

        Mockito.when(reviewDTO.getRating()).thenReturn(invalidRating);

        assertThrows(InvalidRatingException.class, () -> reviewService.updateReview(reviewDTO));
    }

    @Test
    void updateReview_shouldThrowWrongUserException() {
        ReviewDTO reviewDTO = Mockito.mock(ReviewDTO.class);
        double validRating = 3;
        Session session = Mockito.mock(Session.class);
        User user1 = Mockito.mock(User.class);
        UUID uuid = UUID.randomUUID();
        Review review = Mockito.mock(Review.class);
        Long userId1 = 1L;
        User user2 = Mockito.mock(User.class);
        Long userId2 = 2L;

        Mockito.when(reviewDTO.getRating()).thenReturn(validRating);
        Mockito.when(context.getAttribute(Mockito.any())).thenReturn(session);
        Mockito.when(session.getUser()).thenReturn(user1);
        Mockito.when(reviewDTO.getId()).thenReturn(uuid);
        Mockito.when(reviewRepository.getReviewById(uuid)).thenReturn(review);
        Mockito.when(review.getUser()).thenReturn(user2);
        Mockito.when(user2.getId()).thenReturn(userId2);
        Mockito.when(user1.getId()).thenReturn(userId1);

        assertThrows(WrongUserException.class, () -> reviewService.updateReview(reviewDTO));
    }
}