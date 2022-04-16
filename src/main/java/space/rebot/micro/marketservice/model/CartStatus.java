package space.rebot.micro.marketservice.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "cart_status")
public class CartStatus {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    public CartStatus(String name) {
        this.name = name;
    }

    public CartStatus() {
    }
}
