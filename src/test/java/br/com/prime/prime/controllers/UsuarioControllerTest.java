package br.com.prime.prime.controllers;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.ArrayList;
import java.util.List;

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

import br.com.prime.prime.Builders.UsuarioBuilder;
import br.com.prime.prime.models.Usuario;
import br.com.prime.prime.repository.UsuarioRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    @AfterEach
    public void deletaDados() {
        usuarioRepository.deleteAll();
    }

    @Test
    public void deve_buscar_os_usuarios_cadastrados() throws Exception {
        String email1 = "cleverton@gmail.com";
        String email2 = "joao@exemplo.com";
        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
        usuarios.add(new UsuarioBuilder().comEmail(email1).construir());
        usuarios.add(new UsuarioBuilder().comEmail(email2).construir());

        usuarioRepository.saveAll(usuarios);

        this.mockMvc
                .perform(get("/usuario"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(email1)))
                .andExpect(content().string(containsString(email2)));
    }

    

    @Test
    public void deve_remover_um_usuario_pelo_id() throws Exception {
        String email = "gustavopintomurcho@gmail.com";
        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
        usuarios.add(new UsuarioBuilder().construir());
        usuarios.add(new UsuarioBuilder().comEmail(email).construir());

        usuarioRepository.saveAll(usuarios);

        Usuario usuario1 = usuarios.get(1);

        this.mockMvc
                .perform(delete("/usuario/" + usuario1.getId()))
                .andExpect(status().isOk());

        List<Usuario> usuarioRetornados = usuarioRepository.findByEmailContainingIgnoreCase(usuario1.getEmail());

        Assertions.assertThat(usuarioRetornados).isEmpty();
    }

    @Test
    public void deve_incluir_um_usuario() throws Exception {
        Usuario usuario = new UsuarioBuilder().construir();
        String json = toJSON(usuario);
        this.mockMvc
                .perform(post("/usuario").content(json).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());

        List<Usuario> usuarioRetornados = usuarioRepository.findByEmailContainingIgnoreCase(usuario.getEmail());

        Assertions.assertThat(usuarioRetornados.size()).isEqualTo(1);
        Assertions.assertThat(
                usuario.getEmail()).isIn(usuarioRetornados.stream().map(Usuario::getEmail).toList());
    }

    private String toJSON(Usuario usuario) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(usuario);
        return json;
    }

    @Test
    public void deve_alterar_email_do_usuario() throws Exception {
        Usuario emailAlterado = new UsuarioBuilder().construir();
        usuarioRepository.save(emailAlterado);

        emailAlterado.setEmail("Gustavodurn@gmail.com");

        String json = toJSON(emailAlterado);

        this.mockMvc.perform(put("/usuario").content(json).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());

        Usuario usuarioRetornado = usuarioRepository.findById(emailAlterado.getId()).get();
        Assertions.assertThat(usuarioRetornado.getId()).isEqualTo(emailAlterado.getId());
        Assertions.assertThat(usuarioRetornado.getEmail()).isEqualTo(emailAlterado.getEmail());
    }

    @Test
    public void deve_alterar_senha_do_usuario() throws Exception {
        Usuario senhaAlterada = new UsuarioBuilder().construir();
        usuarioRepository.save(senhaAlterada);

        senhaAlterada.setSenha("tururu");

        String json = toJSON(senhaAlterada);

        this.mockMvc.perform(put("/usuario").content(json).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());

        Usuario usuarioRetornado = usuarioRepository.findById(senhaAlterada.getId()).get();
        Assertions.assertThat(usuarioRetornado.getId()).isEqualTo(senhaAlterada.getId());
        Assertions.assertThat(usuarioRetornado.getSenha()).isEqualTo(senhaAlterada.getSenha());
    }

}
