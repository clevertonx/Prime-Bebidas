package br.com.prime.prime.Mappers;
import java.util.Collection;
import java.util.List;

import org.mapstruct.Mapper;

import br.com.prime.prime.dto.ProdutoEstabelecimentoUsuarioResponseDTO;
import br.com.prime.prime.dto.ProdutoRequestDTO;
import br.com.prime.prime.dto.ProdutoResponseDTO;
import br.com.prime.prime.models.PrecoInvalidoException;
import br.com.prime.prime.models.Produto;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {
    public Produto produtoRequestParaProduto(ProdutoRequestDTO ProdutoRequestDTO) throws PrecoInvalidoException;

    public ProdutoResponseDTO produtoParaProdutoResponse(Produto produto);

    public List<ProdutoResponseDTO> produtosParaProdutoResponses(List<Produto> produtos);


    public Collection<ProdutoEstabelecimentoUsuarioResponseDTO>
    produtosParaProdutosEstabelecimentosUsuarioResponse(Collection<Produto> produtos);

}
