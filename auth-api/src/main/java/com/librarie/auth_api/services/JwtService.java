package com.librarie.auth_api.services;

import com.librarie.auth_api.entities.EnumRole;
import com.librarie.auth_api.entities.User;
import com.librarie.auth_api.repository.RoleRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class JwtService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    private final RoleRepository roleRepository;

    public JwtService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    // Méthode qui génère un token à partir d'un utilisateur personnalisé (User)
    public String generateToken(User user) {
        Set<EnumRole> roles = user.getRoles();

        // Convertir User en UserDetails
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.name()))
                        .collect(Collectors.toList())
        );

        return generateToken(userDetails, roles);
    }

    // Générer un token à partir d'un UserDetails et des rôles
    public String generateToken(UserDetails userDetails, Set<EnumRole> roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);

        // Ajouter les permissions des rôles dans les claims
        Set<String> permissions = new HashSet<>();
        for (EnumRole role : roles) {
            permissions.add(role.name()); // Ajoutez ici des permissions spécifiques si nécessaire
        }

        claims.put("permissions", permissions);

        return buildToken(claims, userDetails, jwtExpiration);
    }

    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Récupérer le temps d'expiration du token
    public long getExpirationTime() {
        return jwtExpiration;
    }

    // Extraire toutes les claims du token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Extraire l'username (email) du token
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Vérifier si le token est valide
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Vérifier si le token est expiré
    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
}
