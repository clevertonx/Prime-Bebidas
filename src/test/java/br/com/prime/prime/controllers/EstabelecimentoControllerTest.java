package br.com.prime.prime.controllers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

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
import br.com.prime.prime.models.Estabelecimento;
import br.com.prime.prime.repository.EstabelecimentoRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class EstabelecimentoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

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
        estabelecimentos.add(new EstabelecimentoBuilder().comNome(nome).comUsuario(new UsuarioBuilder().comEmail(email).construir()).construir());

        estabelecimentoRepository.saveAll(estabelecimentos);

        this.mockMvc
                .perform(get("/estabelecimento"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(nome)));
    }

    @Test
    public void deve_buscar_os_estabelecimentos_cadastrados_pela_cidade() throws Exception {
        String cidade = "Corumba";
        ArrayList<Estabelecimento> estabelecimentos = new ArrayList<Estabelecimento>();
        estabelecimentos.add(new EstabelecimentoBuilder().construir());
        estabelecimentos.add(new EstabelecimentoBuilder().comCidade(cidade).construir());

        estabelecimentoRepository.saveAll(estabelecimentos);

        this.mockMvc
                .perform(get("/estabelecimento"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(cidade)));
    }

    @Test
    public void deve_remover_um_estabelecimento_pelo_id() throws Exception {
        String nome = "Assai";
        ArrayList<Estabelecimento> estabelecimentos = new ArrayList<Estabelecimento>();
        estabelecimentos.add(new EstabelecimentoBuilder().construir());
        estabelecimentos.add(new EstabelecimentoBuilder().comNome(nome).construir());

        estabelecimentoRepository.saveAll(estabelecimentos);

        Estabelecimento estabelecimento1 = estabelecimentos.get(1);

        this.mockMvc
                .perform(delete("/estabelecimento/" + estabelecimento1.getId()))
                .andExpect(status().isOk());

        List<Estabelecimento> estabelecimentoRetornados = estabelecimentoRepository
                .findByNomeContainingIgnoreCase(estabelecimento1.getNome());

        Assertions.assertThat(estabelecimentoRetornados).isEmpty();
    }

    @Test
    public void deve_incluir_um_estabelecimento() throws Exception {
        Estabelecimento estabelecimento = new EstabelecimentoBuilder().construir();
        String json = toJSON(estabelecimento);
        this.mockMvc
                .perform(post("/estabelecimento").content(json).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());

        List<Estabelecimento> estabelecimentoRetornados = estabelecimentoRepository
                .findByNomeContainingIgnoreCase(estabelecimento.getNome());

        Assertions.assertThat(estabelecimentoRetornados.size()).isEqualTo(1);
        Assertions.assertThat(
                estabelecimento.getNome())
                .isIn(estabelecimentoRetornados.stream().map(Estabelecimento::getNome).toList());
    }

    private String toJSON(Estabelecimento estabelecimento) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(estabelecimento);
        return json;
    }

    @Test
    public void deve_alterar_dados_do_estabelecimento() throws Exception {
        Estabelecimento nomeAlterado = new EstabelecimentoBuilder().construir();
        estabelecimentoRepository.save(nomeAlterado);

        nomeAlterado.setNome("Pires");

        String json = toJSON(nomeAlterado);

        this.mockMvc.perform(put("/estabelecimento").content(json).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());

        Estabelecimento estabelecimentoRetornado = estabelecimentoRepository.findById(nomeAlterado.getId()).get();
        Assertions.assertThat(estabelecimentoRetornado.getId()).isEqualTo(nomeAlterado.getId());
        Assertions.assertThat(estabelecimentoRetornado.getNome()).isEqualTo(nomeAlterado.getNome());
    }
}
