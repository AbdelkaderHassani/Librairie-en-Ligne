package com.librarie.auth_api.repository;

import com.librarie.auth_api.entities.EnumRole;
import com.librarie.auth_api.entities.Role;
import com.librarie.auth_api.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);  // Recherche d'un rôle par son nom (EnumRole)

}
