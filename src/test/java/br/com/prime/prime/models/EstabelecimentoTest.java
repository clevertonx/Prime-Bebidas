package br.com.prime.prime.models;

import org.junit.jupiter.api.Assertions;
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

        Estabelecimento estabelecimento = new EstabelecimentoBuilder().construir();

        Assertions.assertEquals(nome, estabelecimento.getNome());
        Assertions.assertEquals(telefone, estabelecimento.getTelefone());
        Assertions.assertEquals(horarioAtendimento, estabelecimento.getHorarioAtendimento());
        Assertions.assertEquals(numero, estabelecimento.getNumero());
        Assertions.assertEquals(cidade, estabelecimento.getCidade());
        Assertions.assertEquals(logradouro, estabelecimento.getLogradouro());
        Assertions.assertEquals(estado, estabelecimento.getEstado());
    }
}
