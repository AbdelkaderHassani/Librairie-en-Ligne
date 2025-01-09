package com.librarie.auth_api.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "mot_de_passe", nullable = false)
    private String motDePasse;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private boolean emailVerified = false;

    @Column(name = "confirmation_token", nullable = false)
    private String confirmationToken;


    @Column(name = "reset_token", nullable = true)
    private String resetToken;

    @Column(name = "reset_token_expiration", nullable = true)
    private Date resetTokenExpiration;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<EnumRole> roles; // Les rôles de l'utilisateur

    @Column(nullable = false)
    private boolean active = true;  // Ajout du champ "active"

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Pour chaque rôle, récupérer ses permissions
        return roles.stream()
                .flatMap(role -> role.getPermissions().stream())  // Récupère les permissions liées à chaque rôle
                .map(permission -> (GrantedAuthority) () -> permission)  // Utilise directement la permission comme autorité
                .toList();
    }

    @CreationTimestamp
    @Column(updatable = false, name = "created_at", nullable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return motDePasse;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;  // Utilisation de la variable "active" pour déterminer si l'utilisateur est activé
    }

    // Méthode pour activer/désactiver l'utilisateur
    public void setActive(boolean active) {
        this.active = active;
    }
    @PrePersist
    public void generateConfirmationToken() {
        if (this.confirmationToken == null) {
            this.confirmationToken = UUID.randomUUID().toString();
        }
    }
}
