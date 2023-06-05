package br.com.prime.prime.Mappers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.prime.prime.dto.ProdutoEstabelecimentoResponseDTO;
import br.com.prime.prime.dto.ProdutoRequestDTO;
import br.com.prime.prime.dto.ProdutoResponseDTO;
import br.com.prime.prime.models.Estabelecimento;
import br.com.prime.prime.models.PrecoInvalidoException;
import br.com.prime.prime.models.Produto;
import br.com.prime.prime.repository.EstabelecimentoRepository;

@Component
public class ProdutoMapperImpl implements ProdutoMapper {
    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @Override
    public Produto produtoRequestParaProduto(ProdutoRequestDTO produtoRequestDTO) throws PrecoInvalidoException {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(produtoRequestDTO.getIdEstabelecimento())
                .get();
        return new Produto(produtoRequestDTO.getNome(), produtoRequestDTO.getDescricao(), produtoRequestDTO.getMarca(),
                produtoRequestDTO.getPreco(), produtoRequestDTO.getCategoria(), produtoRequestDTO.getImagem(),
                estabelecimento);
    }

    @Override
    public ProdutoResponseDTO produtoParaProdutoResponse(Produto produto) {

        return new ProdutoResponseDTO(produto.getId(), produto.getNome(), produto.getDescricao(), produto.getMarca(),
                produto.getPreco(), produto.getImagem(), produto.getEstabelecimento().getId());
    }

    @Override
    public Collection<ProdutoEstabelecimentoResponseDTO> produtosParaProdutosEstabelecimentosResponse(
            Collection<Produto> produtos) {
        Collection<ProdutoEstabelecimentoResponseDTO> produtosMapeados = new ArrayList<>();
        for (Produto produto : produtos) {
            produtosMapeados.add(new ProdutoEstabelecimentoResponseDTO(produto.getId(),
                    produto.getNome(), produto.getDescricao(),
                    produto.getMarca(), produto.getPreco(), produto.getCategoria()));
        }
        return produtosMapeados;
    }

    @Override
    public List<ProdutoResponseDTO> produtosParaProdutoResponses(List<Produto> produtos) {
        List<ProdutoResponseDTO> produtosMapeados = new ArrayList<>();
        for (Produto produto : produtos) {
            produtosMapeados.add(new ProdutoResponseDTO(produto));
        }
        return produtosMapeados;
    }

}
