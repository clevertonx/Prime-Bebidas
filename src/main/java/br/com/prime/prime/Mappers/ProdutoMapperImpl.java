package br.com.prime.prime.Mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.prime.prime.dto.ProdutoPutDTO;
import br.com.prime.prime.dto.ProdutoRequestDTO;
import br.com.prime.prime.dto.ProdutoResponseDTO;
import br.com.prime.prime.models.Estabelecimento;
import br.com.prime.prime.models.PrecoInvalidoException;
import br.com.prime.prime.models.Produto;
import br.com.prime.prime.repository.EstabelecimentoRepository;
import br.com.prime.prime.repository.ProdutoRepository;

@Component
public class ProdutoMapperImpl implements ProdutoMapper {

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Override
    public Produto produtoRequestParaProduto(ProdutoRequestDTO produtoRequestDTO) throws PrecoInvalidoException {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(produtoRequestDTO.getIdEstabelecimento())
                .get();
        return new Produto(produtoRequestDTO.getNome(), produtoRequestDTO.getDescricao(), produtoRequestDTO.getMarca(),
                produtoRequestDTO.getPreco(), produtoRequestDTO.getCategoria(), produtoRequestDTO.getImagem(),
                estabelecimento);
    }

    @Override
    public Produto produtoPutParaProduto(ProdutoPutDTO produtoPutDTO) {
        Produto produto = produtoRepository.findById(produtoPutDTO.getId())
                .get();
        produto.setNome(produtoPutDTO.getNome());
        produto.setDescricao(produtoPutDTO.getDescricao());
        produto.setMarca(produtoPutDTO.getMarca());
        produto.setPreco(produtoPutDTO.getPreco());
        produto.setCategoria(produtoPutDTO.getCategoria());
        produto.setImagem(produtoPutDTO.getImagem());

        return produto;
    }

    @Override
    public ProdutoResponseDTO produtoParaProdutoResponse(Produto produto) {

        return new ProdutoResponseDTO(produto.getId(), produto.getNome(), produto.getDescricao(), produto.getMarca(),
                produto.getPreco(), produto.getImagem());
    }

    @Override
    public List<ProdutoResponseDTO> produtosParaProdutoResponseDTOs(List<Produto> produtos) {
        List<ProdutoResponseDTO> produtoResponseDTOs = new ArrayList<>();
        for (Produto produto : produtos) {
            produtoResponseDTOs.add(produtoParaProdutoResponse(produto));
        }
        return produtoResponseDTOs;
    }

}
