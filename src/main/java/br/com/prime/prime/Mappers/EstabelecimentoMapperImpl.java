package br.com.prime.prime.Mappers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.prime.prime.dto.EstabelecimentoRequestDTO;
import br.com.prime.prime.dto.EstabelecimentoResponseDTO;
import br.com.prime.prime.dto.EstabelecimentoUsuarioResponseDTO;
import br.com.prime.prime.models.Estabelecimento;
import br.com.prime.prime.models.Usuario;
import br.com.prime.prime.repository.UsuarioRepository;

@Component
public class EstabelecimentoMapperImpl implements EstabelecimentoMapper {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Estabelecimento estabelecimentoRequestParaEstabelecimento(
            EstabelecimentoRequestDTO estabelecimentoRequestDTO) {
        Usuario usuario = usuarioRepository
                .findById(estabelecimentoRequestDTO.getIdUsuario())
                .get();
        return new Estabelecimento(estabelecimentoRequestDTO.getNome(), estabelecimentoRequestDTO.getTelefone(),
                estabelecimentoRequestDTO.getHorarioAtendimento(),
                estabelecimentoRequestDTO.getNumero(), estabelecimentoRequestDTO.getCidade(),
                estabelecimentoRequestDTO.getLogradouro(), estabelecimentoRequestDTO.getEstado(),
                estabelecimentoRequestDTO.getCnpj(),
                usuario);
    }

    @Override
    public EstabelecimentoResponseDTO estabelecimentoParaEstabelecimentoResponse(Estabelecimento estabelecimento) {
        return new EstabelecimentoResponseDTO(estabelecimento.getId(), estabelecimento.getNome(),
                estabelecimento.getTelefone(), estabelecimento.getHorarioAtendimento(),
                estabelecimento.getNumero(), estabelecimento.getCidade(), estabelecimento.getLogradouro(),
                estabelecimento.getEstado(), estabelecimento.getCnpj(), estabelecimento.getUsuario().getId());
    }

    @Override
    public Collection<EstabelecimentoUsuarioResponseDTO> estabelecimentosParaEstabelecimentosUsuariosResponse(
            Collection<Estabelecimento> estabelecimentos) {
        Collection<EstabelecimentoUsuarioResponseDTO> estabelecimentosMapeados = new ArrayList<>();
        for (Estabelecimento estabelecimento : estabelecimentos) {
            estabelecimentosMapeados.add(new EstabelecimentoUsuarioResponseDTO(estabelecimento.getId(),
                    estabelecimento.getNome(), estabelecimento.getLogradouro(),
                    estabelecimento.getTelefone(), estabelecimento.getCnpj()));
        }
        return estabelecimentosMapeados;
    }

    @Override
    public Collection<EstabelecimentoResponseDTO> estabelecimentosParaEstabelecimentosResponses(
            Collection<Estabelecimento> estabelecimentos) {
        return estabelecimentos.stream()
                .map(EstabelecimentoResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<EstabelecimentoResponseDTO> estabelecimentosParaEstabelecimentoResponseDTOs(Collection<Estabelecimento> estabelecimentos) {
        Collection<EstabelecimentoResponseDTO> estabelecimentoResponseDTOS = new ArrayList<>();
        for (Estabelecimento estabelecimento : estabelecimentos) {
            estabelecimentoResponseDTOS.add(estabelecimentoParaEstabelecimentoResponse(estabelecimento));
        }
        return estabelecimentoResponseDTOS;
    }
}
