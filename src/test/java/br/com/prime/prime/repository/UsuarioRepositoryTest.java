package br.com.prime.prime.repository;

import java.util.List;
import java.util.Optional;

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
        String email = "clevertonx@gmail.com";
        Usuario usuario = new UsuarioBuilder().comEmail(email).construir();
        usuarioRepository.save(usuario);

        List<Usuario> usuarioRetornado = usuarioRepository.findByEmailContainingIgnoreCase(email);

        Assertions.assertTrue(usuarioRetornado.contains(usuario));
    }

    @Test
    public void deve_buscar_usuario_pelo_cnpj() {
        String cnpj = "67.596.818/0001-90";
        Usuario usuario = new UsuarioBuilder().comCnpj(cnpj).construir();
        usuarioRepository.save(usuario);

        List<Usuario> usuarioRetornado = usuarioRepository.findByCnpjContaining(cnpj);

        Assertions.assertTrue(usuarioRetornado.contains(usuario));
    }

}
