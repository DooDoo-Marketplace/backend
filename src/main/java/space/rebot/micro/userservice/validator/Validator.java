package space.rebot.micro.userservice.validator;

public abstract class Validator<T> {
    abstract boolean isValid(T value);
    public boolean validate(T value, boolean nullable){
        if (nullable && value == null) return true;
        return isValid(value);

     }
}
