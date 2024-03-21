package br.com.prime.prime.Services;

import java.util.*;

import br.com.prime.prime.dto.ProdutoResponseDTO;
import br.com.prime.prime.models.Produto;
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
        estabelecimento.setCep(estabelecimentoRequestDTO.getCep());
        estabelecimento.setNumero(estabelecimentoRequestDTO.getNumero());
        estabelecimento.setCidade(estabelecimentoRequestDTO.getCidade());
        estabelecimento.setLogradouro(estabelecimentoRequestDTO.getLogradouro());
        estabelecimento.setBairro(estabelecimentoRequestDTO.getBairro());
        estabelecimento.setEstado(estabelecimentoRequestDTO.getEstado());
        estabelecimento.setCnpj(estabelecimentoRequestDTO.getCnpj());
        estabelecimentoRepository.save(estabelecimento);

        return estabelecimentoMapper.estabelecimentoParaEstabelecimentoResponse(estabelecimento);
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    public Collection<EstabelecimentoResponseDTO> buscarEstabelecimentoPorNome(String nome) {
        Collection<Estabelecimento> estabelecimentos;
        if (nome == null || nome.isEmpty()) {
            estabelecimentos = (Collection<Estabelecimento>) estabelecimentoRepository.findAll();
        } else {
            estabelecimentos = estabelecimentoRepository.findByNomeContainingIgnoreCase(nome);
        }

        Collection<EstabelecimentoResponseDTO> estabelecimentosRetornados = new ArrayList<>();

        for (Estabelecimento estabelecimento : estabelecimentos) {
            estabelecimentosRetornados.add(new EstabelecimentoResponseDTO(
                    estabelecimento.getId(), estabelecimento.getNome(),
                    estabelecimento.getTelefone(), estabelecimento.getHorarioAtendimento(),
                    estabelecimento.getCep(),
                    estabelecimento.getNumero(), estabelecimento.getCidade(),
                    estabelecimento.getLogradouro(), estabelecimento.getBairro(), estabelecimento.getEstado(),
                    estabelecimento.getCnpj(), estabelecimento.getUsuario().getId())
            );
        }
        return estabelecimentoMapper.estabelecimentosParaEstabelecimentoResponseDTOs(estabelecimentos);
    }


}
