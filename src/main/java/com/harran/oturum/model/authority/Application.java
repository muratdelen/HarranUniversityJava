package com.harran.oturum.model.authority;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity
@Table(name="applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private String ip;
    private String url;
    private boolean isUniversitySoftwareApplication;
    @ManyToOne
    @JoinColumn(name = "developed_by_user_id", referencedColumnName = "id")// foreign key
    private User developedByUser;//uygulama kim tarafından geliştirildiği atanacaktır

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
    public Application() {
        this.active = true;
    }
    public Application(String name, String description, boolean isUniversitySoftwareApplication, String ip, String url, User deletedByUser) {
        this.name = name;
        this.description = description;
        this.isUniversitySoftwareApplication = isUniversitySoftwareApplication;
        this.ip = ip;
        this.url = url;
        this.deletedByUser = deletedByUser;
        this.active = true;
    }
}
