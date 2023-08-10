package br.com.prime.prime.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.prime.prime.dominio.Categoria;
import br.com.prime.prime.dominio.Produto;

public interface ProdutoRepository extends CrudRepository<Produto, Long> {
    public List<Produto> findByNomeContainingIgnoreCase(String nome);

    public List<Produto> findByCategoria(Categoria categoria);
}
