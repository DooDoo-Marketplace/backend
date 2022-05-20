package space.rebot.micro.userservice.exception;

public class TooFastRequestsException extends Exception{
    public TooFastRequestsException(String message) {
        super(message);
    }
}
