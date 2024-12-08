package com.harran.oturum.model.authority;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="user_roles")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String description;
    @ManyToOne
    @JoinColumn(name = "application_id", referencedColumnName = "id")  // foreign key
    private Application application;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")  // foreign key
    private User user;
    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")  // foreign key
    private Role role;

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
    public UserRole() {
        this.active = true;
    }
    public UserRole(String title, String description, Application application, User user, Role role) {
        this.title = title;
        this.description = description;
        this.application = application;
        this.user = user;
        this.role = role;
        this.active = true;
    }
}
