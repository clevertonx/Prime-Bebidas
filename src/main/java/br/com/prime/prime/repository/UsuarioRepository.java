package br.com.prime.prime.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.prime.prime.models.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
    public List<Usuario> findByEmailContainingIgnoreCase(String email);

    @Query(value = "select * from usuario where email = :email and senha = :senha", nativeQuery =  true)
    public Usuario login(String email, String senha);

}
