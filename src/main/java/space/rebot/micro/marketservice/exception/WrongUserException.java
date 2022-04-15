package space.rebot.micro.marketservice.exception;

public class WrongUserException extends Exception {
    public WrongUserException(String message){
        super(message);
    }
}
