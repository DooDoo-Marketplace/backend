package space.rebot.micro.userservice.validator;

import space.rebot.micro.userservice.utils.StringUtils;

public class PhoneValidator extends Validator<String>{

    @Override
    protected boolean isValid(String value){
        value = value.replace("+", "");
        return !( value.isEmpty() || value.length() < 5 || !StringUtils.isUint(value));
    }
}
