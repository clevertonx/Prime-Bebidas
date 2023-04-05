package br.com.prime.prime.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Entity
@Data
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonInclude(Include.NON_NULL)
    private Long id;
    @Column(unique = true)
    private String email = "clevertonx@gmail.com";
    private String senha = "senha123";
    
    @OneToMany(mappedBy = "usuario")
    private List<Estabelecimento> estabelecimentos;

    public Usuario(String email, String senha, List<Estabelecimento> estabelecimentos) {
        this.email = email;
        this.senha = senha;
    }

}
