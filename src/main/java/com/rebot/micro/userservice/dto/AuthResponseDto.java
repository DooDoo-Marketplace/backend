package com.rebot.micro.userservice.dto;

import com.fasterxml.jackson.annotation.JsonView;

@JsonView
public class AuthResponseDto {
    @JsonView
    String token;
    @JsonView
    boolean registered;
    public AuthResponseDto(String token, boolean registered){
        this.token = token;
        this.registered = registered;
    }
}
