package br.com.prime.prime.Builders;

import br.com.prime.prime.models.Usuario;

public class UsuarioBuilder {
    String email = "teste@gmail.com";
    String senha = "senha123";

    public UsuarioBuilder() {

    }

    public Usuario construir() {
        Usuario usuario = new Usuario(email, senha);
        return usuario;
    }

    public UsuarioBuilder comEmail(String email) {
        this.email = email;
        return this;
    }

    public UsuarioBuilder comSenha(String senha) {
        this.senha = senha;
        return this;
    }

}
