package com.harran.oturum.model;

import jakarta.persistence.*;
import jdk.jfr.Enabled;

import java.time.LocalDateTime;
import java.util.Date;

@Enabled
@Table(name = "acces_logs")
public class AccessLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long userId;
    private String method;
    private String url;
    private String ip;
    private String userAgent;
    private String referer;
    private String refererHost;
    private String refererPort;
    private String refererUserAgent;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now(); // Set the creation time when the entity is saved
        updatedAt = LocalDateTime.now(); // Initialize updatedAt as well
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now(); // Update this field whenever the entity is updated
    }


}
