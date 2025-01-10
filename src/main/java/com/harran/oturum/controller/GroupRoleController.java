package com.harran.oturum.controller;

import com.harran.oturum.model.authority.GroupRole;
import com.harran.oturum.service.authority.GroupRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


@RestController
public class GroupRoleController
{
    @Autowired
    private GroupRoleService groupRoleService;//groupRole servise buraya atamasını yap

    //Aktif tüm grupları get ile getir.
    @GetMapping("groupRoles")
    public ResponseEntity<Iterable<GroupRole>> getAllGroupRoles(){
        return ResponseEntity.ok(groupRoleService.getAllGroupRoles());
    }
    //verilen grup id göre grup bilgilerini getir.
    @GetMapping("groupRole/{groupRoleId}")
    public ResponseEntity<GroupRole> getGroupRoleById(@PathVariable("groupRoleId") Integer id){
        GroupRole oldGroupRole = groupRoleService.getGroupRoleById(id);
        if(oldGroupRole != null){
            return ResponseEntity.ok(oldGroupRole);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //Yeni bir grup eklemek için bilgileri yollanır.
    @PostMapping("groupRole")
    public ResponseEntity<GroupRole> createGroupRole(@RequestBody GroupRole groupRole){
       // GroupRole newGroupRole = new GroupRole();
        //newGroupRole.setTitle(groupRole.getTitle());
       // newGroupRole.setDescription(groupRole.getDescription());
        return ResponseEntity.ok(groupRoleService.createGroupRole(groupRole));
    }
    //Tüm grup içinde grup adı ve açıklaması araması yapılıyor
    @GetMapping("search_groupRoles/{searchText}")
    public ResponseEntity<Iterable<GroupRole>> searchGroupRole(@PathVariable("searchText") String searchText){
        if(!Objects.equals(searchText, "")){
            return ResponseEntity.ok(groupRoleService.getGroupRolesByTitle(searchText,searchText));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //Belirlenen grup id göre grup bilgisi getiriliyor
    @PutMapping("groupRole/{groupRoleId}")
    public ResponseEntity<GroupRole> updateGroupRole(@PathVariable("groupRoleId") Integer id, @RequestBody GroupRole groupRole){
        GroupRole oldGroupRole = groupRoleService.getGroupRoleById(id);
        if(oldGroupRole != null){
            return ResponseEntity.ok(groupRoleService.updateGroupRole(oldGroupRole, groupRole));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //Grup bilgisi silme işlemi grubu pasif hale getirilerek yapılmaktadır.
    @DeleteMapping("groupRole/{groupRoleId}")
    public ResponseEntity<GroupRole> deleteGroupRole(@PathVariable("groupRoleId") Integer id){
        GroupRole oldGroupRole = groupRoleService.getGroupRoleById(id);
        if(oldGroupRole != null){
            return ResponseEntity.ok(groupRoleService.deleteGroupRole(oldGroupRole));
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
