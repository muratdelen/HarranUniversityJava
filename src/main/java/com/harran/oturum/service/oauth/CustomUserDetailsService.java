package com.harran.oturum.service.oauth;

import com.harran.oturum.dao.authority.GroupRoleRepo;
import com.harran.oturum.dao.authority.UserGroupRepo;
import com.harran.oturum.dao.authority.UserRoleRepo;
import com.harran.oturum.model.authority.*;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.harran.oturum.dao.authority.UserRepo;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserGroupRepo userGroupRepo;
    @Autowired
    private UserRoleRepo userRoleRepo;
    @Autowired
    private GroupRoleRepo groupRoleRepo;
    public User logedUser;
    public List<String> roles;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logedUser = userRepo.findByUsername(username);
        if (logedUser == null) {
            System.out.println("User 404");
            throw new UsernameNotFoundException("User 404");//403
        }
        try {
            List<UserGroup> userGroups = userGroupRepo.findByUserIdAndActiveTrue(logedUser.getId());
            //seçilen uygulamaya göre kullanıcı rolleri getirildi
            Iterable<UserRole> userRoles = userRoleRepo.findByUserIdAndActiveTrue(logedUser.getId());

            roles  = new java.util.ArrayList<>(List.of());
            //Kullanıcıya ait roler alındı
            for (UserRole userRole : userRoles) {
                roles.add(userRole.getRole().getName());
            }

            for (UserGroup group : userGroups) {
                //Gruba ait roller eklendi
                List<GroupRole> groupRoles = groupRoleRepo.findByGroupIdAndActiveTrue(group.getId());
                for (GroupRole groupRole : groupRoles) {
                    roles.add(groupRole.getRole().getName());
                }
            }
            // Kullanıcının grubu varsa, ona ait rolleri de ekle
            if (logedUser.getGroup() != null) {
                List<GroupRole> groupRoles = groupRoleRepo.findByGroupIdAndActiveTrue(logedUser.getGroup().getId());
                for (GroupRole groupRole : groupRoles) {
                    roles.add(groupRole.getRole().getName());
                }
            }
            return new UserPrincipal(logedUser,roles);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public String getLoggedInUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString(); // Kullanıcı anonimse ya da bir string kullanılıyorsa
        }
    }
}