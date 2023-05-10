package br.com.prime.prime.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.prime.prime.Mappers.EstabelecimentoMapper;
import br.com.prime.prime.dto.EstabelecimentoRequestDTO;
import br.com.prime.prime.dto.EstabelecimentoResponseDTO;
import br.com.prime.prime.models.Estabelecimento;
import br.com.prime.prime.repository.EstabelecimentoRepository;

@Service
public class EstabelecimentoService {
    @Autowired
    EstabelecimentoMapper estabelecimentoMapper;

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    public EstabelecimentoResponseDTO criar(EstabelecimentoRequestDTO estabelecimentoRequestDTO) {
        Estabelecimento estabelecimento = estabelecimentoMapper.estabelecimentoRequestParaEstabelecimento(estabelecimentoRequestDTO);
        estabelecimentoRepository.save(estabelecimento);
        return estabelecimentoMapper.estabelecimentoParaEstabelecimentoResponse(estabelecimento);
    }
}
