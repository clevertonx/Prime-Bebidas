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
public class ProdutoRequestDTO {

    @NotBlank(message = "Um nome é obrigatório")
    private String nome;
    private String descricao;
    @NotBlank(message = "Uma marca é obrigatória")
    private String marca;
    @NotNull(message = "Um valor é obrigatório")
    private Double preço;
    @NotNull(message = "Uma categoria é obrigatória")
    private Categoria categoria;
    private String imagem;
    private Long idEstabelecimento;

    public ProdutoRequestDTO(Produto produto) {
        this.nome = produto.getNome();
        this.descricao = produto.getDescricao();
        this.marca = produto.getMarca();
        this.preço = produto.getPreço();
        this.categoria = produto.getCategoria();
        this.imagem = produto.getImagem();
        this.idEstabelecimento = produto.getEstabelecimento().getId();
    }

}
