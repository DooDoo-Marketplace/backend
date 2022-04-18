package space.rebot.micro.userservice.service;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class DateService {
    public Date utcNow(){
        return Date.from(Instant.now());
    }

    public Date addTimeForCurrent(int hours) {
        return Date.from(Instant.now().plus(hours, ChronoUnit.HOURS));
    }
}
