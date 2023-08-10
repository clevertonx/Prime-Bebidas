package br.com.prime.prime.dto;

import br.com.prime.prime.dominio.Categoria;
import br.com.prime.prime.dominio.Produto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoEstabelecimentoUsuarioResponseDTO {
    private long id;
    private String nome;
    private String descricao;
    private String marca;
    private Double preco;
    private Categoria categoria;
    private long idEstabelecimento;

    public ProdutoEstabelecimentoUsuarioResponseDTO(Produto produto) {
        this.id = produto.getId();
        this.nome = produto.getNome();
        this.descricao = produto.getDescricao();
        this.marca = produto.getMarca();
        this.preco = produto.getPreco();
        this.categoria = produto.getCategoria();
        this.idEstabelecimento = produto.getEstabelecimento().getId(); 
    }
}
