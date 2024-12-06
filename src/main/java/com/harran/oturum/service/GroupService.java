package com.harran.oturum.service;

import com.harran.oturum.dao.GroupRepo;
import com.harran.oturum.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    @Autowired
    private GroupRepo groupRepo;
    public Iterable<Group> getAllGroups() {
        return groupRepo.findAll();
    }
    public Group getGroupById(int id) {
        return groupRepo.findById(id);
    }
    public Group createGroup(Group group) {
        return groupRepo.save(group);
    }
    public Group updateGroup(Group group) {
        return groupRepo.save(group);
    }
    public Group deleteGroup(int id) {
        Group group = groupRepo.findById(id);
        group.set_active(false);
        return groupRepo.save(group);
    }

}
