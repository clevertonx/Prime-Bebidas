package br.com.prime.prime.dto;

import br.com.prime.prime.models.Estabelecimento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstabelecimentoResponseDTO {
    private long id;
    private String nome;
    private String telefone;
    private String horarioAtendimento;
    private String cep;
    private int numero;
    private String cidade;
    private String logradouro;
    private String bairro;
    private String estado;
    private String cnpj;
    private Long idUsuario;


    public EstabelecimentoResponseDTO(Estabelecimento estabelecimento) {
    }
}
