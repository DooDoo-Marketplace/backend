package space.rebot.micro.orderservice.enums;

public enum OrdersStatusEnum {
    WAITING_PAYMENT(1, "WAITING_PAYMENT"),
    PAID(2, "PAID"),
    TAKEN_FROM_WAREHOUSE(3, "TAKEN_FROM_WAREHOUSE"),
    TRANSPORT(4, "TRANSPORT"),
    COMPLETED(5, "COMPLETED"),
    CANCELLED(6, "CANCELLED");

    private final String name;

    private final int id;

    OrdersStatusEnum(int id, String name){
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
