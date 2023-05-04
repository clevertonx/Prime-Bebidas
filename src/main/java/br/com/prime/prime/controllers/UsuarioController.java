package br.com.prime.prime.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.prime.prime.Services.UsuarioService;
import br.com.prime.prime.dto.LoginDTO;
import br.com.prime.prime.dto.LoginRequestDTO;
import br.com.prime.prime.dto.UsuarioPutDTO;
import br.com.prime.prime.dto.UsuarioRequestDTO;
import br.com.prime.prime.dto.UsuarioResponseDTO;
import br.com.prime.prime.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

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

    @Operation(summary = "Cadastrar um novo usuario")
    @ApiResponse(responseCode = "201")
    @PostMapping(consumes = { "application/json" })
    public ResponseEntity<UsuarioResponseDTO> cadastrarUsuario(
            @RequestBody @Valid UsuarioRequestDTO novoUsuario) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.criar(novoUsuario));
    }

    @Operation(summary = "Editar o usuario")
    @ApiResponse(responseCode = "200")
    @PutMapping(path = "/{id}", consumes = "application/json")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(@RequestBody @Valid UsuarioPutDTO usuarioPutDTO,
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
}
