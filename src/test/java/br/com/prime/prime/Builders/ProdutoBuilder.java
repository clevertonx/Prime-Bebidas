package br.com.prime.prime.Builders;

import br.com.prime.prime.dominio.Categoria;
import br.com.prime.prime.dominio.estabelecimento.Estabelecimento;
import br.com.prime.prime.dominio.PrecoInvalidoException;
import br.com.prime.prime.dominio.Produto;

public class ProdutoBuilder {

    private String nome = "Vodka";
    private String descricao = "Nossa premiada vodka tem um sabor robusto com acabamento seco para uma maior suavidade";
    private String marca = "Absolut";
    private Double preco = 89.90;
    private Categoria categoria = Categoria.Destilada;
    private String imagem = Imagem.getBytes();
    private Estabelecimento estabelecimento = new EstabelecimentoBuilder().construir();


    public ProdutoBuilder()
            throws PrecoInvalidoException {

    }

    public Produto construir()
            throws PrecoInvalidoException {
        return new Produto(nome, descricao, marca, preco, categoria, imagem, estabelecimento);
    }

    public ProdutoBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public ProdutoBuilder comDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public ProdutoBuilder comPreço(double preco) {
        this.preco = preco;
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

    public ProdutoBuilder comEstabelecimento(Estabelecimento estabelecimento){
        this.estabelecimento = estabelecimento;
        return this;
    }
}
