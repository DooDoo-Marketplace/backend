package space.rebot.micro.marketservice.enums;

public enum GroupStatusEnum {
    ACTIVE(1, "ACTIVE"),
    EXTRA(2, "EXTRA"),
    CANCELED(3, "CANCELED"),
    COMPLETED(4, "COMPLETED");

    private final String name;

    private final int id;

    GroupStatusEnum(int id, String name){
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

