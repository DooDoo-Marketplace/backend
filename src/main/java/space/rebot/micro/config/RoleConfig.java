package com.rebot.micro.config;

public enum RoleConfig {
    ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN"),
    UNAUTHORIZED("UNAUTHORIZED");

    private final String name;

    RoleConfig(String name){
        this.name = name;
    }

    @Override
    public String toString(){
        return this.name;
    }


}
