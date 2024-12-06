package com.harran.oturum.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String password;
    private String title;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String work_phone;
    private String tc_no;
    private String foreign_no;
    private boolean is_academic = false;
    private boolean is_administrative = false;
    private boolean is_student = false;

    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id")  // foreign key
    private Group group;

    //Standart bilgiler
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
