package com.harran.oturum.dao.authority;

import com.harran.oturum.model.authority.Group;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepo extends CrudRepository<Group, Long> {
    List<Group> findByActiveTrue(); // isActive alanı true olanları getirir
    Group findByIdAndActiveTrue(long id);
    List<Group> findByNameAndActiveTrue(String name);
    List<Group> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndActiveTrue(String name, String description); // İsme göre arama yapar

}
