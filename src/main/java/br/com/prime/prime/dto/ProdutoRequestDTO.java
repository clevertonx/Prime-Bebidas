package br.com.prime.prime.dto;

import br.com.prime.prime.models.Categoria;
import br.com.prime.prime.models.Produto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoRequestDTO {
    
    private String nome;
    private String descricao;
    private String marca;
    private Double preço;
    private Categoria categoria;

    public ProdutoRequestDTO(Produto produto) {
        this.nome = produto.getNome();
        this.descricao = produto.getDescricao();
        this.marca = produto.getMarca();
        this.preço = produto.getPreço();
        this.categoria = produto.getCategoria();
    }

    public Produto toProduto() {
        return new Produto()
            .setNome(this.nome)
            .setDescricao(this.descricao)
            .setMarca(this.marca)
            .setPreço(this.preço)
            .setCategoria(this.categoria);
    }
}
