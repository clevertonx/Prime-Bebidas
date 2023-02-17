package br.com.prime.prime.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.prime.prime.Builders.ProdutoBuilder;

public class ProdutoTest {
    @Test
    public void deve_criar_um_produto()
            throws PreçoInvalidoException {

        String nome = "Vodka";
        String descricao = "Nossa premiada vodka tem um sabor robusto com acabamento seco para uma maior suavidade";
        String marca = "Absolut";
        Double preço = 89.90;
        Categoria categoria = Categoria.Destilada;

        Produto produto = new ProdutoBuilder().construir();

        Assertions.assertEquals(nome, produto.getNome());
        Assertions.assertEquals(descricao, produto.getDescricao());
        Assertions.assertEquals(marca, produto.getMarca());
        Assertions.assertEquals(preço, produto.getPreço());
        Assertions.assertEquals(categoria, produto.getCategoria());
    }
}