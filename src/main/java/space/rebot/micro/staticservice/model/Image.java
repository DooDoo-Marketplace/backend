package space.rebot.micro.staticservice.model;


import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "images")
public class Image {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type="pg-uuid")
    private UUID id;

    @Column(name = "filename")
    private String filename;

    @Column(name = "hashsum")
    private String hashsum;

    @Column(name = "filesize")
    private long filesize;

    @Column(name = "mimetype")
    private String mimetype;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    public Image() {
    }

    public Image(String filename, String hashsum, long filesize, String mimetype, Date createdAt, Date updatedAt) {
        this.filename = filename;
        this.hashsum = hashsum;
        this.filesize = filesize;
        this.mimetype = mimetype;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
