package br.com.prime.prime.controllers;

import br.com.prime.prime.Services.EstabelecimentoService;
import br.com.prime.prime.dominio.estabelecimento.*;
import br.com.prime.prime.repository.EstabelecimentoRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "/estabelecimento")
public class EstabelecimentoController {

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    private EstabelecimentoService estabelecimentoService;

    @Operation(summary = "Busca todos estabelecimentos cadastrados")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EstabelecimentoListagem>> buscarTodos() {
        Iterable<Estabelecimento> iterable = estabelecimentoRepository.findAll();
        List<Estabelecimento> estabelecimentos = new ArrayList<>();
        iterable.forEach(estabelecimentos::add);
        List<EstabelecimentoListagem> listagemRecords = estabelecimentos.stream()
                .map(estabelecimento -> new EstabelecimentoListagem(
                        estabelecimento.getId(),
                        estabelecimento.getNome(),
                        estabelecimento.getCidade(),
                        estabelecimento.getEstado(),
                        estabelecimento.getCnpj()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(listagemRecords);
    }

    @Operation(summary = "Busca estabelecimento por Id")
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<> buscarPorId(@PathVariable Long id) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Estabelecimento não encontrado"));


        return ResponseEntity.ok().body();
    }

    @DeleteMapping(path = "/{id}")
    public void remover(@PathVariable Long id) {
        estabelecimentoRepository.deleteById(id);
    }

    @Operation(summary = "Cadastrar um novo estabelecimento")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EstabelecimentoDTO> cadastrar(
            @RequestBody @Valid EstabelecimentoCriacao estabelecimentoCriacao) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(estabelecimentoService.criar(estabelecimentoCriacao));
    }

    @Operation(summary = "Altera dados do estabelecimento")
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EstabelecimentoDTO> alterar(@PathVariable Long id,
                                                      @RequestBody EstabelecimentoAtualizacao estabelecimentoAtualizacao) {
        return ResponseEntity.ok(estabelecimentoService.editar(estabelecimentoAtualizacao, id));
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