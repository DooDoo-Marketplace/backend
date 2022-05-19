package space.rebot.micro.userservice.exception;

public class AttemptsLimitException extends Exception{
    public AttemptsLimitException (String message) {
        super(message);
    }
}
