package br.com.prime.prime.Builders;

import br.com.prime.prime.models.Categoria;
import br.com.prime.prime.models.PreçoInvalidoException;
import br.com.prime.prime.models.Produto;

public class ProdutoBuilder {

    private String nome = "Vodka";
    private String descricao = "Nossa premiada vodka tem um sabor robusto com acabamento seco para uma maior suavidade";
    private String marca = "Absolut";
    private Double preço = 89.90;
    private Categoria categoria = Categoria.Destilada;
    private String imagem = Imagem.getBytes();
    private Long idEstabelecimento;

    public ProdutoBuilder()
            throws PreçoInvalidoException {

    }

    public Produto construir()
            throws PreçoInvalidoException {
        return new Produto(nome, descricao, marca, preço, categoria, imagem, idEstabelecimento);
    }

    public ProdutoBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public ProdutoBuilder comDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public ProdutoBuilder comPreço(double preço) {
        this.preço = preço;
        return this;
    }

    public ProdutoBuilder comCategoria(Categoria categoria) {
        this.categoria = categoria;
        return this;
    }

    public ProdutoBuilder comMarca(String marca) {
        this.marca = marca;
        return this;
    }

    public ProdutoBuilder comImagem(String imagem) {
        this.imagem = imagem;
        return this;
    }

    public ProdutoBuilder comIdEstabelecimento(Long idEstabelecimento){
        this.idEstabelecimento = idEstabelecimento;
        return this;
    }
}
