package space.rebot.micro.userservice.validator;

import space.rebot.micro.userservice.utils.StringUtils;

public class PhoneValidator implements Validator<String>{

    public boolean validate(String value){
        value = value.replace("+", "");
        return !( value.isEmpty() || value.length() < 5 || !StringUtils.isUint(value));
    }
}
