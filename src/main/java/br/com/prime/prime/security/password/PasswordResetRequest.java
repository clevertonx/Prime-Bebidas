package br.com.prime.prime.security.password;

import lombok.Data;

@Data
public class
PasswordResetRequest {
    private String email;
    private String oldPassword;
    private String newPassword;
}
