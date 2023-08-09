package br.com.prime.prime.Mappers;
import java.util.Collection;

import org.mapstruct.Mapper;

import br.com.prime.prime.dto.ProdutoEstabelecimentoUsuarioResponseDTO;
import br.com.prime.prime.dto.ProdutoRequestDTO;
import br.com.prime.prime.dto.ProdutoResponseDTO;
import br.com.prime.prime.Exceptions.PrecoInvalidoException;
import br.com.prime.prime.models.Produto;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {
    Produto produtoRequestParaProduto(ProdutoRequestDTO ProdutoRequestDTO) throws PrecoInvalidoException;

    ProdutoResponseDTO produtoParaProdutoResponse(Produto produto);

    Collection<ProdutoResponseDTO> produtosParaProdutoResponses(Collection<Produto> produtos);

    Collection<ProdutoEstabelecimentoUsuarioResponseDTO>
    produtosParaProdutosEstabelecimentosUsuarioResponse(Collection<Produto> produtos);

    Collection<ProdutoEstabelecimentoUsuarioResponseDTO> produtosParaUsuariosResponse(Collection<Produto> produtos);

}
