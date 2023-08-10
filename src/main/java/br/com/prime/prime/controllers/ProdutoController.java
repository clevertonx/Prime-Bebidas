package br.com.prime.prime.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

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

import br.com.prime.prime.Mappers.ProdutoMapper;
import br.com.prime.prime.Services.ProdutoService;
import br.com.prime.prime.dto.ProdutoEstabelecimentoUsuarioResponseDTO;
import br.com.prime.prime.dto.ProdutoRequestDTO;
import br.com.prime.prime.dto.ProdutoResponseDTO;
import br.com.prime.prime.dominio.Categoria;
import br.com.prime.prime.dominio.PrecoInvalidoException;
import br.com.prime.prime.dominio.Produto;
import br.com.prime.prime.repository.ProdutoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping(path = { "/produto" }, produces = { "application/json" })

public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoMapper produtoMapper;

    @Autowired
    private ProdutoService produtoService;

    @Operation(summary = "Busca todos os produtos cadastrados")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ProdutoResponseDTO>> buscarTodos() {
        Iterable<Produto> iterable = produtoRepository.findAll();
        List<Produto> produtos = new ArrayList<>();
        iterable.forEach(produtos::add);
        return ResponseEntity.ok()
                .body(produtoMapper.produtosParaProdutoResponses(produtos));
    }
    @Operation(summary = "Busca produto por Id")
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProdutoResponseDTO> buscarPorId(@PathVariable Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

        ProdutoResponseDTO produtoResponseDTO = produtoMapper
                .produtoParaProdutoResponse(produto);
        return ResponseEntity.ok().body(produtoResponseDTO);
    }
    @Operation(summary = "Busca produtos por categoria")
    @GetMapping("/categoria")
    public ResponseEntity<List<ProdutoResponseDTO>> buscarPorCategoria(@RequestParam Categoria categoria) {
        List<ProdutoResponseDTO> produtos = produtoService.buscarPorCategoria(categoria);
        return ResponseEntity.ok(produtos);
    }
    @Operation(summary = "Busca produtos por nome")
    @GetMapping(path = "/buscarPorNome", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProdutoResponseDTO>> buscarPorNome(
            @RequestParam(required = false, name = "nome") String nome) {
        return ResponseEntity.ok(produtoService.buscarPorNome(nome));
    }

    @Operation(summary = "Deletar um Produto pelo seu id")
    @ApiResponse(responseCode = "204")
    @DeleteMapping(path = "/{id}")
    public void remover(@PathVariable Long id) {
        produtoRepository.deleteById(id);
    }

    @Operation(summary = "Cadastrar um novo produto")
    @ApiResponse(responseCode = "201")
    @PostMapping(consumes = { "application/json" })
    public ResponseEntity<ProdutoResponseDTO> cadastrar(
            @RequestBody @Valid ProdutoRequestDTO produtoDto) throws PrecoInvalidoException {
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.criar(produtoDto));
    }
    @Operation(summary = "Altera dados do produto")
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProdutoResponseDTO> alterar(@PathVariable Long id,
            @RequestBody ProdutoRequestDTO produtoRequestDTO) {
        return ResponseEntity.ok(produtoService.editar(produtoRequestDTO, id));
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

    @Operation(summary = "Busca produtos do Estabelecimento")
    @GetMapping(path = "/usuario/{idUsuario}/estabelecimento/{idEstabelecimento}/produto")
    public ResponseEntity<Collection<ProdutoEstabelecimentoUsuarioResponseDTO>> getProdutosPorEstabelecimentoUsuario(
            @PathVariable Long idUsuario, @PathVariable Long idEstabelecimento) {
        try {
            Collection<ProdutoEstabelecimentoUsuarioResponseDTO> produtos = produtoService
                    .produtoPorEstabelecimentoUsuario(idUsuario, idEstabelecimento);
            return ResponseEntity.ok(produtos);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Busca produtos do Usuário")
    @GetMapping(path = "/usuario/{idUsuario}")
    public ResponseEntity<Collection<ProdutoEstabelecimentoUsuarioResponseDTO>> buscarProduto(
            @PathVariable Long idUsuario) {
        return ResponseEntity.ok(produtoService.produtoPorUsuario(idUsuario));
    }
}