package com.librarie.commande_service.repository;

import com.librarie.commande_service.model.entities.Commande;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {
    @EntityGraph(attributePaths = {"livre"})
    Optional<Commande> findByNumero(Long numero);
}
