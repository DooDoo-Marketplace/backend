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

    @Column(name = "user_id" , nullable = false)
    @ManyToOne
    @JoinColumn(name = "id")
    private User user;

    @Column(name = "sku_id", nullable = false)
    @ManyToOne
    @JoinColumn(name = "id")
    private Sku sku;


    @Column(name = "count", nullable = false)
    private int count;

    @Column(name = "isDeleted", nullable = false)
    private boolean isDeleted;
}
