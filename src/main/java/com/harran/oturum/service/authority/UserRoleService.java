package com.harran.oturum.service.authority;

import com.harran.oturum.dao.authority.UserRoleRepo;
import com.harran.oturum.model.authority.UserRole;
import com.harran.oturum.service.oauth.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {
    @Autowired
    private UserRoleRepo userRoleRepo;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    public Iterable<UserRole> getAllUserRoles() {
        return userRoleRepo.findByActiveTrue();
    }
    public UserRole getUserRoleById(long id) {
        return userRoleRepo.findByIdAndActiveTrue(id);
    }
    public Iterable<UserRole> getUserRolesByUserId(long userId) {
        return userRoleRepo.findByApplicationAndUserIdAndActiveTrue(userId);
    }
    public Iterable<UserRole> getUserRolesByTitle(String title, String description) {
        return userRoleRepo.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndActiveTrue(title, description);
    }
    public UserRole createUserRole(UserRole userRole) {
        userRole.setCratedByUser(customUserDetailsService.logedUser);
        return userRoleRepo.save(userRole);
    }
    public UserRole updateUserRole(UserRole old_userRole, UserRole new_userRole) {
        old_userRole.setUpdatedByUser(customUserDetailsService.logedUser);
        old_userRole.setTitle(new_userRole.getTitle());
        old_userRole.setDescription(new_userRole.getDescription());
        return userRoleRepo.save(old_userRole);
    }
    public UserRole deleteUserRole(UserRole old_userRole) {
            old_userRole.setDeletedByUser(customUserDetailsService.logedUser);
            old_userRole.setActive(false);
            return userRoleRepo.save(old_userRole);
    }

}
