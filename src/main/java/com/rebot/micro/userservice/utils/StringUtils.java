package com.rebot.micro.userservice.utils;

public class StringUtils {
    public static boolean isUint(String value){
        return value.matches("[0-9]+");
    }
    public static boolean isInt(String value){
        return value.matches("-?[0-9]+");
    }
}
