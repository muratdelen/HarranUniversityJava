package com.harran.oturum.dao.authority;

import com.harran.oturum.model.authority.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepo extends CrudRepository<Role, Long> {
    List<Role> findByActiveTrue(); // isActive alanı true olanları getirir
    Role findByIdAndActiveTrue(long id);
    List<Role> findByNameAndActiveTrue(String name);
    List<Role> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndActiveTrue(String name, String description); // İsme göre arama yapar
}
