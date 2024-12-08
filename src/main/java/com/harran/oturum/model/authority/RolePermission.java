package com.harran.oturum.model.authority;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="role_permissions")
public class RolePermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String description;
    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")  // foreign key
    private Role role;
    @ManyToOne
    @JoinColumn(name = "page_url_id", referencedColumnName = "id")  // foreign key
    private PageUrl pageUrl;
    @ManyToOne
    @JoinColumn(name = "permission_id", referencedColumnName = "id")  // foreign key
    private Permission permission;

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
    public RolePermission() {
        this.active = true;
    }
    public RolePermission(String title, String description, Role role, PageUrl pageUrl, Permission permission) {
        this.title = title;
        this.description = description;
        this.role = role;
        this.pageUrl = pageUrl;
        this.permission = permission;
        this.active = true;
    }
}
