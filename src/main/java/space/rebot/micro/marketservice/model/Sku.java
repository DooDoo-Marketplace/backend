package space.rebot.micro.marketservice.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table (name = "sku")
public class Sku {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;
}
