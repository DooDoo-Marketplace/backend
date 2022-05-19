package space.rebot.micro.userservice.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "auth_requests")
public class AuthRequest {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "phone")
    private String phone;
    @Column(name = "code", nullable = false)
    private int code;
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "attempts")
    private int attempts;

    public AuthRequest() {
    }
}
