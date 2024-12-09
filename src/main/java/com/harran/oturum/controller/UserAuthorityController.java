package com.harran.oturum.controller;

import com.harran.oturum.model.authority.Permission;
import com.harran.oturum.service.oauth.UserAuthority;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAuthorityController {

    @GetMapping("/")
    public String hello() {
        return "Hello World";
    }
    @GetMapping("hello")
    public String greet(HttpServletRequest request) {
        return "Hello World "+request.getSession().getId();
    }

    @GetMapping("about")
    public String about(HttpServletRequest request) {
        return "Telusko "+request.getSession().getId();
    }
    @GetMapping("csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }
    @GetMapping("userPermission/{username}/{applicationName}/{pageUrl}")
    public Iterable<Permission> getUserPermissions(@PathVariable("username") String username, @PathVariable("applicationName") String applicationName, @PathVariable("pageUrl") String pageUrl ) {
        UserAuthority userAuthority = new UserAuthority();
        return userAuthority.getUserPermissions(username,applicationName,pageUrl);
    }
}
