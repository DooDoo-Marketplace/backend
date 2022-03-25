package space.rebot.micro.marketservice.model;

import lombok.Data;
import space.rebot.micro.userservice.model.User;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "sku_id")
    private Sku sku;


    @Column(name = "count", nullable = false)
    private int count;

    @Column(name = "isDeleted", nullable = false)
    private boolean isDeleted;
}
