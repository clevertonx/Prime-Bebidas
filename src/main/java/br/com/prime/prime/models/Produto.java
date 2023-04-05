package br.com.prime.prime.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
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
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String imagem;

    @ManyToOne
    @JoinColumn(name = "estabelecimento_id")
    private Estabelecimento estabelecimento;

    public Produto(String nome, String descricao, String marca, double preço, Categoria categoria, String imagem)
            throws PreçoInvalidoException {

        verificarPreçoEntreZeroeDezMil(preço);
        this.nome = nome;
        this.descricao = descricao;
        this.preço = preço;
        this.categoria = categoria;
        this.marca = marca;
        this.imagem = imagem;
    }

    private void verificarPreçoEntreZeroeDezMil(double preço) throws PreçoInvalidoException {
        if (preço < 1 || preço > 10000) {
            throw new PreçoInvalidoException();
        }
    }

}
