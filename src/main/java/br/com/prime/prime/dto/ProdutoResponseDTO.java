package br.com.prime.prime.dto;

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
    private Double preço;
    private String imagem;
}
