package com.rebot.micro.validator;

import com.rebot.micro.utils.StringUtils;

public class PhoneValidator implements Validator<String>{

    public boolean validate(String value){
        return !(value.isEmpty() || value.length() < 5 || !StringUtils.isUint(value));
    }
}
