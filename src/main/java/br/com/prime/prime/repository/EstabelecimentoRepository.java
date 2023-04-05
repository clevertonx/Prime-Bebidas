package br.com.prime.prime.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.prime.prime.models.Estabelecimento;

public interface EstabelecimentoRepository extends
        CrudRepository<Estabelecimento, Long> {
    public List<Estabelecimento> findByNomeContainingIgnoreCase(String nome);

    public List<Estabelecimento> findByTelefoneContainingIgnoreCase(String telefone);

    public List<Estabelecimento> findByLogradouroContainingIgnoreCase(String logradouro);

    public List<Estabelecimento> findByCidadeContainingIgnoreCase(String cidade);

    public List<Estabelecimento> findByEstadoContainingIgnoreCase(String estado);

    public List<Estabelecimento> findByCnpjContaining(String cnpj);
}
