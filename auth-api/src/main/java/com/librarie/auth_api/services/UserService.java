package com.librarie.auth_api.services;

import com.librarie.auth_api.dtos.UpdateUserDto;
import com.librarie.auth_api.entities.EnumPermission;
import com.librarie.auth_api.entities.EnumRole;
import com.librarie.auth_api.entities.Role;
import com.librarie.auth_api.entities.User;
import com.librarie.auth_api.repository.RoleRepository;
import com.librarie.auth_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    // Récupérer tous les utilisateurs
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    // Trouver un utilisateur par ID
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    // Mettre à jour un utilisateur à partir d'un DTO
    public User updateUserFromDto(UpdateUserDto dto, User user) {
        user.setNom(dto.getNom());
        user.setPrenom(dto.getPrenom());
        user.setEmail(dto.getEmail());
        user.setRoles(dto.getRoles()); // Mise à jour des rôles
        return userRepository.save(user);
    }

    // Ajouter un rôle à un utilisateur
    public User addRoleToUser(User user, EnumRole role) {
        // Récupérer le rôle à partir de la base de données en fonction de l'EnumRole
        Role roleEntity = roleRepository.findByName(role.toString())
                .orElseThrow(() -> new IllegalArgumentException("Rôle non trouvé : " + role));

        // Ajoutez l'EnumRole à la liste des rôles de l'utilisateur
        user.getRoles().add(roleEntity.getName()); // Ajouter EnumRole à la liste des rôles
        return userRepository.save(user);
    }

    // Retirer un rôle d'un utilisateur
    public User removeRoleFromUser(User user, EnumRole role) {
        // Récupérer le rôle à partir de la base de données en fonction de l'EnumRole
        Role roleEntity = roleRepository.findByName(role.toString())
                .orElseThrow(() -> new IllegalArgumentException("Rôle non trouvé : " + role));

        // Retirer l'EnumRole de la liste des rôles de l'utilisateur
        user.getRoles().remove(roleEntity.getName()); // Retirer EnumRole de la liste des rôles
        return userRepository.save(user);
    }

    // Vérifie si un utilisateur a une permission spécifique
    public boolean hasPermission(User user, EnumPermission permission) {
        // Vérifie si l'un des rôles de l'utilisateur possède la permission
        for (EnumRole enumRole : user.getRoles()) {
            // Récupérer le rôle à partir de la base de données
            Role role = roleRepository.findByName(enumRole.toString())
                    .orElseThrow(() -> new IllegalArgumentException("Rôle non trouvé : " + enumRole));

            // Vérifier si l'une des permissions du rôle correspond à la permission recherchée
            if (role.getPermissions().stream().anyMatch(p -> p.getName().equals(permission))) {
                return true;
            }
        }
        return false;
    }

}
