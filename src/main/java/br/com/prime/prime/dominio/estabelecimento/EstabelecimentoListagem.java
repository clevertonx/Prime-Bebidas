package br.com.prime.prime.dominio.estabelecimento;

public record EstabelecimentoListagem(long id,
                                      String nome,
                                      String cidade,
                                      String estado,
                                      String cnpj) {
}
