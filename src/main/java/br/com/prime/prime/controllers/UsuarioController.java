package br.com.prime.prime.controllers;

import br.com.prime.prime.Services.UsuarioService;
import br.com.prime.prime.dto.EstabelecimentoUsuarioResponseDTO;
import br.com.prime.prime.dto.UsuarioPutDTO;
import br.com.prime.prime.dto.UsuarioRequestDTO;
import br.com.prime.prime.dto.UsuarioResponseDTO;
import br.com.prime.prime.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Operation(summary = "Lista todos os usuarios")
    @ApiResponse(responseCode = "200")
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> buscarTodos() {
        return ResponseEntity.ok(usuarioService.buscarTodos());
    }

    @DeleteMapping(path = "/{id}")
    public void remover(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
    }

    @Operation(summary = "Editar o usuario")
    @ApiResponse(responseCode = "200")
    @PutMapping(path = "/{id}", consumes = "application/json")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(@RequestBody UsuarioPutDTO usuarioPutDTO,
            @PathVariable Long id) {

        return ResponseEntity.ok(usuarioService.alterar(usuarioPutDTO, id));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }

    @Operation(summary = "Busca estabelecimentos do Usuário")
    @GetMapping(path = "/{idUsuario}/estabelecimento")
    public ResponseEntity<Collection<EstabelecimentoUsuarioResponseDTO>> buscarEstabelecimento(
            @PathVariable Long idUsuario) {
        return ResponseEntity.ok(usuarioService.estabelecimentoPorUsuario(idUsuario));
    }

}
