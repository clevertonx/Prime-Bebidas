package br.com.prime.prime.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.prime.prime.Builders.ProdutoBuilder;
import br.com.prime.prime.models.Categoria;
import br.com.prime.prime.models.Estabelecimento;
import br.com.prime.prime.models.PrecoInvalidoException;
import br.com.prime.prime.models.Produto;

@DataJpaTest
public class ProdutoRepositoryTest {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @Test
    public void deve_salvar_um_produto() throws PrecoInvalidoException {

        Produto produto = new ProdutoBuilder().construir();

        produtoRepository.save(produto);

        Assertions.assertNotNull(produto.getId());
    }

    @Test
    void deve_remover_produto() throws PrecoInvalidoException {
        Produto produto = new ProdutoBuilder().construir();
        produtoRepository.save(produto);

        produtoRepository.deleteById(produto.getId());

        Optional<Produto> usuarioBuscado = produtoRepository.findById(produto.getId());

        Assertions.assertFalse(usuarioBuscado.isPresent());
    }

    @Test
    public void deve_buscar_produto_pelo_nome() throws PrecoInvalidoException {
        String nome = "Vodka";
        Produto produto = new ProdutoBuilder().comNome(nome).construir();
        Estabelecimento estabelecimento = new Estabelecimento();
        produto.setEstabelecimento(estabelecimento);
        produtoRepository.save(produto);
        estabelecimentoRepository.save(estabelecimento);

        List<Produto> produtoRetornado = produtoRepository.findByNomeContainingIgnoreCase(nome);

        Assertions.assertTrue(produtoRetornado.contains(produto));
    }

    @Test
    public void deve_buscar_produto_pela_marca() throws PrecoInvalidoException {
        String nome = "Vodka";
        Produto produto = new ProdutoBuilder().comNome(nome).construir();
        produtoRepository.save(produto);

        List<Produto> produtoRetornado = produtoRepository.findByNomeContainingIgnoreCase(nome);

        Assertions.assertTrue(produtoRetornado.contains(produto));
    }

    @Test
    public void deve_buscar_produto_pela_categoria() throws PrecoInvalidoException {
        Categoria categoria = Categoria.Destilada;
        Produto produto = new ProdutoBuilder().comCategoria(categoria).construir();
        produtoRepository.save(produto);

        List<Produto> produtoRetornado = produtoRepository.findByCategoria(categoria);

        Assertions.assertTrue(produtoRetornado.contains(produto));
    }
}
