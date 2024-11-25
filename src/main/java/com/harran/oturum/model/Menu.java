package com.harran.oturum.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jdk.jfr.Enabled;

@Enabled
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
    private boolean is_active;

}
