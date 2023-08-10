package br.com.prime.prime.dominio.estabelecimento;

public record EstabelecimentoDTO(long id,
                                 String nome,
                                 String telefone,
                                 String horarioAtendimento,
                                 int numero,
                                 String cidade,
                                 String logradouro,
                                 String estado,
                                 String cnpj,
                                 Long idUsuario) {
}