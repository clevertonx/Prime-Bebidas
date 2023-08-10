package br.com.prime.prime.dominio.estabelecimento;

public record EstabelecimentoAtualizacao(Long id,
                                         String nome,
                                         String telefone,
                                         String horarioAtendimento,
                                         int numero,
                                         String cidade,
                                         String logradouro,
                                         String estado,
                                         String cnpj,
                                         Long idUsuario) {

    public void atualizarEstabelecimento(Estabelecimento estabelecimento) {
        estabelecimento.setNome(nome() != null ? nome() : estabelecimento.getNome());
        estabelecimento.setTelefone(telefone() != null ? telefone() : estabelecimento.getTelefone());
        estabelecimento.setHorarioAtendimento(horarioAtendimento() != null ? horarioAtendimento() : estabelecimento.getHorarioAtendimento());
        estabelecimento.setNumero(numero() != 0 ? numero() : estabelecimento.getNumero());
        estabelecimento.setCidade(cidade() != null ? cidade() : estabelecimento.getCidade());
        estabelecimento.setLogradouro(logradouro() != null ? logradouro() : estabelecimento.getLogradouro());
        estabelecimento.setEstado(estado() != null ? estado() : estabelecimento.getEstado());
        estabelecimento.setCnpj(cnpj() != null ? cnpj() : estabelecimento.getCnpj());
    }
}
