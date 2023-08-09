package br.com.prime.prime.Mappers;

import br.com.prime.prime.dto.EstabelecimentoRequestDTO;
import br.com.prime.prime.dto.EstabelecimentoResponseDTO;
import br.com.prime.prime.dto.EstabelecimentoUsuarioResponseDTO;
import br.com.prime.prime.models.Estabelecimento;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface EstabelecimentoMapper {
    Estabelecimento estabelecimentoRequestParaEstabelecimento(EstabelecimentoRequestDTO estabelecimentoRequestDTO);

    EstabelecimentoResponseDTO estabelecimentoParaEstabelecimentoResponse(Estabelecimento estabelecimento);

    Collection<EstabelecimentoResponseDTO> estabelecimentosParaEstabelecimentosResponses(Collection<Estabelecimento> estabelecimentos);

    Collection<EstabelecimentoUsuarioResponseDTO> estabelecimentosParaEstabelecimentosUsuariosResponse(Collection<Estabelecimento> estabelecimentos);

    Collection<EstabelecimentoResponseDTO> estabelecimentosParaEstabelecimentoResponseDTOs(Collection<Estabelecimento> estabelecimentos);
}
