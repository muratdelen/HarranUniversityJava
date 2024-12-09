package com.harran.oturum.controller;

import com.harran.oturum.model.authority.UserRole;
import com.harran.oturum.service.authority.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


@RestController
public class UserRoleController
{
    @Autowired
    private UserRoleService userRoleService;//userRole servise buraya atamasını yap

    //Aktif tüm grupları get ile getir.
    @GetMapping("userRoles")
    public ResponseEntity<Iterable<UserRole>> getAllUserRoles(){
        return ResponseEntity.ok(userRoleService.getAllUserRoles());
    }
    //verilen grup id göre grup bilgilerini getir.
    @GetMapping("userRole/{userRoleId}")
    public ResponseEntity<UserRole> getUserRoleById(@PathVariable("userRoleId") Integer id){
        UserRole oldUserRole = userRoleService.getUserRoleById(id);
        if(oldUserRole != null){
            return ResponseEntity.ok(oldUserRole);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //Yeni bir grup eklemek için bilgileri yollanır.
    @PostMapping("userRole")
    public ResponseEntity<UserRole> createUserRole(@RequestBody UserRole userRole){
        UserRole newUserRole = new UserRole();
        newUserRole.setTitle(userRole.getTitle());
        newUserRole.setDescription(userRole.getDescription());
        return ResponseEntity.ok(userRoleService.createUserRole(newUserRole));
    }
    //Tüm grup içinde grup adı ve açıklaması araması yapılıyor
    @GetMapping("search_userRoles/{searchText}")
    public ResponseEntity<Iterable<UserRole>> searchUserRole(@PathVariable("searchText") String searchText){
        if(!Objects.equals(searchText, "")){
            return ResponseEntity.ok(userRoleService.getUserRolesByTitle(searchText,searchText));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //Belirlenen grup id göre grup bilgisi getiriliyor
    @PutMapping("userRole/{userRoleId}")
    public ResponseEntity<UserRole> updateUserRole(@PathVariable("userRoleId") Integer id, @RequestBody UserRole userRole){
        UserRole oldUserRole = userRoleService.getUserRoleById(id);
        if(oldUserRole != null){
            return ResponseEntity.ok(userRoleService.updateUserRole(oldUserRole, userRole));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //Grup bilgisi silme işlemi grubu pasif hale getirilerek yapılmaktadır.
    @DeleteMapping("userRole/{userRoleId}")
    public ResponseEntity<UserRole> deleteUserRole(@PathVariable("userRoleId") Integer id){
        UserRole oldUserRole = userRoleService.getUserRoleById(id);
        if(oldUserRole != null){
            return ResponseEntity.ok(userRoleService.deleteUserRole(oldUserRole));
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
