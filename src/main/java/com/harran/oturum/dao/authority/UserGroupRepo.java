package com.harran.oturum.dao.authority;

import com.harran.oturum.model.authority.UserGroup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGroupRepo extends CrudRepository<UserGroup, Long> {
    List<UserGroup> findByActiveTrue(); // isActive alanı true olanları getirir
    UserGroup findByIdAndActiveTrue(long id);
    List<UserGroup> findByUserIdAndActiveTrue(long userId);
    List<UserGroup> findByTitleAndActiveTrue(String name);
    List<UserGroup> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndActiveTrue(String name, String description); // İsme göre arama yapar
}
