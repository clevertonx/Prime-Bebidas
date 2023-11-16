package br.com.prime.prime.dto;

public record RegistrationRequest(
        String email,
        String senha
) {
}
