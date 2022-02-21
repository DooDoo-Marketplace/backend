package com.rebot.micro.validator;

public interface Validator<T> {
    boolean validate(T value);
}
