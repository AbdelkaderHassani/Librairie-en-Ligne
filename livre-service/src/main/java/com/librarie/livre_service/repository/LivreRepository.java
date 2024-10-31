package com.librarie.livre_service.repository;

import com.librarie.livre_service.model.Livre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface LivreRepository extends JpaRepository<Livre, Long>{
}

