package com.harran.oturum.service.authority;

import com.harran.oturum.dao.authority.GroupRoleRepo;
import com.harran.oturum.model.authority.GroupRole;
import com.harran.oturum.service.oauth.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupRoleService {
    @Autowired
    private GroupRoleRepo groupRoleRepo;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    public Iterable<GroupRole> getAllGroupRoles() {
        return groupRoleRepo.findByActiveTrue();
    }
    public GroupRole getGroupRoleById(long id) {
        return groupRoleRepo.findByIdAndActiveTrue(id);
    }
    public Iterable<GroupRole> getGroupRolesByUserId(long groupId) {
        return groupRoleRepo.findByGroupIdAndActiveTrue(groupId);
    }
    public Iterable<GroupRole> getGroupRolesByTitle(String title, String description) {
        return groupRoleRepo.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndActiveTrue(title, description);
    }
    public GroupRole createGroupRole(GroupRole groupRole) {
        groupRole.setCratedByUser(customUserDetailsService.logedUser);
        return groupRoleRepo.save(groupRole);
    }
    public GroupRole updateGroupRole(GroupRole old_groupRole, GroupRole new_groupRole) {
        old_groupRole.setUpdatedByUser(customUserDetailsService.logedUser);
        old_groupRole.setTitle(new_groupRole.getTitle());
        old_groupRole.setDescription(new_groupRole.getDescription());
        return groupRoleRepo.save(old_groupRole);
    }
    public GroupRole deleteGroupRole(GroupRole old_groupRole) {
            old_groupRole.setDeletedByUser(customUserDetailsService.logedUser);
            old_groupRole.setActive(false);
            return groupRoleRepo.save(old_groupRole);
    }

}
