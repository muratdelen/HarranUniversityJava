package com.harran.oturum.dao.authority;

import com.harran.oturum.model.authority.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepo extends CrudRepository<UserRole, Long> {
    List<UserRole> findByActiveTrue(); // isActive alanı true olanları getirir
    UserRole findByIdAndActiveTrue(long id);
    List<UserRole> findByApplicationAndUserIdAndActiveTrue(long applicationId, long userId);
    List<UserRole> findByTitleAndActiveTrue(String name);
    List<UserRole> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndActiveTrue(String name, String description); // İsme göre arama yapar
}
