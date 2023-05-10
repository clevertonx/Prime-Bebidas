package br.com.prime.prime.dto;

import org.hibernate.validator.constraints.br.CNPJ;

import br.com.prime.prime.models.Estabelecimento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    private int numero;
    private String cidade;
    private String logradouro;
    private String estado;
    private String cnpj;
    private Long idUsuario;

    public EstabelecimentoResponseDTO(Estabelecimento estabelecimento) {
        this.id = estabelecimento.getId();
        this.nome = estabelecimento.getNome();
        this.telefone = estabelecimento.getTelefone();
        this.horarioAtendimento = estabelecimento.getHorarioAtendimento();
        this.numero = estabelecimento.getNumero();
        this.cidade = estabelecimento.getCidade();
        this.logradouro = estabelecimento.getLogradouro();
        this.estado = estabelecimento.getEstado();
        this.cnpj = estabelecimento.getCnpj();
        this.idUsuario = estabelecimento.getUsuario().getId();
    }

}
