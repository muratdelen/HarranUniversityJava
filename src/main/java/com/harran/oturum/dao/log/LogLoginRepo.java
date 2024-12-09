package com.harran.oturum.dao.log;

import com.harran.oturum.model.log.LogLogin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogLoginRepo extends CrudRepository<LogLogin, Long> {
    List<LogLogin> findByActiveTrue(); // isActive alanı true olanları getirir
    LogLogin findByIdAndActiveTrue(long id);
}
