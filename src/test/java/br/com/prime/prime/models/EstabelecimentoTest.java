package br.com.prime.prime.models;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import br.com.prime.prime.Builders.EstabelecimentoBuilder;

public class EstabelecimentoTest {
    @Test
    public void deve_criar_um_estabelecimento() {

        String nome = "Comper";
        String telefone = "6733886075";
        String horarioAtendimento = "8hr às 20hr";
        int numero = 2023;
        String cidade = "Campo Grande";
        String logradouro = "Rua tchudusbangu";
        String estado = "MS";
        String cnpj = "67.596.818/0001-90";

        Estabelecimento estabelecimento = new EstabelecimentoBuilder().construir();

        assertThat(estabelecimento.getNome()).isEqualTo(nome);
        assertThat(estabelecimento.getTelefone()).isEqualTo(telefone);
        assertThat(estabelecimento.getHorarioAtendimento()).isEqualTo(horarioAtendimento);
        assertThat(estabelecimento.getNumero()).isEqualTo(numero);
        assertThat(estabelecimento.getCidade()).isEqualTo(cidade);
        assertThat(estabelecimento.getLogradouro()).isEqualTo(logradouro);
        assertThat(estabelecimento.getEstado()).isEqualTo(estado);
        assertThat(estabelecimento.getCnpj()).isEqualTo(cnpj);
    }
}
