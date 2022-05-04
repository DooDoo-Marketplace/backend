package space.rebot.micro.orderservice.model;

import lombok.Data;
import org.hibernate.annotations.Type;
import space.rebot.micro.marketservice.model.Sku;
import space.rebot.micro.userservice.model.User;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@Table(name = "groups")
public class Group {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "pg-uuid")
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sku_id")
    private Sku sku;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "expired_at")
    private Date expiredAt;

    @Column(name = "count")
    private int count;

    @Column(name = "region")
    private String region;

    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "groups_users",
            joinColumns = {@JoinColumn(name = "group_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<User> users = new HashSet<>();

    @ManyToOne (cascade = CascadeType.MERGE)
    @JoinColumn(name = "group_status_id")
    private GroupStatus groupStatus;

    public Group() {
    }

    public Group(Sku sku, Date createdAt, Date expiredAt, int count, String region, GroupStatus groupStatus) {
        this.sku = sku;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
        this.count = count;
        this.region = region;
        this.groupStatus = groupStatus;
    }
}
