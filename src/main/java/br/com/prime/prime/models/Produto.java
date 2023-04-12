package br.com.prime.prime.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    private Double preco;
    
    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String imagem;

    @ManyToOne
    @JoinColumn(name = "estabelecimento_id")
    private Estabelecimento estabelecimento;

    public Produto(String nome, String descricao, String marca, double preco, Categoria categoria, String imagem, Estabelecimento estabelecimento)
            throws PrecoInvalidoException {

        verificarPreçoEntreZeroeDezMil(preco);
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.categoria = categoria;
        this.marca = marca;
        this.imagem = imagem;
        this.estabelecimento = estabelecimento;
    }

    private void verificarPreçoEntreZeroeDezMil(double preco) throws PrecoInvalidoException {
        if (preco < 1 || preco > 10000) {
            throw new PrecoInvalidoException();
        }
    }

}
