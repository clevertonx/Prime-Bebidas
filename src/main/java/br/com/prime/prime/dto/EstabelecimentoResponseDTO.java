package br.com.prime.prime.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstabelecimentoResponseDTO {

    private Long id;
    private String nome;
    private String telefone;
    private String horarioAtendimento;
    private int numero;
    private String cidade;
    private String logradouro;
    private String estado;
    private String cnpj;
}
