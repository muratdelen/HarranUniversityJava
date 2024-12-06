package com.harran.oturum.service;

import com.harran.oturum.dao.GroupRepo;
import com.harran.oturum.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {
    @Autowired
    private GroupRepo groupRepo;
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    public Iterable<Group> getAllGroups() {
        return groupRepo.findAll();
    }
    public Group getGroupById(int id) {
        return groupRepo.findById(id);
    }
    public Group createGroup(Group group) {
        group.setCratedByUser(myUserDetailsService.logedUser);
        return groupRepo.save(group);
    }
    public Group updateGroup(Group old_grup, Group new_group) {
        old_grup.setUpdatedByUser(myUserDetailsService.logedUser);
        old_grup.setName(new_group.getName());
        old_grup.setDescription(new_group.getDescription());
        return groupRepo.save(old_grup);
    }
    public Group deleteGroup(Group old_group) {
            old_group.setDeletedByUser(myUserDetailsService.logedUser);
            old_group.setActive(false);
            return groupRepo.save(old_group);
    }

}
