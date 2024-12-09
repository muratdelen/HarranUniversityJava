package com.harran.oturum.service.authority;

import com.harran.oturum.dao.authority.GroupRepo;
import com.harran.oturum.model.authority.Group;
import com.harran.oturum.service.oauth.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {
    @Autowired
    private GroupRepo groupRepo;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    public Iterable<Group> getAllGroups() {
        return groupRepo.findByActiveTrue();
    }
    public Group getGroupById(long id) {
        return groupRepo.findByIdAndActiveTrue(id);
    }
    public Iterable<Group> getGroupsByName(String name, String description) {
        return groupRepo.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndActiveTrue(name, description);
    }
    public Group createGroup(Group group) {
        group.setCratedByUser(customUserDetailsService.logedUser);
        return groupRepo.save(group);
    }
    public Group updateGroup(Group old_group, Group new_group) {
        old_group.setUpdatedByUser(customUserDetailsService.logedUser);
        old_group.setName(new_group.getName());
        old_group.setDescription(new_group.getDescription());
        return groupRepo.save(old_group);
    }
    public Group deleteGroup(Group old_group) {
            old_group.setDeletedByUser(customUserDetailsService.logedUser);
            old_group.setActive(false);
            return groupRepo.save(old_group);
    }

}
