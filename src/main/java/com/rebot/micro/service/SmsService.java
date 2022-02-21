package com.rebot.micro.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.ApplicationScope;

@Configuration
public class SmsService {
    private String ACCOUNT_SID;
    private String AUTH_TOKEN;

    @Bean
    @ApplicationScope
    public SmsService getSmsService(){
        return new SmsService();
    }
    public void sendCode(int code){
    }
}
