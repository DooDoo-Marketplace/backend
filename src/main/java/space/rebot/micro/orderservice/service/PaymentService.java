package space.rebot.micro.orderservice.service;

import org.springframework.stereotype.Service;
import space.rebot.micro.orderservice.exception.PaymentException;

@Service
public class PaymentService {

    public boolean spend() throws PaymentException {
        return true;
        //throw new PaymentException();
    }
}
