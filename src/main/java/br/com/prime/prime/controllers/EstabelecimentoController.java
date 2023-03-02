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

import br.com.prime.prime.dto.EstabelecimentoRequestDTO;
import br.com.prime.prime.models.Estabelecimento;
import br.com.prime.prime.repository.EstabelecimentoRepository;

@RestController
@RequestMapping(path = "/estabelecimento")
public class EstabelecimentoController {

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Estabelecimento>> buscarTodos() {
        Iterable<Estabelecimento> iterable = estabelecimentoRepository.findAll();
        List<Estabelecimento> estabelecimentos = new ArrayList<>();
        iterable.forEach(estabelecimentos::add);
        return ResponseEntity.ok().body(estabelecimentos);
    }

    @DeleteMapping(path = "/{id}")
    public void remover(@PathVariable Long id) {
        estabelecimentoRepository.deleteById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EstabelecimentoRequestDTO> cadastrar(@RequestBody EstabelecimentoRequestDTO estabelecimentoDto) {
        estabelecimentoRepository.save(estabelecimentoDto.toEstabelecimento());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Estabelecimento> alterar(@RequestBody Estabelecimento estabelecimento) {
        Estabelecimento estabelecimentoAlterado = estabelecimentoRepository.save(estabelecimento);
        return ResponseEntity.status(HttpStatus.CREATED).body(estabelecimentoAlterado);
    }

}
