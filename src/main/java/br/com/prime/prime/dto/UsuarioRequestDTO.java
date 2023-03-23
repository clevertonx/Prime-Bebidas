package br.com.prime.prime.dto;

import org.hibernate.validator.constraints.br.CNPJ;

import br.com.prime.prime.models.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequestDTO {

    @Email(message = "Campo inválido")
    @NotBlank(message = "Email não informado")
    private String email;
    @CNPJ(message = "Campo inválido")
    @NotBlank(message = "CNPJ não informado")
    private String cnpj;
    @NotBlank(message = "Senha não informada")
    private String senha;

    public UsuarioRequestDTO(Usuario usuario) {
        this.email = usuario.getEmail();
        this.cnpj = usuario.getCnpj();
        this.senha = usuario.getSenha();
    }

    public Usuario toUsuario() {
        return new Usuario()
                .setEmail(this.email)
                .setCnpj(this.cnpj)
                .setSenha(this.senha);
    }
}