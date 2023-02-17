package br.com.prime.prime.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.prime.prime.models.Categoria;
import br.com.prime.prime.models.Produto;

public interface ProdutoRepository extends CrudRepository<Produto, Long> {
    public List<Produto> findByNomeContainingIgnoreCase(String nome);

    public List<Produto> findByCategoria(Categoria categoria);
}
