package space.rebot.micro.userservice.validator;

import org.springframework.stereotype.Component;

@Component
public class PhoneValidator extends Validator<String>{
    @Override
    protected boolean isValid(String value){
        return value.matches("[+]?[78][0-9]{10}");
    }
}