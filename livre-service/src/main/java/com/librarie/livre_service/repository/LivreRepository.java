package com.librarie.livre_service.repository;

import com.librarie.livre_service.model.entities.Livre;
import com.librarie.livre_service.model.entities.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivreRepository extends JpaRepository<Livre, Long> {
    List<Livre> findByCategorie(Categorie categorie);
}
