package space.rebot.micro.staticservice.model;


import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;
//import java.util.UUID;

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

//    @Column(name = "encoding")
//    private String encoding;

//    @Column(name = "extname")
//    private String extname;

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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getHashsum() {
        return hashsum;
    }

    public void setHashsum(String hashsum) {
        this.hashsum = hashsum;
    }

    public long getFilesize() {
        return filesize;
    }

    public void setFilesize(long filesize) {
        this.filesize = filesize;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

//    public String getEncoding() {
//        return encoding;
//    }
//
//    public void setEncoding(String encoding) {
//        this.encoding = encoding;
//    }

//    public String getExtname() {
//        return extname;
//    }
//
//    public void setExtname(String extname) {
//        this.extname = extname;
//    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
