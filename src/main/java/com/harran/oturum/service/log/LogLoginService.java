package com.harran.oturum.service.log;

import com.harran.oturum.dao.log.LogLoginRepo;
import com.harran.oturum.model.log.LogLogin;
import com.harran.oturum.service.oauth.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogLoginService {
    @Autowired
    private LogLoginRepo logLoginRepo;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    public Iterable<LogLogin> getAllLogLogins() {
        return logLoginRepo.findByActiveTrue();
    }
    public LogLogin getLogLoginById(long id) {
        return logLoginRepo.findByIdAndActiveTrue(id);
    }
    public LogLogin createLogLogin(LogLogin LogLogin) {
        LogLogin.setCratedByUser(customUserDetailsService.logedUser);
        return logLoginRepo.save(LogLogin);
    }
    public LogLogin updateLogLogin(LogLogin old_LogLogin, LogLogin new_LogLogin) {
        old_LogLogin.setUpdatedByUser(customUserDetailsService.logedUser);
        return logLoginRepo.save(old_LogLogin);
    }
    public LogLogin deleteLogLogin(LogLogin old_LogLogin) {
            old_LogLogin.setDeletedByUser(customUserDetailsService.logedUser);
            old_LogLogin.setActive(false);
            return logLoginRepo.save(old_LogLogin);
    }

}
