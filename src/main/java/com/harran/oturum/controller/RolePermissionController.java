package com.harran.oturum.controller;

import com.harran.oturum.model.authority.RolePermission;
import com.harran.oturum.service.authority.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


@RestController
public class RolePermissionController
{
    @Autowired
    private RolePermissionService rolePermissionService;//rolePermission servise buraya atamasını yap

    //Aktif tüm grupları get ile getir.
    @GetMapping("rolePermissions")
    public ResponseEntity<Iterable<RolePermission>> getAllRolePermissions(){
        return ResponseEntity.ok(rolePermissionService.getAllRolePermissions());
    }
    //verilen grup id göre grup bilgilerini getir.
    @GetMapping("rolePermission/{rolePermissionId}")
    public ResponseEntity<RolePermission> getRolePermissionById(@PathVariable("rolePermissionId") Integer id){
        RolePermission oldRolePermission = rolePermissionService.getRolePermissionById(id);
        if(oldRolePermission != null){
            return ResponseEntity.ok(oldRolePermission);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //Yeni bir grup eklemek için bilgileri yollanır.
    @PostMapping("rolePermission")
    public ResponseEntity<RolePermission> createRolePermission(@RequestBody RolePermission rolePermission){
        RolePermission newRolePermission = new RolePermission();
        newRolePermission.setTitle(rolePermission.getTitle());
        newRolePermission.setDescription(rolePermission.getDescription());
        return ResponseEntity.ok(rolePermissionService.createRolePermission(newRolePermission));
    }
    //Tüm grup içinde grup adı ve açıklaması araması yapılıyor
    @GetMapping("search_rolePermissions/{searchText}")
    public ResponseEntity<Iterable<RolePermission>> searchRolePermission(@PathVariable("searchText") String searchText){
        if(!Objects.equals(searchText, "")){
            return ResponseEntity.ok(rolePermissionService.getRolePermissionsByTitle(searchText,searchText));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //Belirlenen grup id göre grup bilgisi getiriliyor
    @PutMapping("rolePermission/{rolePermissionId}")
    public ResponseEntity<RolePermission> updateRolePermission(@PathVariable("rolePermissionId") Integer id, @RequestBody RolePermission rolePermission){
        RolePermission oldRolePermission = rolePermissionService.getRolePermissionById(id);
        if(oldRolePermission != null){
            return ResponseEntity.ok(rolePermissionService.updateRolePermission(oldRolePermission, rolePermission));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //Grup bilgisi silme işlemi grubu pasif hale getirilerek yapılmaktadır.
    @DeleteMapping("rolePermission/{rolePermissionId}")
    public ResponseEntity<RolePermission> deleteRolePermission(@PathVariable("rolePermissionId") Integer id){
        RolePermission oldRolePermission = rolePermissionService.getRolePermissionById(id);
        if(oldRolePermission != null){
            return ResponseEntity.ok(rolePermissionService.deleteRolePermission(oldRolePermission));
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
