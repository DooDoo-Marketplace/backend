package space.rebot.micro.marketservice.enums;

public enum CartStatusEnum {
    ACTIVE("ACTIVE"),
    DELETED("DELETED"),
    IN_GROUP("IN_GROUP");

    private final String name;

    CartStatusEnum(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
