package br.com.prime.prime.models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@Entity(name = "produto")
@EqualsAndHashCode(of = "id")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
        @Cascade(CascadeType.PERSIST)
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
