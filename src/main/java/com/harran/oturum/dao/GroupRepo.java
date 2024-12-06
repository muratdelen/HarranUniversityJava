package com.harran.oturum.dao;

import com.harran.oturum.model.Group;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface GroupRepo extends CrudRepository<Group, Long> {
    Group findById(Integer id);
}
