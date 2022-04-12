package space.rebot.micro.marketservice.enums;

public enum CartStatusEnum {
    ACTIVE(1, "ACTIVE"),
    DELETED(2, "DELETED"),
    IN_GROUP(3, "IN_GROUP");

    private final String name;

    private final int id;

    CartStatusEnum(int id, String name){
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
