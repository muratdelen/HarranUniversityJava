package com.harran.oturum.service.authority;

import com.harran.oturum.dao.authority.ApplicationRepo;
import com.harran.oturum.model.authority.Application;
import com.harran.oturum.service.oauth.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {
    @Autowired
    private ApplicationRepo applicationRepo;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    public Iterable<Application> getAllApplications() {
        return applicationRepo.findByActiveTrue();
    }
    public Application getApplicationById(long id) {
        return applicationRepo.findByIdAndActiveTrue(id);
    }
    public Iterable<Application> getApplicationsByName(String name, String description) {
        return applicationRepo.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndActiveTrue(name, description);
    }
    public Application createApplication(Application application) {
        application.setCratedByUser(customUserDetailsService.logedUser);
        return applicationRepo.save(application);
    }
    public Application updateApplication(Application old_application, Application new_application) {
        old_application.setUpdatedByUser(customUserDetailsService.logedUser);
        old_application.setName(new_application.getName());
        old_application.setDescription(new_application.getDescription());
        return applicationRepo.save(old_application);
    }
    public Application deleteApplication(Application old_application) {
            old_application.setDeletedByUser(customUserDetailsService.logedUser);
            old_application.setActive(false);
            return applicationRepo.save(old_application);
    }

}
