package com.librarie.auth_api.configs;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Documentation API de mon projet")
                        .version("1.0")
                        .description("Cette API permet de gérer les fonctionnalités de mon application.")
                        .contact(new Contact()
                                .name("hassani abdelkader")
                                .email("hassaniabdelkaderhh@gmail.com")
                                .url("http://localhost:8005/swagger-ui"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));
    }
}
