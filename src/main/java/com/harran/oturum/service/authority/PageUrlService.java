package com.harran.oturum.service.authority;

import com.harran.oturum.dao.authority.PageUrlRepo;
import com.harran.oturum.model.authority.PageUrl;
import com.harran.oturum.service.oauth.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PageUrlService {
    @Autowired
    private PageUrlRepo pageUrlRepo;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    public Iterable<PageUrl> getAllPageUrls() {
        return pageUrlRepo.findByActiveTrue();
    }
    public PageUrl getPageUrlById(long id) {
        return pageUrlRepo.findByIdAndActiveTrue(id);
    }
    public Iterable<PageUrl> getPageUrlsByName(String name, String description) {
        return pageUrlRepo.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndActiveTrue(name, description);
    }
    public PageUrl createPageUrl(PageUrl pageUrl) {
        pageUrl.setCratedByUser(customUserDetailsService.logedUser);
        return pageUrlRepo.save(pageUrl);
    }
    public PageUrl updatePageUrl(PageUrl old_pageUrl, PageUrl new_pageUrl) {
        old_pageUrl.setUpdatedByUser(customUserDetailsService.logedUser);
        old_pageUrl.setName(new_pageUrl.getName());
        old_pageUrl.setDescription(new_pageUrl.getDescription());
        return pageUrlRepo.save(old_pageUrl);
    }
    public PageUrl deletePageUrl(PageUrl old_pageUrl) {
            old_pageUrl.setDeletedByUser(customUserDetailsService.logedUser);
            old_pageUrl.setActive(false);
            return pageUrlRepo.save(old_pageUrl);
    }

}
