package com.harran.oturum.dao;

import com.harran.oturum.model.authority.Application;
import com.harran.oturum.model.authority.Group;
import com.harran.oturum.model.authority.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Repository
public interface ApplicationRepo extends CrudRepository<Application, Long> {
    List<Application> findByActiveTrue(); // isActive alanı true olanları getirir
    Application findByIdAndActiveTrue(long id);
    List<Application> findByNameAndActiveTrue(String name);
    List<Application> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndActiveTrue(String name, String description); // İsme göre arama yapar
}
