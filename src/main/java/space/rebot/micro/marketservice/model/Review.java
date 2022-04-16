package space.rebot.micro.marketservice.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import space.rebot.micro.marketservice.model.Sku;
import space.rebot.micro.userservice.model.User;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sku_id")
    private Sku sku;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "rating", nullable = false)
    private Double rating;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    public Review() {
    }

    public Review(User user, Sku sku, String text, String photoUrl, Double rating, Date createdAt) {
        this.user = user;
        this.sku = sku;
        this.text = text;
        this.photoUrl = photoUrl;
        this.rating = rating;
        this.createdAt = createdAt;
    }
}
