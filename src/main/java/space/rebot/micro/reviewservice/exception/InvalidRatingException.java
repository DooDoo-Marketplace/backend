package space.rebot.micro.reviewservice.exception;

public class InvalidRatingException extends Exception{
    public InvalidRatingException(String message) {
        super(message);
    }
}
