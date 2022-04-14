package space.rebot.micro.reviewservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import space.rebot.micro.marketservice.exception.SkuNotFoundException;
import space.rebot.micro.reviewservice.dto.ReviewDTO;
import space.rebot.micro.reviewservice.exception.InvalidRatingException;
import space.rebot.micro.reviewservice.exception.WrongUserException;
import space.rebot.micro.reviewservice.service.ReviewService;

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
    public ResponseEntity<?> getSkuReviews(@RequestParam("sku_id") Long skuId) {
        Map<Object, Object> model = new HashMap<>();
        List<ReviewDTO> reviewDTOList = reviewService.getSkuReview(skuId);
        model.put("success", true);
        model.put("reviews", reviewDTOList);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @PostMapping(value = "add", produces = "application/json")
    public ResponseEntity<?> addReviewToSku(@RequestBody ReviewDTO reviewDTO) {
        Map<Object, Object> model = new HashMap<>();
        try {
            UUID reviewId = reviewService.addReviewToSku(reviewDTO);
            model.put("success", true);
            model.put("uuid", reviewId);
        } catch (SkuNotFoundException e) {
            model.put("success", false);
            model.put("message", "INVALID_SKU");
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        } catch (InvalidRatingException e) {
            model.put("success", false);
            model.put("message", e.getMessage());
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(model, HttpStatus.OK);
    }


    @PostMapping(value = "delete", produces = "application/json")
    public ResponseEntity<?> deleteReview(@RequestParam("id") UUID uuid) {
        Map<Object, Object> model = new HashMap<>();
        try {
            reviewService.deleteReview(uuid);
        } catch (WrongUserException e) {
            model.put("success", false);
            model.put("message", e.getMessage());
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        }

        model.put("success", true);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @PostMapping(value = "update", produces = "application/json")
    public ResponseEntity<?> updateReview(@RequestParam("id") UUID uuid,
                                          @RequestBody ReviewDTO reviewDTO) {
        Map<Object, Object> model = new HashMap<>();
        try {
            reviewService.updateReview(uuid, reviewDTO);
        } catch (WrongUserException | InvalidRatingException e) {
            model.put("success", false);
            model.put("message", e.getMessage());
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        }

        model.put("success", true);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
