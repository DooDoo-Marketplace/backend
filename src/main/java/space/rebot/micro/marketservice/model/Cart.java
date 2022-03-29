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

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "sku_id")
    private Sku sku;


    @Column(name = "count", nullable = false)
    private int count;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    public Cart() {
    }

    public Cart(User user, Sku sku, int count, boolean isDeleted) {
        this.user = user;
        this.sku = sku;
        this.count = count;
        this.isDeleted = isDeleted;
    }
}
