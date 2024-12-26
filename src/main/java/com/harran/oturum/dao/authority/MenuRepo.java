package com.harran.oturum.dao.authority;

import com.harran.oturum.model.authority.Menu;
import com.harran.oturum.model.authority.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepo extends CrudRepository<Menu, Long> {
    List<Menu> findByActiveTrue(); // isActive alanı true olanları getirir
    Menu findByIdAndActiveTrue(long id);
    Menu findByApplicationIdAndActiveTrue(long applicationId);
}
