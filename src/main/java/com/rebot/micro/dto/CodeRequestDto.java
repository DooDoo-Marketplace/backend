package com.rebot.micro.dto;

import org.wildfly.common.annotation.NotNull;

public class CodeRequestDto {
    @NotNull
    private String phone;
    @NotNull
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
