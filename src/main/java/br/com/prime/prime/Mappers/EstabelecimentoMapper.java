package br.com.prime.prime.Mappers;
import org.mapstruct.Mapper;

import br.com.prime.prime.dto.EstabelecimentoRequestDTO;
import br.com.prime.prime.dto.EstabelecimentoResponseDTO;
import br.com.prime.prime.models.Estabelecimento;

@Mapper(componentModel = "spring")
public interface EstabelecimentoMapper {
    public Estabelecimento estabelecimentoRequestParaEstabelecimento(EstabelecimentoRequestDTO estabelecimentoRequestDTO);

    public EstabelecimentoResponseDTO estabelecimentoParaEstabelecimentoResponse(Estabelecimento estabelecimento);

}
