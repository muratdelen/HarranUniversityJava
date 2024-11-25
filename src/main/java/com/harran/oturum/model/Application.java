package com.harran.oturum.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String description;

    @OneToOne
    @JoinColumn(name = "created_by_user_id", referencedColumnName = "id")  // foreign key
    private User cratedByUser;

    @OneToOne
    @JoinColumn(name = "update_by_user_id", referencedColumnName = "id")  // foreign key
    private User updatedByUser;
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
    private boolean is_active;
}
