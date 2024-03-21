package br.com.prime.prime.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.prime.prime.models.Estabelecimento;

public interface EstabelecimentoRepository extends
        CrudRepository<Estabelecimento, Long> {
    List<Estabelecimento> findByNomeContainingIgnoreCase(String nome);

    List<Estabelecimento> findByTelefoneContainingIgnoreCase(String telefone);

    List<Estabelecimento> findByLogradouroContainingIgnoreCase(String logradouro);

    List<Estabelecimento> findByCepContainingIgnoreCase(String cep);

    List<Estabelecimento> findByCidadeContainingIgnoreCase(String cidade);

    List<Estabelecimento> findByEstadoContainingIgnoreCase(String estado);

    List<Estabelecimento> findByCnpjContaining(String cnpj);
}
