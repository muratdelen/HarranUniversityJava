package com.harran.oturum.service.authority;

import com.harran.oturum.dao.authority.UserPermissionRepo;
import com.harran.oturum.model.authority.UserPermission;
import com.harran.oturum.service.oauth.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserPermissionService {
    @Autowired
    private UserPermissionRepo userPermissionRepo;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    public Iterable<UserPermission> getAllUserPermissions() {
        return userPermissionRepo.findByActiveTrue();
    }
    public UserPermission getUserPermissionById(long id) {
        return userPermissionRepo.findByIdAndActiveTrue(id);
    }
    public Iterable<UserPermission> getUserPermissionsByUserId(long userId) {
        return userPermissionRepo.findByUserIdAndActiveTrue(userId);
    }
    public Iterable<UserPermission> findByApplicationAndUserIdAndAddAndActiveTrue(long applicationId, long userId, boolean add) {
        return userPermissionRepo.findByApplicationIdAndUserIdAndAddAndActiveTrue(applicationId, userId, add);
    }
    public Iterable<UserPermission> getUserPermissionsByTitle(String title, String description) {
        return userPermissionRepo.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndActiveTrue(title, description);
    }
    public UserPermission createUserPermission(UserPermission userPermission) {
        userPermission.setCratedByUser(customUserDetailsService.logedUser);
        return userPermissionRepo.save(userPermission);
    }
    public UserPermission updateUserPermission(UserPermission old_userPermission, UserPermission new_userPermission) {
        old_userPermission.setUpdatedByUser(customUserDetailsService.logedUser);
        old_userPermission.setTitle(new_userPermission.getTitle());
        old_userPermission.setDescription(new_userPermission.getDescription());
        return userPermissionRepo.save(old_userPermission);
    }
    public UserPermission deleteUserPermission(UserPermission old_userPermission) {
            old_userPermission.setDeletedByUser(customUserDetailsService.logedUser);
            old_userPermission.setActive(false);
            return userPermissionRepo.save(old_userPermission);
    }

}
