package com.librarie.auth_api.dtos;


import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private Long expiresIn;
    private String errorMessage;

}
