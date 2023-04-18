package br.com.prime.prime.Services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.prime.prime.Mappers.EstabelecimentoMapper;
import br.com.prime.prime.dto.EstabelecimentoPutDTO;
import br.com.prime.prime.dto.EstabelecimentoRequestDTO;
import br.com.prime.prime.dto.EstabelecimentoResponseDTO;
import br.com.prime.prime.models.Estabelecimento;
import br.com.prime.prime.repository.EstabelecimentoRepository;

@Service
public class EstabelecimentoService {

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    EstabelecimentoMapper estabelecimentoMapper;

    public void removerPorId(Long id) {
        estabelecimentoRepository.deleteById(id);
    }

    public List<EstabelecimentoResponseDTO> buscarTodos() {
        return estabelecimentoMapper.estabelecimentosParaEstabelecimentoResponseDTOs(
                (List<Estabelecimento>) estabelecimentoRepository.findAll());
    }

    public EstabelecimentoResponseDTO criar(EstabelecimentoRequestDTO estabelecimentoRequestDTO) throws Exception {
        Estabelecimento estabelecimento = estabelecimentoMapper
                .estabelecimentoRequestParaEstabelecimento(estabelecimentoRequestDTO);
        estabelecimentoRepository.save(estabelecimento);
        return estabelecimentoMapper.estabelecimentoParaEstabelecimentoResponse(estabelecimento);
    }

    public EstabelecimentoResponseDTO alterar(EstabelecimentoPutDTO estabelecimentoPutDTO, Long id) {

        Optional<Estabelecimento> estabelecimentoOptional = estabelecimentoRepository.findById(id);
        if (estabelecimentoOptional.isEmpty()) {
            throw new NoSuchElementException();
        }
        Estabelecimento estabelecimento = estabelecimentoOptional.get();
        estabelecimento.setNome(estabelecimentoPutDTO.getNome());
        estabelecimento.setTelefone(estabelecimentoPutDTO.getTelefone());
        estabelecimento.setHorarioAtendimento(estabelecimentoPutDTO.getHorarioAtendimento());
        estabelecimento.setNumero(estabelecimentoPutDTO.getNumero());
        estabelecimento.setCidade(estabelecimentoPutDTO.getCidade());
        estabelecimento.setLogradouro(estabelecimentoPutDTO.getLogradouro());
        estabelecimento.setEstado(estabelecimentoPutDTO.getEstado());
        estabelecimento.setCnpj(estabelecimentoPutDTO.getCnpj());

        estabelecimentoRepository.save(estabelecimento);

        return estabelecimentoMapper.estabelecimentoParaEstabelecimentoResponse(estabelecimento);
    }
}
