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
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "region")
    private String region;
    @Column(name = "count")
    private int count;
    @Column(name = "retail_price")
    private double retail_price;
    @Column(name = "group_price")
    private double group_price;
    @Column(name = "percent")
    private double percent;
    @Column(name = "min_count")
    private int min_count;
    @Column(name = "rating")
    private double rating;
    @Column(name = "created_at")
    private Date created_at;
    @Column(name = "updated_at")
    private Date updated_at;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
