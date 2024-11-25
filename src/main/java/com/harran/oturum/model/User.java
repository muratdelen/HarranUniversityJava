package com.harran.oturum.model;

import jakarta.persistence.*;
import lombok.Data;

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
    private String yabanci_tc_no;
    private boolean is_academic;
    private boolean is_administrative;
    private boolean is_student;
    private boolean is_active;

}
