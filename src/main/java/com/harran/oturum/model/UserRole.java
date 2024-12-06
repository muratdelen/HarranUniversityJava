package com.harran.oturum.model;

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
    private boolean is_active = true;
}
