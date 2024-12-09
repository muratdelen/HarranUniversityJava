package com.harran.oturum.dao.authority;

import com.harran.oturum.model.authority.UserPermission;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPermissionRepo extends CrudRepository<UserPermission, Long> {
    List<UserPermission> findByActiveTrue(); // isActive alanı true olanları getirir
    UserPermission findByIdAndActiveTrue(long id);
    List<UserPermission> findByUserIdAndAddAndActiveTrue(long userId, boolean add);
    List<UserPermission> findByUserIdAndActiveTrue(long userId);
    List<UserPermission> findByTitleAndActiveTrue(String name);
    List<UserPermission> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndActiveTrue(String name, String description); // İsme göre arama yapar
    Iterable<UserPermission> findByApplicationIdAndUserIdAndAddAndActiveTrue(long applicationId, long userId, boolean add);
}
