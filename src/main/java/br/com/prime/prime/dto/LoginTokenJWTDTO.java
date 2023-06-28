package br.com.prime.prime.dto;

import lombok.Data;

@Data
public class LoginTokenJWTDTO {
    private String tokenJWT;

    public LoginTokenJWTDTO(String tokenJWT) {
        this.tokenJWT = tokenJWT;
    }

}