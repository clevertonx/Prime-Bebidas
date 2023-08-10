package br.com.prime.prime.Builders;

import br.com.prime.prime.dominio.Usuario;

public class UsuarioBuilder {
    String email = "test@gmail.com";
    String senha = "senha123";

    public UsuarioBuilder() {

    }

    public Usuario construir() {
        return new Usuario(email, senha);
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
