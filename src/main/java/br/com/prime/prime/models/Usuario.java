package br.com.prime.prime.models;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor

public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email = "clevertonx@gmail.com";
    private String cnpj = "67.596.818/0001-90";
    private String senha = "senha123";

    public Usuario(String email, String cnpj, String senha) {
        this.email = email;
        this.cnpj = cnpj;
        this.senha = senha;
    }


}
