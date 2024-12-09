package com.harran.oturum.dao.authority;

import com.harran.oturum.model.authority.Application;
import com.harran.oturum.model.authority.PageUrl;
import com.harran.oturum.model.authority.Role;
import com.harran.oturum.model.authority.RolePermission;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissionRepo extends CrudRepository<RolePermission, Long> {
    List<RolePermission> findByActiveTrue(); // isActive alanı true olanları getirir
    RolePermission findByIdAndActiveTrue(long id);
    List<RolePermission> findByRoleAndPageUrlAndActiveTrue( Role role, PageUrl pageUrl);
    List<RolePermission> findByTitleAndActiveTrue(String name);
    List<RolePermission> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndActiveTrue(String name, String description); // İsme göre arama yapar
}
