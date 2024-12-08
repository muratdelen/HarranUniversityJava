package com.harran.oturum.model.authority;

import jakarta.persistence.*;

import java.time.LocalDateTime;

public class UserAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String description;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")  // foreign key
    private User user;
    @ManyToOne
    @JoinColumn(name = "authority_id", referencedColumnName = "id")  // foreign key
    private _Authority authority;


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
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now(); // Set the creation time when the entity is saved
        updateAt = LocalDateTime.now(); // Initialize updatedAt as well
    }
    @PreUpdate
    protected void onUpdate() {
        updateAt = LocalDateTime.now(); // Update this field whenever the entity is updated
    }
    private boolean active = true;
}
