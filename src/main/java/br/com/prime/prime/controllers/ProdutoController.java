package br.com.prime.prime.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.prime.prime.Services.ProdutoService;
import br.com.prime.prime.dto.ProdutoRequestDTO;
import br.com.prime.prime.dto.ProdutoResponseDTO;
import br.com.prime.prime.models.PrecoInvalidoException;
import br.com.prime.prime.models.Produto;
import br.com.prime.prime.repository.ProdutoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@CrossOrigin("*")
@RequestMapping(path = { "/produto" }, produces = { "application/json" })

public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoService produtoService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Produto>> buscarTodos() {
        Iterable<Produto> iterable = produtoRepository.findAll();
        List<Produto> produtos = new ArrayList<>();
        iterable.forEach(produtos::add);
        return ResponseEntity.ok().body(produtos);
    }

    @GetMapping(path = "/buscarPorNome", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProdutoResponseDTO>> buscarPorNome(
            @RequestParam(required = false, name = "nome") String nome) {
        return ResponseEntity.ok(produtoService.buscarPorNome(nome));
    }

    @Operation(summary = "Deletar um Produto pelo seu id")
    @ApiResponse(responseCode = "204")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> removerProdutoId(@PathVariable Long id) {
        produtoService.removerPorId(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Cadastrar um novo produto")
    @ApiResponse(responseCode = "201")
    @PostMapping(consumes = { "application/json" })
    public ResponseEntity<ProdutoResponseDTO> cadastrarProduto(
            @RequestBody ProdutoRequestDTO novoProduto) throws PrecoInvalidoException {
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.criar(novoProduto));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Produto> alterar(@RequestBody Produto produto) {
        Produto produtoAlterado = produtoRepository.save(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoAlterado);
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
