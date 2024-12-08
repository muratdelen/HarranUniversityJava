package com.harran.oturum.dao;

import com.harran.oturum.model.authority.Group;
import com.harran.oturum.model.authority.PageUrl;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PageUrlRepo extends CrudRepository<PageUrl, Long> {
    List<PageUrl> findByActiveTrue(); // isActive alanı true olanları getirir
    PageUrl findByIdAndActiveTrue(long id);
    List<PageUrl> findByNameAndActiveTrue(String name);
    List<PageUrl> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndActiveTrue(String name, String description); // İsme göre arama yapar
}
