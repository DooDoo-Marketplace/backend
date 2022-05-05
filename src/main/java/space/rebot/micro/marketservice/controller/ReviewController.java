package space.rebot.micro.marketservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import space.rebot.micro.marketservice.exception.SkuNotFoundException;
import space.rebot.micro.marketservice.dto.ReviewDTO;
import space.rebot.micro.marketservice.exception.InvalidRatingException;
import space.rebot.micro.marketservice.exception.WrongUserException;
import space.rebot.micro.marketservice.service.ReviewService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/review/")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;


    @PostMapping(value = "get", produces = "application/json")
    public ResponseEntity<?> getSkuReviews(@RequestParam("skuId") Long skuId) {
        Map<Object, Object> model = new HashMap<>();
        List<ReviewDTO> reviewDTOList = reviewService.getSkuReview(skuId);
        model.put("reviews", reviewDTOList);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @PostMapping(value = "add", produces = "application/json")
    public ResponseEntity<?> addReviewToSku(@RequestBody ReviewDTO reviewDTO) {
        Map<Object, Object> model = new HashMap<>();
        try {
            UUID reviewId = reviewService.addReviewToSku(reviewDTO);
            model.put("uuid", reviewId);
        } catch (SkuNotFoundException e) {
            model.put("message", "INVALID_SKU");
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        } catch (InvalidRatingException e) {
            model.put("message", e.getMessage());
            return new ResponseEntity<>(model, HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(model, HttpStatus.OK);
    }


    @PostMapping(value = "delete", produces = "application/json")
    public ResponseEntity<?> deleteReview(@RequestParam("id") UUID uuid) {
        Map<Object, Object> model = new HashMap<>();
        try {
            reviewService.deleteReview(uuid);
        } catch (WrongUserException e) {
            model.put("message", e.getMessage());
            return new ResponseEntity<>(model, HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @PostMapping(value = "update", produces = "application/json")
    public ResponseEntity<?> updateReview(@RequestBody ReviewDTO reviewDTO) {
        Map<Object, Object> model = new HashMap<>();
        try {
            reviewService.updateReview(reviewDTO);
        } catch (WrongUserException e) {
            model.put("message", e.getMessage());
            return new ResponseEntity<>(model, HttpStatus.NOT_ACCEPTABLE);
        } catch (InvalidRatingException e) {
            model.put("message", e.getMessage());
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
