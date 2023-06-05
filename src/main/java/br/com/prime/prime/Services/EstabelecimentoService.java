package br.com.prime.prime.Services;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.prime.prime.Mappers.EstabelecimentoMapper;
import br.com.prime.prime.Mappers.ProdutoMapper;
import br.com.prime.prime.dto.EstabelecimentoRequestDTO;
import br.com.prime.prime.dto.EstabelecimentoResponseDTO;
import br.com.prime.prime.models.Estabelecimento;
import br.com.prime.prime.repository.EstabelecimentoRepository;
import br.com.prime.prime.repository.UsuarioRepository;

@Service
public class EstabelecimentoService {
    @Autowired
    EstabelecimentoMapper estabelecimentoMapper;

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ProdutoMapper produtoMapper;

    public EstabelecimentoResponseDTO criar(EstabelecimentoRequestDTO estabelecimentoRequestDTO) {
        Estabelecimento estabelecimento = estabelecimentoMapper
                .estabelecimentoRequestParaEstabelecimento(estabelecimentoRequestDTO);
        estabelecimentoRepository.save(estabelecimento);
        return estabelecimentoMapper.estabelecimentoParaEstabelecimentoResponse(estabelecimento);
    }

    public EstabelecimentoResponseDTO editar(EstabelecimentoRequestDTO estabelecimentoRequestDTO, Long id) {

        Optional<Estabelecimento> estabelecimentoOptional = estabelecimentoRepository.findById(id);
        if (estabelecimentoOptional.isEmpty()) {
            throw new NoSuchElementException();
        }
        Estabelecimento estabelecimento = estabelecimentoOptional.get();
        estabelecimento.setNome(estabelecimentoRequestDTO.getNome());
        estabelecimento.setTelefone(estabelecimentoRequestDTO.getTelefone());
        estabelecimento.setHorarioAtendimento(estabelecimentoRequestDTO.getHorarioAtendimento());
        estabelecimento.setNumero(estabelecimentoRequestDTO.getNumero());
        estabelecimento.setCidade(estabelecimentoRequestDTO.getCidade());
        estabelecimento.setLogradouro(estabelecimentoRequestDTO.getLogradouro());
        estabelecimento.setEstado(estabelecimentoRequestDTO.getEstado());
        estabelecimento.setCnpj(estabelecimentoRequestDTO.getCnpj());
        estabelecimentoRepository.save(estabelecimento);

        return estabelecimentoMapper.estabelecimentoParaEstabelecimentoResponse(estabelecimento);
    }

  

}
