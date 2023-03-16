package br.com.prime.prime.models;

import org.hibernate.validator.constraints.br.CNPJ;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonInclude(Include.NON_NULL)
    private Long id;
    @Email
    private String email = "clevertonx@gmail.com";
    @CNPJ
    private String cnpj = "67.596.818/0001-90";
    @NotBlank
    private String senha = "senha123";

    public Usuario(String email, String cnpj, String senha) {
        this.email = email;
        this.cnpj = cnpj;
        this.senha = senha;
    }

}
