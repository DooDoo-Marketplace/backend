package space.rebot.micro.marketservice.model;

import lombok.Data;
import space.rebot.micro.userservice.model.User;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "sku")
public class Sku {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "region", nullable = false)
    private String region;
    @Column(name = "count", nullable = false)
    private int count;
    @Column(name = "retail_price", nullable = false)
    private double retail_price;
    @Column(name = "group_price", nullable = false)
    private double group_price;
    @Column(name = "percent", nullable = false)
    private double percent;
    @Column(name = "min_count", nullable = false)
    private int min_count;
    @Column(name = "rating", nullable = false)
    private double rating;
    @Column(name = "created_at")
    private Date created_at;
    @Column(name = "updated_at")
    private Date updated_at;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
