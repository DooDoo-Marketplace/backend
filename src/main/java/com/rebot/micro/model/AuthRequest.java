package com.rebot.micro.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "auth_requests")
public class AuthRequest {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "phone")
    private String phone;
    @Column(name = "code", nullable = false)
    private int code;
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "attempts")
    private int attempts;

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
