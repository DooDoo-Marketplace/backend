package space.rebot.micro.reviewservice.exception;

public class WrongUserException extends Exception {
    public WrongUserException(String message){
        super(message);
    }
}
