package br.com.prime.prime.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Entity
@Setter
@Getter
@NoArgsConstructor
public class Estabelecimento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    private String nome;
    @NotBlank
    private String telefone;
    @NotBlank
    private String horarioAtendimento;
    @NotNull
    private int numero;
    @NotBlank
    private String cidade;
    @NotBlank
    private String logradouro;
    @NotBlank
    private String estado;

    public Estabelecimento(String nome, String telefone, String horarioAtendimento, int numero, String cidade,
            String logradouro, String estado) {

        this.nome = nome;
        this.telefone = telefone;
        this.horarioAtendimento = horarioAtendimento;
        this.numero = numero;
        this.cidade = cidade;
        this.logradouro = logradouro;
        this.estado = estado;
    }

}
