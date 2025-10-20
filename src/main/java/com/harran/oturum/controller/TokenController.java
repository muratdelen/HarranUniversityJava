package com.harran.oturum.controller;

import com.harran.oturum.service.oauth.CustomUserDetailsService;
import com.harran.oturum.service.oauth.JwtService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/token")
public class TokenController {

    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    public TokenController(JwtService jwtService, CustomUserDetailsService customUserDetailsService) {
        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @GetMapping("/validate")
    public ResponseEntity<Map<String, Object>> validateBearerToken(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorizationHeader) {

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body(Map.of("error", "Authorization header must contain Bearer token"));
        }

        String token = authorizationHeader.substring(7);
        if (!jwtService.isTokenValid(token)) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid or expired token"));
        }

        String username = jwtService.getUsernameFromToken(token);
        try {
            customUserDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(401).body(Map.of("error", "User not found: " + username));
        }

        Map<String, Object> response = new HashMap<>();
        response.put("username", username);
        List<String> roles = jwtService.getRolesFromToken(token);
        response.put("roles", roles);
        jwtService.getEmailFromToken(token).ifPresent(email -> response.put("email", email));
        response.put("claims", jwtService.getClaimsFromToken(token));

        return ResponseEntity.ok(response);
    }
}
