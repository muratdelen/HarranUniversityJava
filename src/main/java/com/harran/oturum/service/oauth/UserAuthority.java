package com.harran.oturum.service.oauth;

import com.harran.oturum.dao.authority.*;
import com.harran.oturum.model.authority.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAuthority {
    @Autowired
    private UserPermissionRepo userPermissionRepo;
    @Autowired
    private RolePermissionRepo rolePermissionRepo;
    @Autowired
    private ApplicationRepo applicationRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserGroupRepo userGroupRepo;
    //private GroupRepo groupRepo;
    @Autowired
    private UserRoleRepo userRoleRepo;
    @Autowired
    private GroupRoleRepo groupRoleRepo;
    @Autowired
    private PageUrlRepo pageUrlRepo;

    public UserAuthority() {
    }

    public Iterable<Permission> getUserPermissions(String username, String applicationName, String pageUrlStr) {
        // yetki isteyen kullanıcının bilgisi alındı
        User user = userRepo.findByUsername(username);
        List<UserGroup> userGroups = userGroupRepo.findByUserIdAndActiveTrue(user.getId());
        Application application = applicationRepo.findByNameAndActiveTrue(applicationName);
        //hangi uygulama ve sayfa için olduğu bilgisi alındı
        PageUrl pageUrl = pageUrlRepo.findByApplicationIdAndUrlAndActiveTrue(application.getId(), pageUrlStr);

        //seçilen uygulamaya göre kullanıcı rolleri getirildi
        Iterable<UserRole> userRoles = userRoleRepo.findByUserIdAndActiveTrue(user.getId());

        List<Role> theRoles = List.of();
        //Kullanıcıya ait roler alındı
        for (UserRole userRole : userRoles) {
            theRoles.add(userRole.getRole()) ;
        }

        for (UserGroup group : userGroups) {
            //Gruba ait roller eklendi
            List<GroupRole> groupRoles = groupRoleRepo.findByGroupIdAndActiveTrue(group.getId());
            for (GroupRole groupRole : groupRoles) {
                theRoles.add(groupRole.getRole()) ;
            }
        }
        //Kişiye ait tüm rollerin seçilen uygulamadaki izinleri çıkarılır.
        List<Permission> thePermissions = List.of();
        for (Role role : theRoles) {
            for(RolePermission rolePermission: rolePermissionRepo.findByRoleAndPageUrlAndActiveTrue(role, pageUrl)){
                thePermissions.add(rolePermission.getPermission()) ;
            }
        }
        //Uygulama için kullancı yetkisine eklenenler izinler
        for (UserPermission userPermission : userPermissionRepo.findByApplicationIdAndUserIdAndAddAndActiveTrue(application.getId(),user.getId(),true)) {
            thePermissions.add(userPermission.getPermission()) ;
        }
        //Uygulama için kullancı yetkisinden kaldırılan izinler
        for (UserPermission userPermission : userPermissionRepo.findByApplicationIdAndUserIdAndAddAndActiveTrue(application.getId(),user.getId(),false)) {
            thePermissions.remove(userPermission.getPermission()) ;
        }

      return thePermissions;
    }


}
