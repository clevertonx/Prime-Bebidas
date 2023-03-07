package br.com.prime.prime.dto;

import br.com.prime.prime.models.Produto;

public class ProdutoResponseDTO {
    private String nome;
    private String descricao;
    private String marca;
    private Double preço;

    public ProdutoResponseDTO(String nome, String descricao, String marca, Double preço) {
        this.nome = nome;
        this.descricao = descricao;
        this.marca = marca;
        this.preço = preço;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getMarca() {
        return marca;
    }

    public double getPreço() {
        return preço;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setPreço(Double preço) {
        this.preço = preço;
    }
    public Produto toProduto() {
        return new Produto()
            .setNome(this.nome)
            .setDescricao(this.descricao)
            .setMarca(this.marca)
            .setPreço(this.preço);
    }
}
