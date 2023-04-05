package br.com.prime.prime.Mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.prime.prime.dto.ProdutoRequestDTO;
import br.com.prime.prime.dto.ProdutoResponseDTO;
import br.com.prime.prime.models.Estabelecimento;
import br.com.prime.prime.models.PreçoInvalidoException;
import br.com.prime.prime.models.Produto;
import br.com.prime.prime.repository.EstabelecimentoRepository;

@Component
public class ProdutoMapperImpl implements ProdutoMapper {
@Autowired 
private EstabelecimentoRepository estabelecimentoRepository;
    @Override
    public Produto produtoRequestParaProduto(ProdutoRequestDTO produtoRequestDTO) throws PreçoInvalidoException {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(produtoRequestDTO.getIdEstabelecimento()).get();
        return new Produto(produtoRequestDTO.getNome(), produtoRequestDTO.getDescricao(), produtoRequestDTO.getMarca(),
                produtoRequestDTO.getPreço(), produtoRequestDTO.getCategoria(), produtoRequestDTO.getImagem(), estabelecimento);
    }

    @Override
    public ProdutoResponseDTO produtoParaProdutoResponse(Produto produto) {

        return new ProdutoResponseDTO(produto.getId(), produto.getNome(), produto.getDescricao(), produto.getMarca(),
                produto.getPreço(), produto.getImagem());
    }

}
