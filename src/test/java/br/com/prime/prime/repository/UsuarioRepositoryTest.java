package br.com.prime.prime.repository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.prime.prime.Builders.UsuarioBuilder;
import br.com.prime.prime.models.Usuario;

@DataJpaTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void deve_buscar_usuario_pelo_id() throws Exception {

        String email = "teste@gmail.com";
        String senha = "senha123";
        Usuario usuario = new Usuario(email, senha);
        usuarioRepository.save(usuario);

        Usuario usuarioRetornado = usuarioRepository.findById(usuario.getId()).orElse(null);

        assertThat(usuarioRetornado).isNotNull();
        assertThat(usuarioRetornado.getId()).isEqualTo(usuario.getId());
        assertThat(usuarioRetornado.getEmail()).isEqualTo(email);
        assertThat(usuarioRetornado.getSenha()).isEqualTo(senha);
    }

    @Test
    public void deve_salvar_um_usuario() {

        Usuario usuario = new UsuarioBuilder().construir();

        usuarioRepository.save(usuario);

        Assertions.assertNotNull(usuario.getId());
    }

    @Test
    public void deve_remover_usuario() {
        Usuario usuario = new UsuarioBuilder().construir();
        usuarioRepository.save(usuario);

        usuarioRepository.deleteById(usuario.getId());

        Optional<Usuario> usuarioBuscado = usuarioRepository.findById(usuario.getId());

        Assertions.assertFalse(usuarioBuscado.isPresent());
    }

    @Test
    public void deve_buscar_usuario_pelo_email() {
        String email = "teste@gmail.com";
        Usuario usuario = new UsuarioBuilder().comEmail(email).construir();
        usuarioRepository.save(usuario);

        List<Usuario> usuarioRetornado = usuarioRepository.findByEmailContainingIgnoreCase(email);

        Assertions.assertTrue(usuarioRetornado.contains(usuario));
    }

}
