package br.com.prime.prime.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.prime.prime.models.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
    public List<Usuario> findByEmailContainingIgnoreCase(String email);

    public Usuario saveAndFlush(Usuario usuario);

    public Usuario findByEmailIgnoreCase(String email);
}
