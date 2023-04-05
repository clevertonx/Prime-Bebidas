package br.com.prime.prime.Mappers;

import org.springframework.stereotype.Component;

import br.com.prime.prime.dto.ProdutoRequestDTO;
import br.com.prime.prime.dto.ProdutoResponseDTO;
import br.com.prime.prime.models.PreçoInvalidoException;
import br.com.prime.prime.models.Produto;

@Component
public class ProdutoMapperImpl implements ProdutoMapper {

    @Override
    public Produto produtoRequestParaProduto(ProdutoRequestDTO produtoRequestDTO) throws PreçoInvalidoException {

        return new Produto(produtoRequestDTO.getNome(), produtoRequestDTO.getDescricao(), produtoRequestDTO.getMarca(),
                produtoRequestDTO.getPreço(), produtoRequestDTO.getCategoria(), produtoRequestDTO.getImagem());
    }

    @Override
    public ProdutoResponseDTO produtoParaProdutoResponse(Produto produto) {

        return new ProdutoResponseDTO(produto.getId(), produto.getNome(), produto.getDescricao(), produto.getMarca(),
                produto.getPreço(), produto.getImagem());
    }

}
