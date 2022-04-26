package space.rebot.micro.marketservice.service;

import org.springframework.stereotype.Service;
import space.rebot.micro.marketservice.exception.PaymentException;

import java.text.ParseException;

@Service
public class PaymentService {

    public boolean spend() throws PaymentException {
        return true;
        //throw new PaymentException();
    }
}
