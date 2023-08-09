package br.com.prime.prime.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.prime.prime.models.Categoria;
import br.com.prime.prime.models.Produto;

public interface ProdutoRepository extends CrudRepository<Produto, Long> {
    List<Produto> findByNomeContainingIgnoreCase(String nome);

    List<Produto> findByCategoria(Categoria categoria);
}
