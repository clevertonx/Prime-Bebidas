package br.com.prime.prime.Builders;

import br.com.prime.prime.models.Estabelecimento;

public class EstabelecimentoBuilder {
    String nome = "Comper";
    String telefone = "6733886075";
    String horarioAtendimento = "8hr às 20hr";
    int numero = 2023;
    String cidade = "Campo Grande";
    String logradouro = "Rua tchudusbangu";
    String estado = "MS";

    public EstabelecimentoBuilder() {

    }

    public Estabelecimento construir() {
        return new Estabelecimento(nome, telefone, horarioAtendimento, numero, cidade, logradouro, estado);
    }

    public EstabelecimentoBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public EstabelecimentoBuilder comTelefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public EstabelecimentoBuilder comHorarioAtendimento(String horarioAtendimento) {
        this.horarioAtendimento = horarioAtendimento;
        return this;
    }

    public EstabelecimentoBuilder comNumero(int numero) {
        this.numero = numero;
        return this;
    }

    public EstabelecimentoBuilder comCidade(String cidade) {
        this.cidade = cidade;
        return this;
    }

    public EstabelecimentoBuilder comLogradouro(String logradouro) {
        this.logradouro = logradouro;
        return this;
    }

    public EstabelecimentoBuilder comEstado(String estado) {
        this.estado = estado;
        return this;
    }
}
