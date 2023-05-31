package br.com.prime.prime.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.prime.prime.Mappers.ProdutoMapper;
import br.com.prime.prime.dto.ProdutoRequestDTO;
import br.com.prime.prime.dto.ProdutoResponseDTO;
import br.com.prime.prime.models.PrecoInvalidoException;
import br.com.prime.prime.models.Produto;
import br.com.prime.prime.repository.ProdutoRepository;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoMapper produtoMapper;

    public void removerPorId(Long id) {
        produtoRepository.deleteById(id);
    }

    public ProdutoResponseDTO criar(ProdutoRequestDTO ProdutoRequestDTO) throws PrecoInvalidoException {
        Produto produto = produtoMapper.produtoRequestParaProduto(ProdutoRequestDTO);
        produtoRepository.save(produto);
        return produtoMapper.produtoParaProdutoResponse(produto);
    }

    public List<ProdutoResponseDTO> buscarPorNome(String nome) {
        List<Produto> produtos;
        if (nome == null || nome.isEmpty()) {
            produtos = (List<Produto>) produtoRepository.findAll();
        } else {
            produtos = produtoRepository.findByNomeContainingIgnoreCase(nome);
        }

        List<ProdutoResponseDTO> produtosRetornados = new ArrayList<>();

        for (Produto produto : produtos) {
            produtosRetornados
                    .add(new ProdutoResponseDTO(produto.getId(), produto.getNome(),
                            produto.getDescricao(), produto.getMarca(), produto.getPreco(), produto.getImagem(),
                            produto.getEstabelecimento().getId()));
        }
        return produtoMapper.produtosParaProdutoResponseDTOs(produtos);
    }

    public List<ProdutoResponseDTO> buscarTodos() {
        return produtoMapper.produtosParaProdutoResponseDTOs((List<Produto>) produtoRepository.findAll());
    }
}
