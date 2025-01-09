package com.librarie.commande_service.service;

import com.librarie.commande_service.model.entities.Commande;
import com.librarie.commande_service.model.entities.dto.LivreDto;
import com.librarie.commande_service.repository.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Service
public class CommandeService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CommandeRepository commandeRepository;

    // Méthode pour récupérer les informations d'un livre via le microservice livre-service
    public LivreDto getLivreDetails(Long livreId) {
        String url = "http://localhost:8088/api/livres/" + livreId;  // L'URL du microservice Livre

        ResponseEntity<LivreDto> response = restTemplate.getForEntity(url, LivreDto.class);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody();  // Retourne les détails du livre sous forme de DTO
        } else {
            throw new IllegalArgumentException("Livre introuvable pour l'ID : " + livreId);
        }
    }

    // Méthode pour créer une commande
    public Commande createCommande(Long livreId, int quantity) {
        LivreDto livre = getLivreDetails(livreId);  // Appel à l'API du microservice Livre

        if (livre == null) {
            throw new IllegalArgumentException("Livre ID " + livreId + " non trouvé.");
        }

        // Calcul du prix total
        double totalPrice = livre.getPrix() * quantity;

        // Créer une nouvelle commande
        Commande commande = new Commande();
        commande.setLivre(livreId); // Enregistrer l'ID du livre dans la commande
        commande.setQuantity(quantity);
        commande.setTotalPrice(totalPrice);
        commande.setDate(new Date());
        commande.setEtat("EN COURS");

        // Sauvegarder la commande dans le repository
        return commandeRepository.save(commande);
    }
}
