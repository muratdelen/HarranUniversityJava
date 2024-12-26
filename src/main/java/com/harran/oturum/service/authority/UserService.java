package com.harran.oturum.service.authority;

import com.harran.oturum.dao.authority.UserRepo;
import com.harran.oturum.model.authority.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepo userRepo;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    //Kullanıcı kayıt olma servisi
    public User saveUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
//        System.out.println(user.getPassword());
        return userRepo.save(user);
    }


    public Iterable<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User getUser(String username) {
        return userRepo.findByUsername(username);
    }

    public User updateUser(String username, User user) {
        return userRepo.save(user);
    }
}
