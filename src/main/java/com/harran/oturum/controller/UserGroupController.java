package com.harran.oturum.controller;

import com.harran.oturum.model.authority.UserGroup;
import com.harran.oturum.service.authority.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


@RestController
public class UserGroupController
{
    @Autowired
    private UserGroupService userGroupService;//userGroup servise buraya atamasını yap

    //Aktif tüm grupları get ile getir.
    @GetMapping("userGroups")
    public ResponseEntity<Iterable<UserGroup>> getAllUserGroups(){
        return ResponseEntity.ok(userGroupService.getAllUserGroups());
    }
    //verilen grup id göre grup bilgilerini getir.
    @GetMapping("userGroup/{userGroupId}")
    public ResponseEntity<UserGroup> getUserGroupById(@PathVariable("userGroupId") Integer id){
        UserGroup oldUserGroup = userGroupService.getUserGroupById(id);
        if(oldUserGroup != null){
            return ResponseEntity.ok(oldUserGroup);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //Yeni bir grup eklemek için bilgileri yollanır.
    @PostMapping("userGroup")
    public ResponseEntity<UserGroup> createUserGroup(@RequestBody UserGroup userGroup){
        //UserGroup newUserGroup = new UserGroup();
        //newUserGroup.setTitle(userGroup.getTitle());
        //newUserGroup.setDescription(userGroup.getDescription());
        return ResponseEntity.ok(userGroupService.createUserGroup(userGroup));
    }
    //Tüm grup içinde grup adı ve açıklaması araması yapılıyor
    @GetMapping("search_userGroups/{searchText}")
    public ResponseEntity<Iterable<UserGroup>> searchUserGroup(@PathVariable("searchText") String searchText){
        if(!Objects.equals(searchText, "")){
            return ResponseEntity.ok(userGroupService.getUserGroupsByTitle(searchText,searchText));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //Belirlenen grup id göre grup bilgisi getiriliyor
    @PutMapping("userGroup/{userGroupId}")
    public ResponseEntity<UserGroup> updateUserGroup(@PathVariable("userGroupId") Integer id, @RequestBody UserGroup userGroup){
        UserGroup oldUserGroup = userGroupService.getUserGroupById(id);
        if(oldUserGroup != null){
            return ResponseEntity.ok(userGroupService.updateUserGroup(oldUserGroup, userGroup));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //Grup bilgisi silme işlemi grubu pasif hale getirilerek yapılmaktadır.
    @DeleteMapping("userGroup/{userGroupId}")
    public ResponseEntity<UserGroup> deleteUserGroup(@PathVariable("userGroupId") Integer id){
        UserGroup oldUserGroup = userGroupService.getUserGroupById(id);
        if(oldUserGroup != null){
            return ResponseEntity.ok(userGroupService.deleteUserGroup(oldUserGroup));
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
