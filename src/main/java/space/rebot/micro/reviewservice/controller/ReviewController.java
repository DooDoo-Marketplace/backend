package space.rebot.micro.reviewservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import space.rebot.micro.marketservice.exception.SkuNotFoundException;
import space.rebot.micro.reviewservice.dto.ReviewDTO;
import space.rebot.micro.reviewservice.exception.InvalidTextException;
import space.rebot.micro.reviewservice.exception.WrongUserException;
import space.rebot.micro.reviewservice.mapper.ReviewMapper;
import space.rebot.micro.reviewservice.service.ReviewService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/review/")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewMapper reviewMapper;

    @PostMapping(value = "get", produces = "application/json")
    public ResponseEntity<?> getSkuReviews(@RequestParam("sku_id") Long skuId) {
        Map<Object, Object> model = new HashMap<>();
        List<ReviewDTO> reviewDTOList = reviewService.getSkuReview(skuId)
                .stream().map(review -> reviewMapper.mapToReviewDto(review))
                .collect(Collectors.toList());
        model.put("success", true);
        model.put("reviews", reviewDTOList);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @PostMapping(value = "add", produces = "application/json")
    public ResponseEntity<?> addReviewToSku(@RequestParam("sku_id") Long skuId,
                                            @RequestParam("text") String text,
                                            @RequestParam("photo_url") String photoUrl) {
        Map<Object, Object> model = new HashMap<>();
        try {
            UUID reviewId = reviewService.addReviewToSku(skuId, text, photoUrl);
            model.put("success", true);
            model.put("uuid", reviewId);
        } catch (SkuNotFoundException e) {
            model.put("success", false);
            model.put("message", "Sku not found");
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        } catch (InvalidTextException e) {
            model.put("success", false);
            model.put("message", "Text length is 0");
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
            model.put("message", "This isn't your comment");
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        }

        model.put("success", true);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @PostMapping(value = "update", produces = "application/json")
    public ResponseEntity<?> updateReview(@RequestParam("id") UUID uuid,
                                          @RequestParam("text") String text,
                                          @RequestParam("photo_url") String photoUrl) {
        Map<Object, Object> model = new HashMap<>();
        try {
            reviewService.updateReview(uuid, text, photoUrl);
        } catch (WrongUserException e) {
            model.put("success", false);
            model.put("message", "This isn't your comment");
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        }

        model.put("success", true);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
