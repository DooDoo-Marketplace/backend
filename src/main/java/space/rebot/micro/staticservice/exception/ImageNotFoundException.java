package space.rebot.micro.staticservice.exception;

public class ImageNotFoundException extends Exception {
    public ImageNotFoundException() {
        super("IMAGE_NOT_FOUND");
    }
}
