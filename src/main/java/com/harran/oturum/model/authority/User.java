package com.harran.oturum.model.authority;

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
    private boolean isAcademic = false;
    private boolean isAdministrative = false;
    private boolean isStudent = false;

    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id")  // foreign key
    private Group group;

    //Standart bilgiler
    @Column(name = "createdAt", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "updateAt")
    private LocalDateTime updateAt;
    private boolean active = true;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now(); // Set the creation time when the entity is saved
        updateAt = LocalDateTime.now(); // Initialize updatedAt as well
    }

    @PreUpdate
    protected void onUpdate() {
        updateAt = LocalDateTime.now(); // Update this field whenever the entity is updated
    }

    public User(){
        this.active = true;
    }
    public User(String username, String password, String firstName, String lastName, String email) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
//    public Collection<Role> getRoles() {
//        return new Role("ROLE_USER");
//    }
}
