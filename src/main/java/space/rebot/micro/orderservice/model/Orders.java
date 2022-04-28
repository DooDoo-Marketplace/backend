package space.rebot.micro.orderservice.model;

import lombok.Data;
import org.hibernate.annotations.Type;
import space.rebot.micro.marketservice.model.Cart;
import space.rebot.micro.userservice.model.User;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "pg-uuid")
    private UUID id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne (cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne (cascade = CascadeType.MERGE)
    @JoinColumn(name = "orders_status_id")
    private OrdersStatus OrdersStatus;

    @Column(name = "address")
    private String address;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAt;

    public Orders() {
    }

    public Orders(Cart cart, OrdersStatus ordersStatus, Date createdAt, Date updatedAt, User user, String address) {
        this.cart = cart;
        this.user = user;
        this.address = address;
        this.OrdersStatus = ordersStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
