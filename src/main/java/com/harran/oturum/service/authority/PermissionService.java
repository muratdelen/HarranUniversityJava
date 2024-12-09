package com.harran.oturum.service.authority;

import com.harran.oturum.dao.authority.PermissionRepo;
import com.harran.oturum.model.authority.Permission;
import com.harran.oturum.service.oauth.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {
    @Autowired
    private PermissionRepo permissionRepo;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    public Iterable<Permission> getAllPermissions() {
        return permissionRepo.findByActiveTrue();
    }
    public Permission getPermissionById(long id) {
        return permissionRepo.findByIdAndActiveTrue(id);
    }
    public Iterable<Permission> getPermissionsByName(String name, String description) {
        return permissionRepo.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndActiveTrue(name, description);
    }
    public Permission createPermission(Permission permission) {
        permission.setCratedByUser(customUserDetailsService.logedUser);
        return permissionRepo.save(permission);
    }
    public Permission updatePermission(Permission old_permission, Permission new_permission) {
        old_permission.setUpdatedByUser(customUserDetailsService.logedUser);
        old_permission.setName(new_permission.getName());
        old_permission.setDescription(new_permission.getDescription());
        return permissionRepo.save(old_permission);
    }
    public Permission deletePermission(Permission old_permission) {
            old_permission.setDeletedByUser(customUserDetailsService.logedUser);
            old_permission.setActive(false);
            return permissionRepo.save(old_permission);
    }

}
