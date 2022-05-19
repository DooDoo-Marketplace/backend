package space.rebot.micro.userservice.exception;

import org.springframework.http.HttpStatus;

public class AuthException extends Exception{
    private final String message;
    private final HttpStatus status;

    public AuthException(String message, HttpStatus status ) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
