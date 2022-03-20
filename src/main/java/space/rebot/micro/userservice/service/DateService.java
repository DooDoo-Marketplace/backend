package com.rebot.micro.userservice.service;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class DateService {
    public Date utcNow(){
        return Date.from(Instant.now());
    }
}
