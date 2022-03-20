package space.rebot.micro.userservice.validator;

public interface Validator<T> {
    boolean validate(T value);
}
