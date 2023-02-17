package br.com.prime.prime.Builders;

import br.com.prime.prime.models.Usuario;

public class UsuarioBuilder {
    String email = "clevertonx@gmail.com";
    String cnpj = "67.596.818/0001-90";
    String senha = "senha123";

    public UsuarioBuilder() {

    }

    public Usuario construir() {
        return new Usuario(email, cnpj, senha);
    }

    public UsuarioBuilder comEmail(String email) {
        this.email = email;
        return this;
    }

    public UsuarioBuilder comCnpj(String cnpj) {
        this.cnpj = cnpj;
        return this;
    }

    public UsuarioBuilder comSenha(String senha) {
        this.senha = senha;
        return this;
    }

}
