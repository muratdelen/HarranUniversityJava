package com.harran.oturum.dao;

import com.harran.oturum.model.Group;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public interface GroupRepo extends CrudRepository<Group, Long> {
    List<Group> findByActiveTrue(); // isActive alanı true olanları getirir
    List<Group> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndActiveTrue(String name, String description); // İsme göre arama yapar
    Group findByIdAndActiveTrue(long id);
}
