package com.harran.oturum.service.oauth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import com.harran.oturum.model.authority.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private final CustomUserDetailsService customUserDetailsService;
    private final String secretKey;

    public JwtService(CustomUserDetailsService customUserDetailsService,
                      @Value("${security.jwt.secret}") String secretKey) {
        this.customUserDetailsService = customUserDetailsService;
        this.secretKey = secretKey;
    }

    public String generateToken() {
        User loggedUser = customUserDetailsService.logedUser;
        if (loggedUser == null) {
            throw new IllegalStateException("No authenticated user available for token generation");
        }
        return generateToken(loggedUser.getUsername());
    }
    public String generateToken(String username) {

        customUserDetailsService.loadUserByUsername(username);
        List<String> roles = sanitizeRoles(customUserDetailsService.roles);
        return buildToken(username, roles,
                Optional.ofNullable(customUserDetailsService.logedUser)
                        .map(User::getEmail)
                        .orElse(null));
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserName(String token) {
        // extract the username from jwt token
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build().parseClaimsJws(token).getBody();
    }


    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    public Map<String, Object> getClaimsFromToken(String token) {
        return new HashMap<>(extractAllClaims(token));
    }

    public String getUsernameFromToken(String token) {
        return extractAllClaims(token).getSubject();
    }

    public List<String> getRolesFromToken(String token) {
        Claims claims = extractAllClaims(token);
        Object rolesClaim = claims.get("roles");
        if (rolesClaim instanceof List<?>) {
            return ((List<?>) rolesClaim).stream()
                    .filter(String.class::isInstance)
                    .map(String.class::cast)
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    public Optional<String> getEmailFromToken(String token) {
        Object email = extractAllClaims(token).get("email");
        return email instanceof String ? Optional.of((String) email) : Optional.empty();
    }

    public boolean isTokenValid(String token) {
        try {
            extractAllClaims(token);
            return !isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private String buildToken(String username, List<String> roles, String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);
        if (email != null) {
            claims.put("email", email);
        }

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getKey(), SignatureAlgorithm.HS256).compact();
    }

    private List<String> sanitizeRoles(List<String> roles) {
        if (roles == null) {
            return List.of();
        }
        Set<String> uniqueRoles = new LinkedHashSet<>(roles);
        return List.copyOf(uniqueRoles);
    }
}
