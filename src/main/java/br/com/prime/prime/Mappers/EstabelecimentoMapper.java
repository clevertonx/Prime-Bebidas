package br.com.prime.prime.Mappers;
import java.util.Collection;

import org.mapstruct.Mapper;

import br.com.prime.prime.dto.EstabelecimentoRequestDTO;
import br.com.prime.prime.dto.EstabelecimentoResponseDTO;
import br.com.prime.prime.dto.EstabelecimentoUsuarioResponseDTO;
import br.com.prime.prime.dominio.estabelecimento.Estabelecimento;

@Mapper(componentModel = "spring")
public interface EstabelecimentoMapper {
    public Estabelecimento estabelecimentoRequestParaEstabelecimento(EstabelecimentoRequestDTO estabelecimentoRequestDTO);

    public EstabelecimentoResponseDTO estabelecimentoParaEstabelecimentoResponse(Estabelecimento estabelecimento);

    public Collection<EstabelecimentoResponseDTO> estabelecimentosParaEstabelecimentosResponses(Collection<Estabelecimento> estabelecimentos);

    public Collection<EstabelecimentoUsuarioResponseDTO> estabelecimentosParaEstabelecimentosUsuariosResponse(Collection<Estabelecimento> estabelecimentos);

}
