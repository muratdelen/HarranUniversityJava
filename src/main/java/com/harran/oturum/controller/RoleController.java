package com.harran.oturum.controller;

import com.harran.oturum.model.authority.Role;
import com.harran.oturum.service.authority.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

//@PreAuthorize("hasRole('ADMIN')")  // Sadece ADMIN rolüne sahip kullanıcılar erişebilir
@RestController
public class RoleController
{
    @Autowired
    private RoleService roleService;//role servise buraya atamasını yap

    //Aktif tüm grupları get ile getir.
    @GetMapping("roles")
    public ResponseEntity<Iterable<Role>> getAllRoles(){
        return ResponseEntity.ok(roleService.getAllRoles());
    }
    //verilen grup id göre grup bilgilerini getir.
    @GetMapping("role/{roleId}")
    public ResponseEntity<Role> getRoleById(@PathVariable("roleId") Integer id){
        Role oldRole = roleService.getRoleById(id);
        if(oldRole != null){
            return ResponseEntity.ok(oldRole);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //Yeni bir grup eklemek için bilgileri yollanır.
    @PostMapping("role")
    public ResponseEntity<Role> createRole(@RequestBody Role role){
       // Role newRole = new Role();
        //newRole.setName(role.getName());
        //newRole.setDescription(role.getDescription());
        return ResponseEntity.ok(roleService.createRole(role));
    }
    //Tüm grup içinde grup adı ve açıklaması araması yapılıyor
    @GetMapping("search_roles/{searchText}")
    public ResponseEntity<Iterable<Role>> searchRole(@PathVariable("searchText") String searchText){
        if(!Objects.equals(searchText, "")){
            return ResponseEntity.ok(roleService.getRolesByName(searchText,searchText));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //Belirlenen grup id göre grup bilgisi getiriliyor
    @PutMapping("role/{roleId}")
    public ResponseEntity<Role> updateRole(@PathVariable("roleId") Integer id, @RequestBody Role role){
        Role oldRole = roleService.getRoleById(id);
        if(oldRole != null){
            return ResponseEntity.ok(roleService.updateRole(oldRole, role));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //Grup bilgisi silme işlemi grubu pasif hale getirilerek yapılmaktadır.
    @DeleteMapping("role/{roleId}")
    public ResponseEntity<Role> deleteRole(@PathVariable("roleId") Integer id){
        Role oldRole = roleService.getRoleById(id);
        if(oldRole != null){
            return ResponseEntity.ok(roleService.deleteRole(oldRole));
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
