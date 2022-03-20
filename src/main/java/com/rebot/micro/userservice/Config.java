package com.rebot.micro.userservice;

public enum Config {
    ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN");

    private final String name;

    Config(String name){
        this.name = name;
    }

    @Override
    public String toString(){
        return this.name;
    }


}
