package com.harran.oturum.controller;

import com.harran.oturum.model.Group;
import com.harran.oturum.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class GroupController
{
    @Autowired
    private GroupService groupService;
    @GetMapping("groups")
    public ResponseEntity<Iterable<Group>> getAllGroups(){
        return ResponseEntity.ok(groupService.getAllGroups());
    }
    @GetMapping("group/{groupId}")
    public ResponseEntity<Group> getGroupById(@PathVariable("groupId") Integer id){
        Group oldGroup = groupService.getGroupById(id);
        if(oldGroup != null){
            return ResponseEntity.ok(oldGroup);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("group")
    public ResponseEntity<Group> createGroup(@RequestBody Group group){
        Group newGroup = new Group();
        newGroup.setName(group.getName());
        newGroup.setDescription(group.getDescription());
        return ResponseEntity.ok(groupService.createGroup(newGroup));
    }
    @PutMapping("group/{groupId}")
    public ResponseEntity<Group> updateGroup(@PathVariable("groupId") Integer id, @RequestBody Group group){
        Group oldGroup = groupService.getGroupById(id);
        if(oldGroup != null){
            return ResponseEntity.ok(groupService.updateGroup(oldGroup, group));
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("group/{groupId}")
    public ResponseEntity<Group> deleteGroup(@PathVariable("groupId") Integer id){
        Group oldGroup = groupService.getGroupById(id);
        if(oldGroup != null){
            return ResponseEntity.ok(groupService.deleteGroup(oldGroup));
        }else{
            return ResponseEntity.notFound().build();
        }
    }


}
