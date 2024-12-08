package com.harran.oturum.controller;

import com.harran.oturum.model.authority.Group;
import com.harran.oturum.service.authority.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


@RestController
public class GroupController
{
    @Autowired
    private GroupService groupService;//group servise buraya atamasını yap

    //Aktif tüm grupları get ile getir.
    @GetMapping("groups")
    public ResponseEntity<Iterable<Group>> getAllGroups(){
        return ResponseEntity.ok(groupService.getAllGroups());
    }
    //verilen grup id göre grup bilgilerini getir.
    @GetMapping("group/{groupId}")
    public ResponseEntity<Group> getGroupById(@PathVariable("groupId") Integer id){
        Group oldGroup = groupService.getGroupById(id);
        if(oldGroup != null){
            return ResponseEntity.ok(oldGroup);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //Yeni bir grup eklemek için bilgileri yollanır.
    @PostMapping("group")
    public ResponseEntity<Group> createGroup(@RequestBody Group group){
        Group newGroup = new Group();
        newGroup.setName(group.getName());
        newGroup.setDescription(group.getDescription());
        return ResponseEntity.ok(groupService.createGroup(newGroup));
    }
    //Tüm grup içinde grup adı ve açıklaması araması yapılıyor
    @GetMapping("search_groups/{searchText}")
    public ResponseEntity<Iterable<Group>> searchGroup(@PathVariable("searchText") String searchText){
        if(!Objects.equals(searchText, "")){
            return ResponseEntity.ok(groupService.getGroupsByName(searchText,searchText));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //Belirlenen grup id göre grup bilgisi getiriliyor
    @PutMapping("group/{groupId}")
    public ResponseEntity<Group> updateGroup(@PathVariable("groupId") Integer id, @RequestBody Group group){
        Group oldGroup = groupService.getGroupById(id);
        if(oldGroup != null){
            return ResponseEntity.ok(groupService.updateGroup(oldGroup, group));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //Grup bilgisi silme işlemi grubu pasif hale getirilerek yapılmaktadır.
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
