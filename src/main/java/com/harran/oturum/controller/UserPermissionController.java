package com.harran.oturum.controller;

import com.harran.oturum.model.authority.UserPermission;
import com.harran.oturum.service.authority.UserPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


@RestController
public class UserPermissionController
{
    @Autowired
    private UserPermissionService userPermissionService;//userPermission servise buraya atamasını yap

    //Aktif tüm grupları get ile getir.
    @GetMapping("userPermissions")
    public ResponseEntity<Iterable<UserPermission>> getAllUserPermissions(){
        return ResponseEntity.ok(userPermissionService.getAllUserPermissions());
    }
    //verilen grup id göre grup bilgilerini getir.
    @GetMapping("userPermission/{userPermissionId}")
    public ResponseEntity<UserPermission> getUserPermissionById(@PathVariable("userPermissionId") Integer id){
        UserPermission oldUserPermission = userPermissionService.getUserPermissionById(id);
        if(oldUserPermission != null){
            return ResponseEntity.ok(oldUserPermission);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //Yeni bir grup eklemek için bilgileri yollanır.
    @PostMapping("userPermission")
    public ResponseEntity<UserPermission> createUserPermission(@RequestBody UserPermission userPermission){
        //UserPermission newUserPermission = new UserPermission();
        //newUserPermission.setTitle(userPermission.getTitle());
        //newUserPermission.setDescription(userPermission.getDescription());
        //newUserPermission.setApplication(userPermission.getApplication());
       // newUserPermission.setUser(userPermission.getUser());
       // newUserPermission.setPermission(userPermission.getPermission());
       // newUserPermission.setAdd(userPermission.isAdd());
        return ResponseEntity.ok(userPermissionService.createUserPermission(userPermission));
    }
    //Tüm grup içinde grup adı ve açıklaması araması yapılıyor
    @GetMapping("search_userPermissions/{searchText}")
    public ResponseEntity<Iterable<UserPermission>> searchUserPermission(@PathVariable("searchText") String searchText){
        if(!Objects.equals(searchText, "")){
            return ResponseEntity.ok(userPermissionService.getUserPermissionsByTitle(searchText,searchText));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //Belirlenen grup id göre grup bilgisi getiriliyor
    @PutMapping("userPermission/{userPermissionId}")
    public ResponseEntity<UserPermission> updateUserPermission(@PathVariable("userPermissionId") Integer id, @RequestBody UserPermission userPermission){
        UserPermission oldUserPermission = userPermissionService.getUserPermissionById(id);
        if(oldUserPermission != null){
            return ResponseEntity.ok(userPermissionService.updateUserPermission(oldUserPermission, userPermission));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //Grup bilgisi silme işlemi grubu pasif hale getirilerek yapılmaktadır.
    @DeleteMapping("userPermission/{userPermissionId}")
    public ResponseEntity<UserPermission> deleteUserPermission(@PathVariable("userPermissionId") Integer id){
        UserPermission oldUserPermission = userPermissionService.getUserPermissionById(id);
        if(oldUserPermission != null){
            return ResponseEntity.ok(userPermissionService.deleteUserPermission(oldUserPermission));
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
