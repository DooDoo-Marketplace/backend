package com.rebot.micro.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.rebot.micro.model.Session;

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
