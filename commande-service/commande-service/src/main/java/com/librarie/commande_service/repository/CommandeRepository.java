package com.librarie.commande_service.repository;

import com.librarie.commande_service.model.Commande;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeRepository extends JpaRepository<Commande, Long> {
}
