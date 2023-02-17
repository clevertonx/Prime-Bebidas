package br.com.prime.prime.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    private String descricao;
    private String marca;
    private Double preço;
    private Categoria categoria;

    public Produto(String nome, String descricao, String marca, double preço, Categoria categoria)
            throws PreçoInvalidoException {

        verificarPreçoEntreZeroeDezMil(preço);
        this.nome = nome;
        this.descricao = descricao;
        this.preço = preço;
        this.categoria = categoria;
        this.marca = marca;
    }

    private void verificarPreçoEntreZeroeDezMil(double preço) throws PreçoInvalidoException {
        if (preço < 1 || preço > 10000) {
            throw new PreçoInvalidoException();
        }
    }

}
