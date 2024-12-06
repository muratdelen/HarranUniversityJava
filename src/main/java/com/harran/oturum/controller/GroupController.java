package com.harran.oturum.controller;

import com.harran.oturum.model.Group;
import com.harran.oturum.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class GroupController
{
    @Autowired
    private GroupService groupService;
    @GetMapping("groups")
    public Iterable<Group> getAllGroups(){
        return groupService.getAllGroups();
    }
    @GetMapping("group/{groupId}")
    public Group getGroupById(@PathVariable("groupId") Integer id){
        return groupService.getGroupById(id);
    }
    @PutMapping("group/{groupId}")
    public Group updateGroup(@PathVariable("groupId") Integer id, Group group){
        group.setId(id);
        return groupService.updateGroup(group);
    }
    @DeleteMapping("group/{groupId}")
    public Group deleteGroup(@PathVariable("groupId") Integer id){
        return groupService.deleteGroup(id);
    }


}
