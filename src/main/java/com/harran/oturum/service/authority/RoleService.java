package com.harran.oturum.service.authority;

import com.harran.oturum.dao.authority.RoleRepo;
import com.harran.oturum.model.authority.Role;
import com.harran.oturum.service.oauth.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    public Iterable<Role> getAllRoles() {
        return roleRepo.findByActiveTrue();
    }
    public Role getRoleById(long id) {
        return roleRepo.findByIdAndActiveTrue(id);
    }
    public Iterable<Role> getRolesByName(String name, String description) {
        return roleRepo.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndActiveTrue(name, description);
    }
    public Role createRole(Role Role) {
        Role.setCratedByUser(customUserDetailsService.logedUser);
        return roleRepo.save(Role);
    }
    public Role updateRole(Role old_Role, Role new_Role) {
        old_Role.setUpdatedByUser(customUserDetailsService.logedUser);
        old_Role.setName(new_Role.getName());
        old_Role.setDescription(new_Role.getDescription());
        return roleRepo.save(old_Role);
    }
    public Role deleteRole(Role old_Role) {
            old_Role.setDeletedByUser(customUserDetailsService.logedUser);
            old_Role.setActive(false);
            return roleRepo.save(old_Role);
    }

}
