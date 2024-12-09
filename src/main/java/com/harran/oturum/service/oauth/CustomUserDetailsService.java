package com.harran.oturum.service.oauth;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.harran.oturum.dao.authority.UserRepo;
import com.harran.oturum.model.authority.User;
import com.harran.oturum.model.authority.UserPrincipal;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo repo;
    public User logedUser;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logedUser = repo.findByUsername(username);
        if (logedUser == null) {
            System.out.println("User 404");
            throw new UsernameNotFoundException("User 404");//403
        }
        return new UserPrincipal(logedUser);
    }

}