package br.com.prime.prime.Builders;

import java.util.ArrayList;
import java.util.List;

import br.com.prime.prime.models.Estabelecimento;
import br.com.prime.prime.models.Usuario;

public class UsuarioBuilder {
    String email = "clevertonx@gmail.com";
    String cnpj = "67.596.818/0001-90";
    String senha = "senha123";
    private List<Estabelecimento> estabelecimentos = new ArrayList<>();


    public UsuarioBuilder() {

    }

    public Usuario construir() {
        Usuario usuario = new Usuario(email, cnpj, senha, estabelecimentos);
        usuario.setEstabelecimentos(estabelecimentos);
        return usuario;
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

    public UsuarioBuilder comEstabelecimento(Estabelecimento estabelecimento) {
        this.estabelecimentos.add(estabelecimento);
        return this;
    }
}
