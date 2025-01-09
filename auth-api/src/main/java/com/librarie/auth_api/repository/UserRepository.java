package com.librarie.auth_api.repository;

import com.librarie.auth_api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Méthodes personnalisées pour rechercher un utilisateur avec un token de confirmation ou de réinitialisation
    Optional<User> findByEmail(String email);
    Optional<User> findByConfirmationToken(String confirmationToken);
    Optional<User> findByResetToken(String resetToken);
    Optional<User> findById(Long id);
}
