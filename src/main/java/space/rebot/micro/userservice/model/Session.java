package space.rebot.micro.userservice.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "sessions")
public class Session {
    public static final String SESSION = "session";

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    private User user;
    @Column(name = "token", nullable = false)
    private String token;
    @Column(name = "created_at", nullable = false)
    private Date createdAt;
    @Column(name = "expired", nullable = false)
    private boolean expired;

    public Session() {
    }
}
