package br.com.prime.prime.dto;

import br.com.prime.prime.models.Produto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoResponseDTO {
    private long id;
    private String nome;
    private String descricao;
    private String marca;
    private Double preco;
    private String imagem;
    private Long idEstabelecimento;
    private String nomeEstabelecimento;


    public ProdutoResponseDTO(Produto produto) {
    }
}
