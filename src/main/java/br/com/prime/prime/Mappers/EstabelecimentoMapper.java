package br.com.prime.prime.Mappers;

import java.util.List;

import org.mapstruct.Mapper;

import br.com.prime.prime.dto.EstabelecimentoPutDTO;
import br.com.prime.prime.dto.EstabelecimentoRequestDTO;
import br.com.prime.prime.dto.EstabelecimentoResponseDTO;
import br.com.prime.prime.models.Estabelecimento;

@Mapper(componentModel = "spring")
public interface EstabelecimentoMapper {
    public Estabelecimento estabelecimentoRequestParaEstabelecimento(
            EstabelecimentoRequestDTO estabelecimentoRequestDTO);

    public Estabelecimento estabelecimentoPutParaEstabelecimento(EstabelecimentoPutDTO estabelecimentoPutDTO);

    public EstabelecimentoResponseDTO estabelecimentoParaEstabelecimentoResponse(Estabelecimento estabelecimento);

    public List<EstabelecimentoResponseDTO> estabelecimentosParaEstabelecimentoResponseDTOs(
            List<Estabelecimento> estabelecimentos);
}
