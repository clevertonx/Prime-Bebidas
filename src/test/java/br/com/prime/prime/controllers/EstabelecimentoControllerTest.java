package br.com.prime.prime.controllers;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import br.com.prime.prime.Builders.Imagem;
import br.com.prime.prime.dto.ProdutoRequestDTO;
import br.com.prime.prime.dto.ProdutoResponseDTO;
import br.com.prime.prime.models.Categoria;
import br.com.prime.prime.utils.JsonUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.JsonPath;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.prime.prime.Builders.EstabelecimentoBuilder;
import br.com.prime.prime.Builders.UsuarioBuilder;
import br.com.prime.prime.dto.EstabelecimentoRequestDTO;
import br.com.prime.prime.dto.EstabelecimentoResponseDTO;
import br.com.prime.prime.models.Estabelecimento;
import br.com.prime.prime.models.Usuario;
import br.com.prime.prime.repository.EstabelecimentoRepository;
import br.com.prime.prime.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class EstabelecimentoControllerTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    @AfterEach
    public void deletaDados() {
        estabelecimentoRepository.deleteAll();
    }

    @WithMockUser("spring")
    @Test
    public void deve_buscar_os_estabelecimentos_pelo_nome() throws Exception {
        String email = "testezin@gmail.com";
        String nomeEstabelecimento = "NomeTeste";

        Usuario usuario = new UsuarioBuilder().construir();
        Usuario usuario2 = new UsuarioBuilder().comEmail(email).construir();

        Estabelecimento estabelecimento = new EstabelecimentoBuilder().comNome(nomeEstabelecimento).comUsuario(usuario).construir();
        Estabelecimento estabelecimento2 = new EstabelecimentoBuilder().comUsuario(usuario2).construir();

        estabelecimentoRepository.save(estabelecimento);
        estabelecimentoRepository.save(estabelecimento2);

        mockMvc.perform(get("/estabelecimento/buscarEstabelecimentoPorNome?nome=" + nomeEstabelecimento))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].nome").value(nomeEstabelecimento));
    }


    @WithMockUser("spring")
    @Test
    public void deve_remover_estabelecimento_por_id() throws Exception {

        Usuario usuario = new Usuario();

        usuarioRepository.save(usuario);

        Estabelecimento estabelecimento = new Estabelecimento("Teste", "67991333993", "8 as 9",
                2024, "cg", "rua teste", "ms", "67.596.818/0001-90", usuario);
        estabelecimentoRepository.save(estabelecimento);

        long estabelecimentoId = estabelecimento.getId();

        mockMvc.perform(delete("/estabelecimento/{id}", estabelecimentoId))
                .andExpect(status().isOk());
    }

    @WithMockUser("spring")
    @Test
    public void deve_incluir_um_estabelecimento() throws Exception {

        Usuario usuario = new UsuarioBuilder().construir();
        usuarioRepository.save(usuario);

        EstabelecimentoRequestDTO estabelecimentoRequestDTO = new EstabelecimentoRequestDTO("teste"
                , "67991333993", "8 as 9", 1035, "cg", "rua teste", "ms", "67596818000190", usuario.getId());


        mockMvc.perform(post("/estabelecimento")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(estabelecimentoRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nome").value(estabelecimentoRequestDTO.getNome()))
                .andExpect(jsonPath("$.telefone").value(estabelecimentoRequestDTO.getTelefone()))
                .andExpect(jsonPath("$.horarioAtendimento").value(estabelecimentoRequestDTO.getHorarioAtendimento()))
                .andExpect(jsonPath("$.numero").value(estabelecimentoRequestDTO.getNumero()))
                .andExpect(jsonPath("$.cidade").value(estabelecimentoRequestDTO.getCidade()))
                .andExpect(jsonPath("$.logradouro").value(estabelecimentoRequestDTO.getLogradouro()))
                .andExpect(jsonPath("$.estado").value(estabelecimentoRequestDTO.getEstado()))
                .andExpect(jsonPath("$.cnpj").value(estabelecimentoRequestDTO.getCnpj()))
                .andExpect(jsonPath("$.idUsuario").value(usuario.getId()))
                .andDo(result -> {
                    String jsonResponse = result.getResponse().getContentAsString();
                    EstabelecimentoResponseDTO responseDTO = objectMapper.readValue(jsonResponse,
                            EstabelecimentoResponseDTO.class);
                    assertThat(responseDTO.getNome()).isEqualTo("teste");
                    assertThat(responseDTO.getTelefone()).isEqualTo("67991333993");
                    assertThat(responseDTO.getHorarioAtendimento()).isEqualTo("8 as 9");
                    assertThat(responseDTO.getNumero()).isEqualTo(1035);
                    assertThat(responseDTO.getCidade()).isEqualTo("cg");
                    assertThat(responseDTO.getLogradouro()).isEqualTo("rua teste");
                    assertThat(responseDTO.getEstado()).isEqualTo("ms");
                    assertThat(responseDTO.getCnpj()).isEqualTo("67596818000190");
                    assertThat(responseDTO.getIdUsuario()).isEqualTo(usuario.getId());
                    assertThat(responseDTO.getIdUsuario())
                            .isEqualTo(responseDTO.getIdUsuario());
                });
    }

    @WithMockUser("spring")
    @Test
    public void deve_alterar_dados_do_estabelecimento() throws Exception {

        Usuario usuario = new UsuarioBuilder().construir();

        usuarioRepository.save(usuario);

        Estabelecimento estabelecimento = new Estabelecimento("Teste", "67991333993", "8 as 9",
                2024, "cg", "rua teste", "ms", "67.596.818/0001-90", usuario);

        estabelecimentoRepository.save(estabelecimento);

        EstabelecimentoRequestDTO estabelecimentoDTO = new EstabelecimentoRequestDTO("Estabelecimento Alterado",
                "987654321",
                "9-18", 2024, "Nova Cidade", "Nova Rua", "Novo Estado", "67.987.654/0001-90",
                usuario.getId());

        mockMvc.perform(put("/estabelecimento/{id}", estabelecimento.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(estabelecimentoDTO)))
                .andExpect(status().isOk());

        Estabelecimento estabelecimentoAlterado = estabelecimentoRepository.findById(estabelecimento.getId())
                .orElse(null);
        assertNotNull(estabelecimentoAlterado);
        assertEquals("Estabelecimento Alterado", estabelecimentoAlterado.getNome());
        assertEquals("987654321", estabelecimentoAlterado.getTelefone());
        assertEquals("9-18", estabelecimentoAlterado.getHorarioAtendimento());
        assertEquals(2024, estabelecimentoAlterado.getNumero());
        assertEquals("Nova Cidade", estabelecimentoAlterado.getCidade());
        assertEquals("Nova Rua", estabelecimentoAlterado.getLogradouro());
        assertEquals("Novo Estado", estabelecimentoAlterado.getEstado());
        assertEquals("67.987.654/0001-90", estabelecimentoAlterado.getCnpj());
        assertEquals(usuario.getId(), estabelecimentoAlterado.getUsuario().getId());
    }

    private String asJsonString(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}
