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

import br.com.prime.prime.dto.ProdutoRequestDTO;
import br.com.prime.prime.dto.ProdutoResponseDTO;
import br.com.prime.prime.models.Produto;
import br.com.prime.prime.repository.ProdutoRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/produto")

public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Produto>> buscarTodos() {
        Iterable<Produto> iterable = produtoRepository.findAll();
        List<Produto> produtos = new ArrayList<>();
        iterable.forEach(produtos::add);
        return ResponseEntity.ok().body(produtos);
    }

    @DeleteMapping(path = "/{id}")
    public void remover(@PathVariable Long id) {
        produtoRepository.deleteById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProdutoResponseDTO> cadastrar(@RequestBody @Valid ProdutoRequestDTO produto) {

        Produto produtoSalvo = produtoRepository.save(produto.toProduto());
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ProdutoResponseDTO.builder()
                                    .id(produtoSalvo.getId())
                                    .descricao(produtoSalvo.getDescricao())
                                    .imagem(produtoSalvo.getImagem())
                                    .marca(produtoSalvo.getMarca())
                                    .nome(produtoSalvo.getNome())
                                    .preço(produtoSalvo.getPreço())
                                    .build());
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
