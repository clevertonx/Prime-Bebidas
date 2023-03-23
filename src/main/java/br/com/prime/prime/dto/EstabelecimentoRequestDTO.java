package br.com.prime.prime.dto;

import br.com.prime.prime.models.Estabelecimento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstabelecimentoRequestDTO {
    
    
    @NotBlank(message = "Nome não informado")
    private String nome;
    @NotBlank(message = "Telefone não informado")
    private String telefone;
    @NotBlank(message = "Horario de atendimento não informado")
    private String horarioAtendimento;
    @NotNull
    private int numero;
    @NotBlank(message = "Cidade não informada")
    private String cidade;
    @NotBlank(message = "Logradouro não informado")
    private String logradouro;
    @NotBlank(message = "Estado não informado")
    private String estado;

    public EstabelecimentoRequestDTO(Estabelecimento estabelecimento) {
        this.nome = estabelecimento.getNome();
        this.telefone = estabelecimento.getTelefone();
        this.horarioAtendimento = estabelecimento.getHorarioAtendimento();
        this.numero = estabelecimento.getNumero();
        this.cidade = estabelecimento.getCidade();
        this.logradouro = estabelecimento.getLogradouro();
        this.estado = estabelecimento.getEstado();
    }

    public Estabelecimento toEstabelecimento() {
        return new Estabelecimento()
                .setNome(this.nome)
                .setTelefone(this.telefone)
                .setHorarioAtendimento(this.horarioAtendimento)
                .setNumero(this.numero)
                .setCidade(this.cidade)
                .setLogradouro(this.logradouro)
                .setEstado(this.estado);
    }
}
