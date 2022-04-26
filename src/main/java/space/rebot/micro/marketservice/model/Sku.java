package space.rebot.micro.marketservice.model;

import lombok.Data;
import space.rebot.micro.userservice.model.User;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

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
    @Column(name = "weight", nullable = false)
    private double weight;
    @Column(name = "retail_price")
    private Double retailPrice;
    @Column(name = "group_price", nullable = false)
    private double groupPrice;
    @Column(name = "percent", nullable = false)
    private double percent;
    @Column(name = "min_count", nullable = false)
    private int minCount;
    @Column(name = "rating", nullable = false)
    private double rating;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAt;
}
