package br.com.prime.prime.dominio;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import br.com.prime.prime.Builders.ProdutoBuilder;

public class ProdutoTest {
    @Test
    public void deve_criar_um_produto()
            throws PrecoInvalidoException {

        String nome = "Vodka";
        String descricao = "Nossa premiada vodka tem um sabor robusto com acabamento seco para uma maior suavidade";
        String marca = "Absolut";
        Double preco = 89.90;
        Categoria categoria = Categoria.Destilada;

        Produto produto = new ProdutoBuilder().construir();

        assertThat(produto.getNome()).isEqualTo(nome);
        assertThat(produto.getDescricao()).isEqualTo(descricao);
        assertThat(produto.getMarca()).isEqualTo(marca);
        assertThat(produto.getPreco()).isEqualTo(preco);
        assertThat(produto.getCategoria()).isEqualTo(categoria);
    }
}