package space.rebot.micro.marketservice.enums;

public enum CartStatusEnum {
    ACTIVE(1L, "ACTIVE"),
    DELETED(2L, "DELETED"),
    IN_GROUP(3L, "IN_GROUP");

    private final String name;

    private final Long id;

    CartStatusEnum(Long id, String name){
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }
}
