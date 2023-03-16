package br.com.prime.prime.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.prime.prime.dto.UsuarioRequestDTO;
import br.com.prime.prime.models.Usuario;
import br.com.prime.prime.repository.UsuarioRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Usuario>> buscarTodos() {
        Iterable<Usuario> iterable = usuarioRepository.findAll();
        List<Usuario> usuarios = new ArrayList<>();
        iterable.forEach(usuarios::add);
        return ResponseEntity.ok().body(usuarios);
    }

    @DeleteMapping(path = "/{id}")
    public void remover(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> cadastrar(@RequestBody @Valid UsuarioRequestDTO usuarioDto) {
        usuarioRepository.save(usuarioDto.toUsuario());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Usuario> alterar(@RequestBody Usuario usuario) {
        Usuario usuarioAlterado = usuarioRepository.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioAlterado);
    }
}
