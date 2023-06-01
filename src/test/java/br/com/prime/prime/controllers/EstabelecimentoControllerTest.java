package br.com.prime.prime.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.assertj.core.api.Assertions;
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
import com.fasterxml.jackson.databind.ObjectWriter;

import br.com.prime.prime.Builders.EstabelecimentoBuilder;
import br.com.prime.prime.Builders.UsuarioBuilder;
import br.com.prime.prime.dto.EstabelecimentoRequestDTO;
import br.com.prime.prime.dto.EstabelecimentoResponseDTO;
import br.com.prime.prime.models.Estabelecimento;
import br.com.prime.prime.models.Usuario;
import br.com.prime.prime.repository.EstabelecimentoRepository;
import br.com.prime.prime.repository.UsuarioRepository;

@SpringBootTest
@AutoConfigureMockMvc
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
        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimentoRepository.save(estabelecimento);

        long estabelecimentoId = estabelecimento.getId();

        mockMvc.perform(delete("/estabelecimento/{id}", estabelecimentoId))
                .andExpect(status().isOk());
    }

    @Test
    public void deve_incluir_um_estabelecimento() throws Exception {

        Usuario usuario = new Usuario();
        usuario.setId(1L);

        usuarioRepository.save(usuario);

        EstabelecimentoRequestDTO estabelecimentoDTO = new EstabelecimentoRequestDTO("Teste", "67991333993", "8 as 9",
                2024, "cg", "rua teste", "ms", "67.596.818/0001-90", 1L);

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
                .andExpect(jsonPath("$.idUsuario").value(1L))
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
                    assertThat(responseDTO.getIdUsuario()).isEqualTo(1L);
                });
    }

    private String toJSON(Estabelecimento estabelecimento) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(estabelecimento);
        return json;
    }

    @Test
    public void deve_alterar_dados_do_estabelecimento() throws Exception {

        Usuario usuario = new Usuario("test@gmail.com", "senha");

        usuarioRepository.save(usuario);
        
        Estabelecimento estabelecimento = new Estabelecimento("Teste", "67991333993", "8 as 9",
                2024, "cg", "rua teste", "ms", "67.596.818/0001-90", usuario);

        estabelecimentoRepository.save(estabelecimento);

        long estabelecimentoId = estabelecimento.getId();
        EstabelecimentoRequestDTO estabelecimentoDTO = new EstabelecimentoRequestDTO("Teste12", "67991333984",
                "5 as 15",
                218, "Douradina", "rua do Faz o LONG", "MT", "67.596.818/0001-90", null);

        mockMvc.perform(put("/estabelecimento/{id}", estabelecimentoId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(estabelecimentoDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nome").value("Teste12"))
                .andExpect(jsonPath("$.telefone").value("67991333984"))
                .andExpect(jsonPath("$.horarioAtendimento").value("5 as 15"))
                .andExpect(jsonPath("$.numero").value(218))
                .andExpect(jsonPath("$.cidade").value("Douradina"))
                .andExpect(jsonPath("$.logradouro").value("rua do Faz o LONG"))
                .andExpect(jsonPath("$.estado").value("MT"))
                .andExpect(jsonPath("$.cnpj").value("67.596.818/0001-90"))
                .andExpect(jsonPath("$.idUsuario").value(null))
                .andDo(result -> {
                    String responseBody = result.getResponse().getContentAsString();
                    EstabelecimentoResponseDTO estabelecimentoResponseDTO = objectMapper.readValue(responseBody,
                            EstabelecimentoResponseDTO.class);
                    assertThat(estabelecimentoResponseDTO.getNome()).isEqualTo("Teste12");
                    assertThat(estabelecimentoResponseDTO.getTelefone()).isEqualTo("67991333984");
                    assertThat(estabelecimentoResponseDTO.getHorarioAtendimento()).isEqualTo("5 as 15");
                    assertThat(estabelecimentoResponseDTO.getNumero()).isEqualTo(218);
                    assertThat(estabelecimentoResponseDTO.getCidade()).isEqualTo("Douradina");
                    assertThat(estabelecimentoResponseDTO.getLogradouro()).isEqualTo("rua do Faz o LONG");
                    assertThat(estabelecimentoResponseDTO.getEstado()).isEqualTo("MT");
                    assertThat(estabelecimentoResponseDTO.getCnpj()).isEqualTo("67.596.818/0001-90");
                    assertThat(estabelecimentoResponseDTO.getIdUsuario()).isEqualTo(null);
                });
    }

    private String asJsonString(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}
