package com.librarie.auth_api.services;

import com.librarie.auth_api.dtos.AssignRoleDto;
import com.librarie.auth_api.dtos.UserDto;
import com.librarie.auth_api.entities.EnumRole;
import com.librarie.auth_api.entities.User;
import com.librarie.auth_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final UserRepository userRepository;

    @Autowired
    public AdminService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Récupérer tous les utilisateurs
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        // Convertir les entités User en UserDto si nécessaire
        return users.stream()
                .map(user -> new UserDto(user.getId(), user.getNom(), user.getPrenom(), user.getEmail()))
                .collect(Collectors.toList());
    }

    // Activer un utilisateur
    public void activateUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        user.setActive(true);
        userRepository.save(user);
    }

    // Désactiver un utilisateur
    public void deactivateUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        user.setActive(false);
        userRepository.save(user);
    }

    // Assigner un rôle à un utilisateur
    public void assignRole(AssignRoleDto assignRoleDto) {
        User user = userRepository.findById(assignRoleDto.getUserId())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        EnumRole role = EnumRole.valueOf(assignRoleDto.getRoleName());
        user.getRoles().add(role);
        userRepository.save(user);
    }

    // Retirer un rôle d'un utilisateur
    public void removeRole(AssignRoleDto assignRoleDto) {
        User user = userRepository.findById(assignRoleDto.getUserId())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        EnumRole role = EnumRole.valueOf(assignRoleDto.getRoleName());
        user.getRoles().remove(role);
        userRepository.save(user);
    }
}
