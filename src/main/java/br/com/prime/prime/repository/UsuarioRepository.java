package br.com.prime.prime.repository;

import br.com.prime.prime.models.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
    List<Usuario> findByEmailContainingIgnoreCase(String email);

    Usuario findByEmail(String email);

}
