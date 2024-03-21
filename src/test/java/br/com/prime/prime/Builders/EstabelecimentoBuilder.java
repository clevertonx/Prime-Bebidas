package br.com.prime.prime.Builders;

import br.com.prime.prime.models.Estabelecimento;
import br.com.prime.prime.models.Usuario;

public class EstabelecimentoBuilder {
    String nome = "Comper";
    String telefone = "6733886075";
    String horarioAtendimento = "8hr às 20hr";

    String cep = "79063550";
    int numero = 2023;
    String cidade = "Campo Grande";
    String logradouro = "Rua tchudusbangu";

    String bairro = "bairro teste";
    String estado = "MS";
    String cnpj = "67.596.818/0001-90";
    Usuario usuario = new UsuarioBuilder().construir();

    public EstabelecimentoBuilder() {

    }

    public Estabelecimento construir() {
        return new Estabelecimento(nome, telefone, horarioAtendimento, cep, numero, cidade, logradouro, bairro, estado, cnpj, usuario);
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

    public EstabelecimentoBuilder comCep(String cep) {
        this.cep = cep;
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

    public EstabelecimentoBuilder comBairro(String bairro) {
        this.bairro = bairro;
        return this;
    }

    public EstabelecimentoBuilder comEstado(String estado) {
        this.estado = estado;
        return this;
    }

    public EstabelecimentoBuilder comCnpj(String cnpj){
        this.cnpj = cnpj;
        return this;
    }

    public EstabelecimentoBuilder comUsuario(Usuario usuario){
        this.usuario = usuario;
        return this;
    }

}
