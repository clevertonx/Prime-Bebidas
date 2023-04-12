package br.com.prime.prime.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import br.com.prime.prime.Builders.EstabelecimentoBuilder;
import br.com.prime.prime.Builders.ProdutoBuilder;
import br.com.prime.prime.dto.ProdutoRequestDTO;
import br.com.prime.prime.dto.ProdutoResponseDTO;
import br.com.prime.prime.models.Categoria;
import br.com.prime.prime.models.Estabelecimento;
import br.com.prime.prime.models.Produto;
import br.com.prime.prime.repository.EstabelecimentoRepository;
import br.com.prime.prime.repository.ProdutoRepository;
import br.com.prime.prime.utils.JsonUtil;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class ProdutoControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @BeforeEach
    @AfterEach
    public void deletaDados() {
        produtoRepository.deleteAll();
    }

    @Test
    public void deve_buscar_produtos_por_nome() throws Exception {
        Estabelecimento estabelecimento = new EstabelecimentoBuilder().construir();
        estabelecimentoRepository.save(estabelecimento);

        String nomeProduto = "Pinga";
        Produto produto = new ProdutoBuilder().comNome(nomeProduto).comEstabelecimento(estabelecimento).construir();
        produtoRepository.save(produto);

        MvcResult resultado = mvc.perform(get("/produto/buscarPorNome?nome=" + nomeProduto)).andReturn();

        ProdutoResponseDTO[] produtosRetornadosDTO = JsonUtil.mapFromJson(
                resultado.getResponse().getContentAsString(),
                ProdutoResponseDTO[].class);

        assertThat(HttpStatus.OK.value()).isEqualTo(resultado.getResponse().getStatus());
        assertThat(produtosRetornadosDTO).extracting("nome").contains(nomeProduto);
        assertThat(produtosRetornadosDTO).hasSize(1);
    }

    @Test
    public void deve_buscar_os_produtos_cadastrados_pela_categoria() throws Exception {
        Categoria categoria = Categoria.Fortificada;
        ArrayList<Produto> produtos = new ArrayList<Produto>();
        produtos.add(new ProdutoBuilder().construir());
        produtos.add(new ProdutoBuilder().comCategoria(categoria).construir());

        produtoRepository.saveAll(produtos);

        this.mvc
                .perform(get("/produto"))
                .andExpect(status().isOk());
        assertEquals(Categoria.Fortificada, categoria);
    }

    @Test
    void deve_excluir_um_produto_pelo_id() throws Exception {

        Estabelecimento estabelecimento = new EstabelecimentoBuilder().construir();
        estabelecimentoRepository.save(estabelecimento);

        int quantidadeEsperada = 0;

        Produto produto = new ProdutoBuilder().comEstabelecimento(estabelecimento).construir();
        produtoRepository.save(produto);

        String url = "/produto/" + produto.getId();
        mvc.perform(delete(url)).andReturn();

        Iterable<Produto> produtosEncontrados = produtoRepository.findAll();
        long quantidadeEncontrada = produtosEncontrados.spliterator().getExactSizeIfKnown();

        assertEquals(quantidadeEsperada, quantidadeEncontrada);
    }

    @Test
    void deve_cadastrar_um_novo_produto() throws Exception {

        Estabelecimento estabelecimento = new EstabelecimentoBuilder().construir();
        estabelecimentoRepository.save(estabelecimento);

        int quantidadeEsperada = 1;

        Produto produto = new ProdutoBuilder().comEstabelecimento(estabelecimento).construir();
        produtoRepository.save(produto);

        mvc.perform(post("/produto/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(produto)))
                .andExpect(status().isCreated());

        Iterable<Produto> buscaProduto = produtoRepository.findAll();
        long quantidadeEncontrada = buscaProduto.spliterator().getExactSizeIfKnown();

        assertThat(quantidadeEncontrada).isEqualTo(quantidadeEsperada);

    }

    @Test
    public void deve_incluir_um_produto() throws Exception {

        Estabelecimento estabelecimento = new EstabelecimentoBuilder().construir();
        estabelecimentoRepository.save(estabelecimento);
        String nomeProduto = "Pinga";
        Produto produto = new ProdutoBuilder().comNome(nomeProduto).comEstabelecimento(estabelecimento).construir();
        produtoRepository.save(produto);
        String json = toJSON(produto);
        mvc.perform(post("/produto/").content(json).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());

        List<Produto> produtosRetornados = produtoRepository
                .findByNomeContainingIgnoreCase(produto.getNome());

        Assertions.assertThat(produtosRetornados.size()).isEqualTo(1);
        Assertions.assertThat(
                produto.getNome())
                .isIn(produtosRetornados.stream().map(Produto::getNome).toList());
    }

    private String toJSON(Produto produto) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(produto);
        return json;
    }

    @Test
    void deve_criar_um_produto() throws Exception {

        Estabelecimento estabelecimento = new EstabelecimentoBuilder().construir();
        estabelecimentoRepository.save(estabelecimento);

        Produto produto = new ProdutoBuilder().comEstabelecimento(estabelecimento).construir();
        produtoRepository.save(produto);

        ProdutoRequestDTO produtoRequestDTO = new ProdutoRequestDTO();

        mvc
                .perform(
                        post("/produto/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JsonUtil.toJson(produtoRequestDTO)))
                .andExpect(status().isCreated());

        Iterable<Produto> produtosEncontrados = produtoRepository.findAll();

        assertThat(produtosEncontrados)
                .extracting("nome")
                .containsOnly(produto.getNome());
        assertThat(produtosEncontrados)
                .extracting("descricao")
                .containsOnly(produto.getDescricao());
        assertThat(produtosEncontrados)
                .extracting("marca")
                .containsOnly(produto.getMarca());
        assertThat(produtosEncontrados)
                .extracting("preco")
                .containsOnly(produto.getPreco());
        assertThat(produtosEncontrados)
                .extracting("categoria")
                .containsOnly(produto.getCategoria());
        assertThat(produtosEncontrados)
                .extracting("imagem")
                .containsOnly(produto.getImagem());
        assertThat(produtosEncontrados)
                .extracting("estabelecimento")
                .containsOnly(produto.getEstabelecimento());
    }

    @Test
    public void deve_alterar_dados_do_produto() throws Exception {
        Produto nomeAlterado = new ProdutoBuilder().construir();
        produtoRepository.save(nomeAlterado);

        nomeAlterado.setNome("Pinga");

        String json = toJSON(nomeAlterado);

        this.mvc.perform(put("/produto").content(json).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());

        Produto produtoRetornado = produtoRepository.findById(nomeAlterado.getId()).get();
        Assertions.assertThat(produtoRetornado.getId()).isEqualTo(nomeAlterado.getId());
        Assertions.assertThat(produtoRetornado.getNome()).isEqualTo(nomeAlterado.getNome());
    }
}
