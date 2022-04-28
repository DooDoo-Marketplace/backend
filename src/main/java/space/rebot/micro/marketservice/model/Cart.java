package space.rebot.micro.marketservice.model;

import lombok.Data;
import space.rebot.micro.userservice.model.User;

import javax.persistence.*;



@Data
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne (cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne (cascade = CascadeType.MERGE)
    @JoinColumn(name = "sku_id")
    private Sku sku;


    @Column(name = "count", nullable = false)
    private int count;

    @ManyToOne (cascade = CascadeType.MERGE)
    @JoinColumn(name = "cart_status_id")
    private CartStatus cartStatus;

    @Column(name = "is_retail", nullable = false)
    private boolean isRetail;

    public Cart() {
    }

    public Cart(User user, Sku sku, int count, CartStatus cartStatus, boolean isRetail) {
        this.user = user;
        this.sku = sku;
        this.count = count;
        this.cartStatus = cartStatus;
        this.isRetail = isRetail;
    }
}
