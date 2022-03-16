package com.rebot.micro.userservice.dto;

public class ErrorDto {
    String message;
    public ErrorDto(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
