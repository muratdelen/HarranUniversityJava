package com.harran.oturum.dao;

import com.harran.oturum.model.authority.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
    User findByUsername(String username);
    List<User> findByActiveTrue(); // isActive alanı true olanları getirir
    User findByIdAndActiveTrue(long id);
    List<User> findByUsernameAndActiveTrue(String name);
    List<User> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseAndActiveTrue(String name, String description); // İsme göre arama yapar
}
