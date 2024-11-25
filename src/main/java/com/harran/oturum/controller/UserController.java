package com.harran.oturum.controller;

import com.harran.oturum.model.User;
import com.harran.oturum.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private UserService service;

    @PostMapping("register")
    public User register(@RequestBody User user) {return service.saveUser(user);}

}
