package br.com.prime.prime.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

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

import br.com.prime.prime.Builders.EstabelecimentoBuilder;
import br.com.prime.prime.Builders.Imagem;
import br.com.prime.prime.Builders.ProdutoBuilder;
import br.com.prime.prime.Builders.UsuarioBuilder;
import br.com.prime.prime.dto.ProdutoRequestDTO;
import br.com.prime.prime.dto.ProdutoResponseDTO;
import br.com.prime.prime.models.Categoria;
import br.com.prime.prime.models.Estabelecimento;
import br.com.prime.prime.models.Produto;
import br.com.prime.prime.models.Usuario;
import br.com.prime.prime.repository.EstabelecimentoRepository;
import br.com.prime.prime.repository.ProdutoRepository;
import br.com.prime.prime.utils.JsonUtil;
import jakarta.transaction.Transactional;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@Transactional
public class ProdutoControllerTest {

        private ObjectMapper objectMapper = new ObjectMapper();

        @Autowired
        private MockMvc mockMvc;

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

                Usuario usuario = new UsuarioBuilder().construir();

                Estabelecimento estabelecimento = new EstabelecimentoBuilder().comUsuario(usuario).construir();
                estabelecimentoRepository.save(estabelecimento);

                String nomeProduto = "Pinga";
                Produto produto = new ProdutoBuilder().comEstabelecimento(estabelecimento).comNome(nomeProduto)
                                .construir();
                produtoRepository.save(produto);

                MvcResult resultado = mockMvc.perform(get("/produto/buscarPorNome?nome=" + nomeProduto)).andReturn();

                ProdutoResponseDTO[] produtosRetornadosDTO = JsonUtil.mapFromJson(
                                resultado.getResponse().getContentAsString(),
                                ProdutoResponseDTO[].class);

                assertThat(HttpStatus.OK.value()).isEqualTo(resultado.getResponse().getStatus());
                assertThat(produtosRetornadosDTO).extracting("nome").contains(nomeProduto);
                assertThat(produtosRetornadosDTO).hasSize(1);
        }

        @Test
        public void deve_buscar_os_produtos_cadastrados_pela_categoria() throws Exception {

                Usuario usuario = new UsuarioBuilder().construir();
                Categoria categoria = Categoria.Fortificada;

                Estabelecimento estabelecimento = new EstabelecimentoBuilder().comUsuario(usuario).construir();
                estabelecimentoRepository.save(estabelecimento);

                ArrayList<Produto> produtos = new ArrayList<Produto>();
                produtos.add(new ProdutoBuilder().comEstabelecimento(estabelecimento).construir());
                produtos.add(new ProdutoBuilder().comEstabelecimento(estabelecimento).comCategoria(categoria)
                                .construir());

                produtoRepository.saveAll(produtos);

                this.mockMvc
                                .perform(get("/produto"))
                                .andExpect(status().isOk());
                assertEquals(Categoria.Fortificada, categoria);
        }

        @Test
        void deve_excluir_um_produto_pelo_id() throws Exception {

                Usuario usuario = new UsuarioBuilder().construir();

                Estabelecimento estabelecimento = new EstabelecimentoBuilder().comUsuario(usuario).construir();
                estabelecimentoRepository.save(estabelecimento);

                int quantidadeEsperada = 0;

                Produto produto = new ProdutoBuilder().comEstabelecimento(estabelecimento).construir();
                produtoRepository.save(produto);

                String url = "/produto/" + produto.getId();
                mockMvc.perform(delete(url)).andReturn();

                Iterable<Produto> produtosEncontrados = produtoRepository.findAll();
                long quantidadeEncontrada = produtosEncontrados.spliterator().getExactSizeIfKnown();

                assertEquals(quantidadeEsperada, quantidadeEncontrada);
        }

        @Test
        void deve_cadastrar_um_novo_produto() throws Exception {

                Usuario usuario = new UsuarioBuilder().construir();

                Estabelecimento estabelecimento = new EstabelecimentoBuilder().comUsuario(usuario).construir();
                estabelecimentoRepository.save(estabelecimento);

                ProdutoRequestDTO produtoDTO = new ProdutoRequestDTO("pinga", "testezinho", "skol", 99.90,
                                Categoria.Fermentada, Imagem.getBytes(), estabelecimento.getId());

                mockMvc.perform(post("/produto")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JsonUtil.toJson(produtoDTO)))
                                .andExpect(status().isCreated())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.nome").value("pinga"))
                                .andExpect(jsonPath("$.descricao").value("testezinho"))
                                .andExpect(jsonPath("$.marca").value("skol"))
                                .andExpect(jsonPath("$.preco").value(99.90))
                                .andExpect(jsonPath("$.imagem").value(Imagem.getBytes()))
                                .andExpect(jsonPath("$.idEstabelecimento").value(estabelecimento.getUsuario().getId()))
                                .andDo(result -> {
                                        String jsonResponse = result.getResponse().getContentAsString();
                                        ProdutoResponseDTO responseDTO = objectMapper.readValue(jsonResponse,
                                                        ProdutoResponseDTO.class);
                                        assertThat(responseDTO.getNome()).isEqualTo("pinga");
                                        assertThat(responseDTO.getDescricao()).isEqualTo("testezinho");
                                        assertThat(responseDTO.getMarca()).isEqualTo("skol");
                                        assertThat(responseDTO.getPreco()).isEqualTo(99.90);
                                        assertThat(responseDTO.getImagem()).isEqualTo(Imagem.getBytes());
                                        assertThat(responseDTO.getIdEstabelecimento())
                                                        .isEqualTo(responseDTO.getIdEstabelecimento());
                                });
        }

        @Test
        public void deve_alterar_dados_do_produto() throws Exception {

                Usuario usuario = new UsuarioBuilder().construir();

                Estabelecimento estabelecimento = new EstabelecimentoBuilder().comUsuario(usuario).construir();
                estabelecimentoRepository.save(estabelecimento);

                Produto produto = new ProdutoBuilder().comEstabelecimento(estabelecimento).construir();

                produtoRepository.save(produto);

                ProdutoRequestDTO produtoDTO = new ProdutoRequestDTO("nomeAlterado", "descAlterada", "marcaAlterada",
                                25.0, Categoria.Bitter, Imagem.getBytes(), estabelecimento.getUsuario().getId());

                mockMvc.perform(put("/produto/{id}",
                                produto.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(produtoDTO)))
                                .andExpect(status().isOk());

                Produto produtoAlterado = produtoRepository.findById(produto.getId())
                                .orElse(null);
                assertNotNull(produtoAlterado);
                assertEquals("nomeAlterado", produtoAlterado.getNome());
                assertEquals("descAlterada", produtoAlterado.getDescricao());
                assertEquals("marcaAlterada", produtoAlterado.getMarca());
                assertEquals(25.0, produtoAlterado.getPreco());
                assertEquals(Categoria.Bitter, produtoAlterado.getCategoria());
                assertEquals(Imagem.getBytes(), produtoAlterado.getImagem());
                assertEquals(estabelecimento.getId(), produtoAlterado.getEstabelecimento().getId());
        }

        private String asJsonString(Object object) throws JsonProcessingException {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.writeValueAsString(object);
        }

}
