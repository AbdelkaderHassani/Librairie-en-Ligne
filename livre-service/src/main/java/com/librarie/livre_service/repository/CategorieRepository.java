package com.librarie.livre_service.repository;

import com.librarie.livre_service.model.entities.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorieRepository extends JpaRepository <Categorie, Long> {
}
