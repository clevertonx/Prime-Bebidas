package br.com.prime.prime.Services;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.prime.prime.Mappers.EstabelecimentoMapper;
import br.com.prime.prime.Mappers.ProdutoMapper;
import br.com.prime.prime.dto.EstabelecimentoRequestDTO;
import br.com.prime.prime.dto.EstabelecimentoResponseDTO;
import br.com.prime.prime.dto.ProdutoEstabelecimentoResponseDTO;
import br.com.prime.prime.dto.ProdutoResponseDTO;
import br.com.prime.prime.models.Estabelecimento;
import br.com.prime.prime.repository.EstabelecimentoRepository;

@Service
public class EstabelecimentoService {
    @Autowired
    EstabelecimentoMapper estabelecimentoMapper;

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

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

    public Collection<ProdutoEstabelecimentoResponseDTO> produtoPorEstabelecimento(Long idEstabelecimento) {
        Optional<Estabelecimento> estabelecimentoOptional = estabelecimentoRepository.findById(idEstabelecimento);
        if (estabelecimentoOptional.isEmpty()) {
            throw new NoSuchElementException();
        }
        Estabelecimento estabelecimento = estabelecimentoOptional.get();
        return produtoMapper.produtosParaProdutosEstabelecimentosResponse(estabelecimento.getProdutos());
    }
}
