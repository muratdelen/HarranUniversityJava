package com.harran.oturum.dao;

import com.harran.oturum.model.Group;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public interface GroupRepo extends CrudRepository<Group, Long> {
    List<Group> findByIsActiveTrue(); // isActive alanı true olanları getirir
    List<Group> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndIsActiveTrue(String name, String description); // İsme göre arama yapar
    Group findByIdAndIsActiveTrue(long id);
}
