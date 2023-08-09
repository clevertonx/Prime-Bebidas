package br.com.prime.prime.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.prime.prime.dto.ProdutoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import br.com.prime.prime.Mappers.EstabelecimentoMapper;
import br.com.prime.prime.Services.EstabelecimentoService;
import br.com.prime.prime.dto.EstabelecimentoRequestDTO;
import br.com.prime.prime.dto.EstabelecimentoResponseDTO;
import br.com.prime.prime.models.Estabelecimento;
import br.com.prime.prime.repository.EstabelecimentoRepository;
import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "/estabelecimento")
public class EstabelecimentoController {

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    private EstabelecimentoMapper estabelecimentoMapper;

    @Autowired
    private EstabelecimentoService estabelecimentoService;
    @Operation(summary = "Busca todos estabelecimentos cadastrados")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<EstabelecimentoResponseDTO>> buscarTodos() {
        Iterable<Estabelecimento> iterable = estabelecimentoRepository.findAll();
        List<Estabelecimento> estabelecimentos = new ArrayList<>();
        iterable.forEach(estabelecimentos::add);
        return ResponseEntity.ok()
                .body(estabelecimentoMapper.estabelecimentosParaEstabelecimentosResponses(estabelecimentos));
    }

    @Operation(summary = "Busca estabelecimentos por nome")
    @GetMapping(path = "/buscarEstabelecimentoPorNome", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<EstabelecimentoResponseDTO>> buscarEstabelecimentoPorNome(
            @RequestParam(required = false, name = "nome") String nome) {
        return ResponseEntity.ok(estabelecimentoService.buscarEstabelecimentoPorNome(nome));
    }
    @Operation(summary = "Busca estabelecimento por Id")
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EstabelecimentoResponseDTO> buscarPorId(@PathVariable Long id) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Estabelecimento não encontrado"));

        EstabelecimentoResponseDTO estabelecimentoResponseDTO = estabelecimentoMapper
                .estabelecimentoParaEstabelecimentoResponse(estabelecimento);
        return ResponseEntity.ok().body(estabelecimentoResponseDTO);
    }

    @DeleteMapping(path = "/{id}")
    public void remover(@PathVariable Long id) {
        estabelecimentoRepository.deleteById(id);
    }
    @Operation(summary = "Cadastrar um novo estabelecimento")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EstabelecimentoResponseDTO> cadastrar(
            @RequestBody @Valid EstabelecimentoRequestDTO estabelecimentoDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(estabelecimentoService.criar(estabelecimentoDto));
    }
    @Operation(summary = "Altera dados do estabelecimento")
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EstabelecimentoResponseDTO> alterar(@PathVariable Long id,
            @RequestBody EstabelecimentoRequestDTO estabelecimentoRequestDTO) {
        return ResponseEntity.ok(estabelecimentoService.editar(estabelecimentoRequestDTO, id));
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
