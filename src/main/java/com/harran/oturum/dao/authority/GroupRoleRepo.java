package com.harran.oturum.dao.authority;

import com.harran.oturum.model.authority.GroupRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRoleRepo extends CrudRepository<GroupRole, Long> {
    List<GroupRole> findByActiveTrue(); // isActive alanı true olanları getirir
    GroupRole findByIdAndActiveTrue(long id);
    List<GroupRole> findByGroupIdAndActiveTrue(long userId);
    List<GroupRole> findByTitleAndActiveTrue(String name);
    List<GroupRole> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndActiveTrue(String name, String description); // İsme göre arama yapar
}
