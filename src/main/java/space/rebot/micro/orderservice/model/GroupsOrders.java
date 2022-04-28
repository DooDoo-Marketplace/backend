package space.rebot.micro.orderservice.model;

import lombok.Data;
import org.hibernate.annotations.Type;
import space.rebot.micro.marketservice.model.Cart;
import space.rebot.micro.marketservice.model.Group;
import space.rebot.micro.userservice.model.User;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "groups_orders")
public class GroupsOrders {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "pg-uuid")
    private UUID id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne (cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne (cascade = CascadeType.MERGE)
    @JoinColumn(name = "groups_orders_status_id")
    private OrdersStatus OrdersStatus;

    @Column(name = "address")
    private String address;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAt;

    public GroupsOrders(Group group, OrdersStatus ordersStatus, Cart cart, Date createdAt, Date updatedAt, User user, String address) {
        this.group = group;
        this.OrdersStatus = ordersStatus;
        this.cart = cart;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.user = user;
        this.address = address;
    }

    public GroupsOrders() {
    }
}
