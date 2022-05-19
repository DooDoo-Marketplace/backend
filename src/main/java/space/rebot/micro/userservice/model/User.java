package space.rebot.micro.userservice.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "registered_at")
    private Date registeredAt;
    @Column(name = "online_at")
    private Date onlineAt;
    @Column(name = "phone")
    private String phone;
    @ManyToMany(cascade = CascadeType.MERGE, fetch=FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "roles_id") }
    )
    private List<Role> roles;


    public User(){
        this.roles = new LinkedList<>();
    }
    public void addRole(Role role){
        this.roles.add(role);
    }
}
