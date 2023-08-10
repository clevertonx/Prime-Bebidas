package br.com.prime.prime.dto;

import br.com.prime.prime.dominio.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioPutDTO {
    
    private Long id;

    @Email(message = "Campo inválido")
    @NotBlank(message = "Email não informado")
    private String email;

    @NotBlank(message = "Senha não informada")
    private String senha;

    public UsuarioPutDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.email = usuario.getEmail();
        this.senha = usuario.getSenha();
    }
}
