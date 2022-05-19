package space.rebot.micro.userservice.formatter;

import space.rebot.micro.userservice.exception.InvalidPhoneException;
import space.rebot.micro.userservice.validator.PhoneValidator;

public class PhoneFormatter {
    public String format(String phone) throws Exception {
        PhoneValidator phoneValidator = new PhoneValidator();
        if (phoneValidator.validate(phone, false)) {
            throw new InvalidPhoneException("INVALID_PHONE");
        }
        if (phone.charAt(0) == '8' || phone.charAt(0) == '7') {
            phone = "+7" + phone.substring(1);
        } else if (phone.charAt(0) == '+' && phone.charAt(1) == '8') {
            phone = "+7" + phone.substring(2);
        }
        return phone;
    }
}
