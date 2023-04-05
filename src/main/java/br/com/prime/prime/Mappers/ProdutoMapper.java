package br.com.prime.prime.Mappers;
import org.mapstruct.Mapper;

import br.com.prime.prime.dto.ProdutoRequestDTO;
import br.com.prime.prime.dto.ProdutoResponseDTO;
import br.com.prime.prime.models.PreçoInvalidoException;
import br.com.prime.prime.models.Produto;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {
    public Produto produtoRequestParaProduto(ProdutoRequestDTO ProdutoRequestDTO) throws PreçoInvalidoException;

    public ProdutoResponseDTO produtoParaProdutoResponse(Produto produto);
}
