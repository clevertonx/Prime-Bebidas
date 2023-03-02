package br.com.prime.prime.dto;

import br.com.prime.prime.models.Estabelecimento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstabelecimentoRequestDTO {
    
    private String nome;
    private String telefone;
    private String horarioAtendimento;
    private int numero;
    private String cidade;
    private String logradouro;
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
