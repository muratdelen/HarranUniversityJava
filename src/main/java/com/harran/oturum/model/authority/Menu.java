package com.harran.oturum.model.authority;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "menus")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "application_id", referencedColumnName = "id")// foreign key
    private Application application;
    private Long parentId;
    private String name;
    private String url;
    private String icon;
    private String description;
    private String category;
    private String type;
    private String status;
    private String language;
    private boolean show;
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

    public Menu() {
        this.active = true;
    }
    public Menu(Application application, String name, String url, String description, String icon, Long parentId, String category, String type, String status, String language, boolean show) {
        this.application = application;
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.parentId = parentId;
        this.category = category;
        this.type = type;
        this.status = status;
        this.language = language;
        this.show = show;
        this.active = true;
    }

}
