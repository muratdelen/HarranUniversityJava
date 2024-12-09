package com.harran.oturum.model.log;

import com.harran.oturum.model.authority.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity
@Table(name="logs_login")
public class LogLogin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
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
    @JoinColumn(name = "logged_user_id", referencedColumnName = "id")// foreign key
    private User loggedUser;//oturum yapan kullanıcı bilgisi
    @ManyToOne
    @JoinColumn(name = "crated_by_user_id", referencedColumnName = "id")// foreign key
    private User cratedByUser;
    @ManyToOne
    @JoinColumn(name = "updated_by_user_id", referencedColumnName = "id") // foreign key
    private User updatedByUser;
    @ManyToOne
    @JoinColumn(name = "deleted_by_user_id", referencedColumnName = "id") // foreign key
    private User deletedByUser;
    @Column(name = "createdAt", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "updateAt")
    private LocalDateTime updateAt;
    private boolean active;
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now(); // Set the creation time when the entity is saved
        updateAt = LocalDateTime.now(); // Initialize updatedAt as well
        active = true;
    }
    @PreUpdate
    protected void onUpdate() {
        updateAt = LocalDateTime.now(); // Update this field whenever the entity is updated
    }
    public LogLogin() {
        this.active = true;
    }
    public LogLogin(String method, String url, String ip, String userAgent) {
        this.method = method;
        this.url = url;
        this.ip = ip;
        this.userAgent = userAgent;
        this.referer = ip + ":" + userAgent;
        this.refererHost = ip + ":" + userAgent;
        this.refererPort = ip + ":" + userAgent;
    }
}
