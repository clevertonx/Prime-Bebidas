package br.com.prime.prime.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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

        @Test
        public void deve_buscar_os_estabelecimentos_cadastrados_pelo_nome() throws Exception {
                String nome = "atacadao";
                String email = "tom@gmail.com";
                ArrayList<Estabelecimento> estabelecimentos = new ArrayList<Estabelecimento>();
                estabelecimentos.add(new EstabelecimentoBuilder().construir());
                estabelecimentos.add(new EstabelecimentoBuilder().comNome(nome)
                                .comUsuario(new UsuarioBuilder().comEmail(email).construir()).construir());

                estabelecimentoRepository.saveAll(estabelecimentos);

                this.mockMvc
                                .perform(get("/estabelecimento"))
                                .andExpect(status().isOk())
                                .andExpect(content().string(containsString(nome)));
        }

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

        @Test
        public void deve_incluir_um_estabelecimento() throws Exception {

                Usuario usuario = new UsuarioBuilder().construir();

                usuarioRepository.save(usuario);

                EstabelecimentoRequestDTO estabelecimentoDTO = new EstabelecimentoRequestDTO("Teste", "67991333993",
                                "8 as 9",
                                2024, "cg", "rua teste", "ms", "67.596.818/0001-90", usuario.getId());

                mockMvc.perform(post("/estabelecimento")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(estabelecimentoDTO)))
                                .andExpect(status().isCreated())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.nome").value("Teste"))
                                .andExpect(jsonPath("$.telefone").value("67991333993"))
                                .andExpect(jsonPath("$.horarioAtendimento").value("8 as 9"))
                                .andExpect(jsonPath("$.numero").value(2024))
                                .andExpect(jsonPath("$.cidade").value("cg"))
                                .andExpect(jsonPath("$.logradouro").value("rua teste"))
                                .andExpect(jsonPath("$.estado").value("ms"))
                                .andExpect(jsonPath("$.cnpj").value("67.596.818/0001-90"))
                                .andExpect(jsonPath("$.idUsuario").value(usuario.getId()))
                                .andDo(result -> {
                                        String jsonResponse = result.getResponse().getContentAsString();
                                        EstabelecimentoResponseDTO responseDTO = objectMapper.readValue(jsonResponse,
                                                        EstabelecimentoResponseDTO.class);
                                        assertThat(responseDTO.getNome()).isEqualTo("Teste");
                                        assertThat(responseDTO.getTelefone()).isEqualTo("67991333993");
                                        assertThat(responseDTO.getHorarioAtendimento()).isEqualTo("8 as 9");
                                        assertThat(responseDTO.getNumero()).isEqualTo(2024);
                                        assertThat(responseDTO.getCidade()).isEqualTo("cg");
                                        assertThat(responseDTO.getLogradouro()).isEqualTo("rua teste");
                                        assertThat(responseDTO.getEstado()).isEqualTo("ms");
                                        assertThat(responseDTO.getCnpj()).isEqualTo("67.596.818/0001-90");
                                        assertThat(responseDTO.getIdUsuario()).isEqualTo(usuario.getId());
                                });
        }

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
