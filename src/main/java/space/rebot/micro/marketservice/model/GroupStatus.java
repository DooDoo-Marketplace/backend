package space.rebot.micro.marketservice.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "group_status")
public class GroupStatus {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    public GroupStatus(String name) {
        this.name = name;
    }

    public GroupStatus() {
    }
}
