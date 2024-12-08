package com.harran.oturum.dao;

import com.harran.oturum.model.authority.Permission;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepo extends CrudRepository<Permission, Long> {
    List<Permission> findByActiveTrue(); // isActive alanı true olanları getirir
    Permission findByIdAndActiveTrue(long id);
    List<Permission> findByNameAndActiveTrue(String name);
    List<Permission> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndActiveTrue(String name, String description); // İsme göre arama yapar

}
