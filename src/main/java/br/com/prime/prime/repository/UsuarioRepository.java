package br.com.prime.prime.repository;

import br.com.prime.prime.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findByEmailContainingIgnoreCase(String email);

    Usuario findByEmail(String email);


}
