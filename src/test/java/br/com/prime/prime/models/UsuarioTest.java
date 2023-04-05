package br.com.prime.prime.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.prime.prime.Builders.UsuarioBuilder;

public class UsuarioTest {
    @Test
    public void deve_criar_um_usuario()
            {

        String email = "clevertonx@gmail.com";
        String senha = "senha123";

        Usuario usuario = new UsuarioBuilder().construir();

        Assertions.assertEquals(email, usuario.getEmail());
        Assertions.assertEquals(senha, usuario.getSenha());
    }
}
