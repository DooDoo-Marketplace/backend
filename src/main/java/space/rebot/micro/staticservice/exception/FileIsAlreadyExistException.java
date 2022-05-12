package space.rebot.micro.staticservice.exception;

public class FileIsAlreadyExistException extends Exception {
    public FileIsAlreadyExistException() {
        super("FILE_IS_ALREADY_EXIST");
    }
}
