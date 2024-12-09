package com.harran.oturum.service.oauth;

import com.harran.oturum.dao.authority.*;
import com.harran.oturum.model.authority.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAuthority {
    private final RoleRepo roleRepo;
    private ApplicationRepo applicationRepo;
    private UserRepo userRepo;
    private UserGroupRepo userGroupRepo;
    private GroupRepo groupRepo;
    private UserRoleRepo userRoleRepo;
    private GroupRoleRepo groupRoleRepo;
    private PageUrlRepo pageUrlRepo;

    public UserAuthority(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }


    public Iterable<Permission> getUserPermissions(String username, String name, String pageUrlStr) {
        // yetki isteyen kullanıcının bilgisi alındı
        User user = userRepo.findByUsername(username);
        List<UserGroup> userGroups = userGroupRepo.findByUserIdAndActiveTrue(user.getId());
        Application application = applicationRepo.findByNameAndActiveTrue(name);
        //hangi uygulama ve sayfa için olduğu bilgisi alındı
        PageUrl pageUrl = pageUrlRepo.findByApplicationIdAndUrlAndActiveTrue(application.getId(), pageUrlStr);

        //seçilen uygulamaya göre kullanıcı rolleri getirildi
        Iterable<UserRole> userRoles = userRoleRepo.findByApplicationAndUserIdAndActiveTrue(application.getId(), user.getId());

        List<Role> theRoles = List.of();
        //Kullanıcıya ait roler alındı
        for (UserRole userRole : userRoles) {
            theRoles.add(userRole.getRole()) ;
        }

        for (UserGroup group : userGroups) {
            //
            List<GroupRole> groupRoles = groupRoleRepo.findByApplicationAndGroupIdAndActiveTrue(application.getId(), group.getId());
            for (GroupRole groupRole : groupRoles) {
                theRoles.add(groupRole.getRole()) ;
            }
        }
        List<Permission> thePermissions = List.of();
        for (Role role : theRoles) {

        }
      return thePermissions;
    }


}
