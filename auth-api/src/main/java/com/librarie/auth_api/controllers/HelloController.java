package com.librarie.auth_api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {
    @Operation(
            summary = "Dire Bonjour",
            description = "Retourne un message de bienvenue à l'utilisateur.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Succès"),
                    @ApiResponse(responseCode = "500", description = "Erreur serveur")
            }
    )
    @GetMapping("/hello")
    public String sayHello() {
        return "Bonjour, Swagger!";
    }
}
