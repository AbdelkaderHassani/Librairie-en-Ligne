package com.librarie.auth_api.services;

import com.librarie.auth_api.dtos.UserDto;
import com.librarie.auth_api.entities.User;
import com.librarie.auth_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ManagementService {

    private final UserRepository userRepository;

    @Autowired
    public ManagementService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Récupérer un utilisateur par ID
    public UserDto getUserById(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return new UserDto(user.getId(), user.getNom(), user.getPrenom(), user.getEmail());
        } else {
            throw new RuntimeException("Utilisateur non trouvé");
        }
    }

    // Mettre à jour les informations d'un utilisateur
    public void updateUser(Long userId, UserDto userDto) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setNom(userDto.getNom());
            user.setPrenom(userDto.getPrenom());
            user.setEmail(userDto.getEmail());
            // Vous pouvez ajouter d'autres champs à mettre à jour ici
            userRepository.save(user);
        } else {
            throw new RuntimeException("Utilisateur non trouvé");
        }
    }

    // Supprimer un utilisateur
    public void deleteUser(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            userRepository.deleteById(userId);
        } else {
            throw new RuntimeException("Utilisateur non trouvé");
        }
    }

    // Récupérer tous les utilisateurs
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserDto(user.getId(), user.getNom(), user.getPrenom(), user.getEmail()))
                .collect(Collectors.toList());
    }
}
