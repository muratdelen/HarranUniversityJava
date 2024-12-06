package com.harran.oturum.model;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "menus")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long parentId;
    private String name;
    private String url;
    private String icon;
    private String description;
//    private String image;
    private String author;
    private String category;
    private String type;
    private String status;
    private boolean is_show;

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
    private boolean isActive = true;

}
