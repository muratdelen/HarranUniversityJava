package com.harran.oturum.controller;

import com.harran.oturum.service.oauth.JwtService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Map;
import java.util.HashMap;


@RestController
@RequestMapping("/api/token")
public class TokenController {

    @GetMapping("/validate")
    public ResponseEntity<Map<String, Object>> validateBearerToken(
            @RequestHeader(value = "Authorization") String authorizationHeader) {

        try {
            // Authorization header'dan Bearer token çıkar
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(401).body(Map.of("error", "Authorization header must contain Bearer token"));
            }

            String token = authorizationHeader.substring(7); // "Bearer " kısmını çıkar
            JwtService jwtService = new JwtService();
            // Response için bir map oluştur
            Map<String, Object> response = new HashMap<>();
            response.put("username", jwtService.getRolesFromToken(token)); // Kullanıcı adı (sub)
            response.put("roles", jwtService.getRolesFromToken(token)); // Roller

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Geçersiz token durumu
            return ResponseEntity.status(401).body(Map.of("error", "Invalid or expired token: " + e.getMessage()));
        }
    }

}
