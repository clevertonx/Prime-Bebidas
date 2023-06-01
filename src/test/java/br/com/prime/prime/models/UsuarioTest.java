package br.com.prime.prime.models;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import br.com.prime.prime.Builders.UsuarioBuilder;



public class UsuarioTest {
    @Test
    public void deve_criar_um_usuario()
            {

        String email = "teste@gmail.com";
        String senha = "senha123";

        Usuario usuario = new UsuarioBuilder().construir();

       assertThat(usuario.getEmail()).isEqualTo(email);
       assertThat(usuario.getSenha()).isEqualTo(senha);
    }
}
