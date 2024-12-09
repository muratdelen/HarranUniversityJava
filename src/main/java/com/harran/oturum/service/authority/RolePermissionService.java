package com.harran.oturum.service.authority;

import com.harran.oturum.dao.authority.RolePermissionRepo;
import com.harran.oturum.model.authority.RolePermission;
import com.harran.oturum.service.oauth.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolePermissionService {
    @Autowired
    private RolePermissionRepo rolePermissionRepo;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    public Iterable<RolePermission> getAllRolePermissions() {
        return rolePermissionRepo.findByActiveTrue();
    }
    public RolePermission getRolePermissionById(long id) {
        return rolePermissionRepo.findByIdAndActiveTrue(id);
    }
    public Iterable<RolePermission> getRolePermissionsByTitle(String title, String description) {
        return rolePermissionRepo.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndActiveTrue(title, description);
    }
    public RolePermission createRolePermission(RolePermission rolePermission) {
        rolePermission.setCratedByUser(customUserDetailsService.logedUser);
        return rolePermissionRepo.save(rolePermission);
    }
    public RolePermission updateRolePermission(RolePermission old_rolePermission, RolePermission new_rolePermission) {
        old_rolePermission.setUpdatedByUser(customUserDetailsService.logedUser);
        old_rolePermission.setTitle(new_rolePermission.getTitle());
        old_rolePermission.setDescription(new_rolePermission.getDescription());
        return rolePermissionRepo.save(old_rolePermission);
    }
    public RolePermission deleteRolePermission(RolePermission old_rolePermission) {
            old_rolePermission.setDeletedByUser(customUserDetailsService.logedUser);
            old_rolePermission.setActive(false);
            return rolePermissionRepo.save(old_rolePermission);
    }

}
