package com.rebot.micro.utils;

import java.util.Date;

public class DateUtils {
    public static long between(Date first, Date second){
        return (first.getTime() - second.getTime()) / 1000;
    }
}
