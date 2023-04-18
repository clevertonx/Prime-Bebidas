package br.com.prime.prime.dto;

import br.com.prime.prime.models.Categoria;
import br.com.prime.prime.models.Produto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoPutDTO {

    private Long id;
    @NotBlank(message = "Um nome é obrigatório")
    private String nome;
    private String descricao;
    @NotBlank(message = "Uma marca é obrigatória")
    private String marca;
    @NotNull(message = "Um valor é obrigatório")
    private Double preco;
    @NotNull(message = "Uma categoria é obrigatória")
    private Categoria categoria;
    private String imagem;

    public ProdutoPutDTO(Produto produto) {
        this.id = produto.getId();
        this.nome = produto.getNome();
        this.descricao = produto.getDescricao();
        this.marca = produto.getMarca();
        this.preco = produto.getPreco();
        this.categoria = produto.getCategoria();
        this.imagem = produto.getImagem();
    }
}
