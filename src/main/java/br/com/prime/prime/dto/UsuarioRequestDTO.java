package br.com.prime.prime.dto;
import br.com.prime.prime.models.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequestDTO {

    private String email;
    private String cnpj;
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