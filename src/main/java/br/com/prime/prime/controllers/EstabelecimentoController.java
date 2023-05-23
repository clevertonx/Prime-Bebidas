package br.com.prime.prime.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<EstabelecimentoResponseDTO>> buscarTodos() {
        Iterable<Estabelecimento> iterable = estabelecimentoRepository.findAll();
        List<Estabelecimento> estabelecimentos = new ArrayList<>();
        iterable.forEach(estabelecimentos::add);
        return ResponseEntity.ok()
                .body(estabelecimentoMapper.estabelecimentosParaEstabelecimentoResponses(estabelecimentos));
    }

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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EstabelecimentoResponseDTO> cadastrar(
            @RequestBody @Valid EstabelecimentoRequestDTO estabelecimentoDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(estabelecimentoService.criar(estabelecimentoDto));
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EstabelecimentoResponseDTO> alterar(@PathVariable Long id,
            @RequestBody EstabelecimentoRequestDTO request) {
        Optional<Estabelecimento> optionalEstabelecimento = estabelecimentoRepository.findById(id);

        Estabelecimento estabelecimentoExistente = optionalEstabelecimento.get();

        estabelecimentoExistente.setNome(request.getNome());
        estabelecimentoExistente.setTelefone(request.getTelefone());
        estabelecimentoExistente.setHorarioAtendimento(request.getHorarioAtendimento());
        estabelecimentoExistente.setNumero(request.getNumero());
        estabelecimentoExistente.setCidade(request.getCidade());
        estabelecimentoExistente.setLogradouro(request.getLogradouro());
        estabelecimentoExistente.setEstado(request.getEstado());
        estabelecimentoExistente.setCnpj(request.getCnpj());

        Estabelecimento estabelecimentoAlterado = estabelecimentoRepository.save(estabelecimentoExistente);

        EstabelecimentoResponseDTO response = new EstabelecimentoResponseDTO();
        response.setId(estabelecimentoAlterado.getId());
        response.setNome(estabelecimentoAlterado.getNome());
        response.setTelefone(estabelecimentoAlterado.getTelefone());
        response.setHorarioAtendimento(estabelecimentoAlterado.getHorarioAtendimento());
        response.setNumero(estabelecimentoAlterado.getNumero());
        response.setCidade(estabelecimentoAlterado.getCidade());
        response.setLogradouro(estabelecimentoAlterado.getLogradouro());
        response.setEstado(estabelecimentoAlterado.getEstado());
        response.setCnpj(estabelecimentoAlterado.getCnpj());

        return ResponseEntity.ok(response);
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
