package space.rebot.micro.marketservice.service;

import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public class PaymentService {

    public boolean spend() throws ParseException {
        return true;
        //throw new PaymentException();
    }
}
