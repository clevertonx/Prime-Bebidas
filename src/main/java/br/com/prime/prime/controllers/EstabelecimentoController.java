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

import br.com.prime.prime.Services.EstabelecimentoService;
import br.com.prime.prime.dto.EstabelecimentoPutDTO;
import br.com.prime.prime.dto.EstabelecimentoRequestDTO;
import br.com.prime.prime.dto.EstabelecimentoResponseDTO;
import br.com.prime.prime.repository.EstabelecimentoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/estabelecimento")
public class EstabelecimentoController {

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    private EstabelecimentoService estabelecimentoService;

    @Operation(summary = "Lista todos os estabelecimentos")
    @ApiResponse(responseCode = "200")
    @GetMapping
    public ResponseEntity<List<EstabelecimentoResponseDTO>> buscarTodos() {
        return ResponseEntity.ok(estabelecimentoService.buscarTodos());
    }

    @DeleteMapping(path = "/{id}")
    public void remover(@PathVariable Long id) {
        estabelecimentoRepository.deleteById(id);
    }

    @Operation(summary = "Cadastrar um novo estabelecimento")
    @ApiResponse(responseCode = "201")
    @PostMapping(consumes = { "application/json" })
    public ResponseEntity<EstabelecimentoResponseDTO> cadastrarEstabelecimento(
            @RequestBody @Valid EstabelecimentoRequestDTO novoEstabelecimento) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(estabelecimentoService.criar(novoEstabelecimento));
    }

    @Operation(summary = "Editar o estabelecimento")
    @ApiResponse(responseCode = "200")
    @PutMapping(path = "/{id}", consumes = "application/json")
    public ResponseEntity<EstabelecimentoResponseDTO> atualizarEstabelecimento(
            @RequestBody @Valid EstabelecimentoPutDTO estabelecimentoPutDTO,
            @PathVariable Long id) {

        return ResponseEntity.ok(estabelecimentoService.alterar(estabelecimentoPutDTO, id));
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
