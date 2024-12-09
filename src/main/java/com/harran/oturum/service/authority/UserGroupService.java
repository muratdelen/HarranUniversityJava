package com.harran.oturum.service.authority;

import com.harran.oturum.dao.authority.UserGroupRepo;
import com.harran.oturum.model.authority.UserGroup;
import com.harran.oturum.service.oauth.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserGroupService {
    @Autowired
    private UserGroupRepo userGroupRepo;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    public Iterable<UserGroup> getAllUserGroups() {
        return userGroupRepo.findByActiveTrue();
    }
    public UserGroup getUserGroupById(long id) {
        return userGroupRepo.findByIdAndActiveTrue(id);
    }
    public Iterable<UserGroup> getUserGroupsByTitle(String title, String description) {
        return userGroupRepo.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndActiveTrue(title, description);
    }
    public UserGroup createUserGroup(UserGroup userGroup) {
        userGroup.setCratedByUser(customUserDetailsService.logedUser);
        return userGroupRepo.save(userGroup);
    }
    public UserGroup updateUserGroup(UserGroup old_userGroup, UserGroup new_userGroup) {
        old_userGroup.setUpdatedByUser(customUserDetailsService.logedUser);
        old_userGroup.setTitle(new_userGroup.getTitle());
        old_userGroup.setDescription(new_userGroup.getDescription());
        return userGroupRepo.save(old_userGroup);
    }
    public UserGroup deleteUserGroup(UserGroup old_userGroup) {
            old_userGroup.setDeletedByUser(customUserDetailsService.logedUser);
            old_userGroup.setActive(false);
            return userGroupRepo.save(old_userGroup);
    }

}
