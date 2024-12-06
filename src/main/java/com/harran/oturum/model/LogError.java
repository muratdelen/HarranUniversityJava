package com.harran.oturum.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "logs_error")
public class LogError {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String message;
    private LocalDateTime timestamp;
    private String stackTrace;
    private String className;
    private String methodName;
    private String fileName;
    private String method;
    private String url;
    private String ip;
    private String userAgent;
    private String referer;
    private String refererHost;
    private String refererPort;
    private String refererUserAgent;

    //Standart bilgiler
    @ManyToOne
    @JoinColumn(name = "crated_by_user_id", referencedColumnName = "id")// foreign key
    private User cratedByUser;
    @ManyToOne
    @JoinColumn(name = "updated_by_user_id", referencedColumnName = "id") // foreign key
    private User updatedByUser;
    @ManyToOne
    @JoinColumn(name = "deleted_by_user_id", referencedColumnName = "id") // foreign key
    private User deletedByUser;
    @Column(name = "created", nullable = false, updatable = false)
    private LocalDateTime created;
    @Column(name = "modified")
    private LocalDateTime modified;
    @PrePersist
    protected void onCreate() {
        created = LocalDateTime.now(); // Set the creation time when the entity is saved
        modified = LocalDateTime.now(); // Initialize updatedAt as well
    }
    @PreUpdate
    protected void onUpdate() {
        modified = LocalDateTime.now(); // Update this field whenever the entity is updated
    }
    private boolean isActive = true;

}
