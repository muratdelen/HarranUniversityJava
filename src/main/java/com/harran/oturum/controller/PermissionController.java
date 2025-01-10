package com.harran.oturum.controller;

import com.harran.oturum.model.authority.Permission;
import com.harran.oturum.service.authority.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


@RestController
public class PermissionController
{
    @Autowired
    private PermissionService permissionService;//Permission servise buraya atamasını yap

    //Aktif tüm grupları get ile getir.
    @GetMapping("Permissions")
    public ResponseEntity<Iterable<Permission>> getAllPermissions(){
        return ResponseEntity.ok(permissionService.getAllPermissions());
    }
    //verilen grup id göre grup bilgilerini getir.
    @GetMapping("Permission/{PermissionId}")
    public ResponseEntity<Permission> getPermissionById(@PathVariable("PermissionId") Integer id){
        Permission oldPermission = permissionService.getPermissionById(id);
        if(oldPermission != null){
            return ResponseEntity.ok(oldPermission);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //Yeni bir grup eklemek için bilgileri yollanır.
    @PostMapping("Permission")
    public ResponseEntity<Permission> createPermission(@RequestBody Permission Permission){
        //Permission newPermission = new Permission();
       // newPermission.setName(Permission.getName());
       // newPermission.setDescription(Permission.getDescription());
        return ResponseEntity.ok(permissionService.createPermission(Permission));
    }
    //Tüm grup içinde grup adı ve açıklaması araması yapılıyor
    @GetMapping("search_Permissions/{searchText}")
    public ResponseEntity<Iterable<Permission>> searchPermission(@PathVariable("searchText") String searchText){
        if(!Objects.equals(searchText, "")){
            return ResponseEntity.ok(permissionService.getPermissionsByName(searchText,searchText));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //Belirlenen grup id göre grup bilgisi getiriliyor
    @PutMapping("Permission/{PermissionId}")
    public ResponseEntity<Permission> updatePermission(@PathVariable("PermissionId") Integer id, @RequestBody Permission Permission){
        Permission oldPermission = permissionService.getPermissionById(id);
        if(oldPermission != null){
            return ResponseEntity.ok(permissionService.updatePermission(oldPermission, Permission));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //Grup bilgisi silme işlemi grubu pasif hale getirilerek yapılmaktadır.
    @DeleteMapping("Permission/{PermissionId}")
    public ResponseEntity<Permission> deletePermission(@PathVariable("PermissionId") Integer id){
        Permission oldPermission = permissionService.getPermissionById(id);
        if(oldPermission != null){
            return ResponseEntity.ok(permissionService.deletePermission(oldPermission));
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
