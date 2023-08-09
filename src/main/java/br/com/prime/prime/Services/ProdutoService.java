package br.com.prime.prime.Services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.prime.prime.Mappers.ProdutoMapper;
import br.com.prime.prime.dto.ProdutoEstabelecimentoUsuarioResponseDTO;
import br.com.prime.prime.dto.ProdutoRequestDTO;
import br.com.prime.prime.dto.ProdutoResponseDTO;
import br.com.prime.prime.models.Categoria;
import br.com.prime.prime.models.Estabelecimento;
import br.com.prime.prime.Exceptions.PrecoInvalidoException;
import br.com.prime.prime.models.Produto;
import br.com.prime.prime.models.Usuario;
import br.com.prime.prime.repository.ProdutoRepository;
import br.com.prime.prime.repository.UsuarioRepository;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoMapper produtoMapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void removerPorId(Long id) {
        produtoRepository.deleteById(id);
    }

    public ProdutoResponseDTO criar(ProdutoRequestDTO produtoRequestDTO) throws PrecoInvalidoException {
        Produto produto = produtoMapper
                .produtoRequestParaProduto(produtoRequestDTO);
        produtoRepository.save(produto);
        return produtoMapper.produtoParaProdutoResponse(produto);
    }

    public ProdutoResponseDTO editar(ProdutoRequestDTO produtoRequestDTO, Long id) {

        Optional<Produto> produtoOptional = produtoRepository.findById(id);
        if (produtoOptional.isEmpty()) {
            throw new NoSuchElementException();
        }
        Produto produto = produtoOptional.get();
        produto.setNome(produtoRequestDTO.getNome());
        produto.setDescricao(produtoRequestDTO.getDescricao());
        produto.setMarca(produtoRequestDTO.getMarca());
        produto.setPreco(produtoRequestDTO.getPreco());
        produto.setCategoria(produtoRequestDTO.getCategoria());
        produto.setImagem(produtoRequestDTO.getImagem());
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
                            produto.getEstabelecimento().getId(), produto.getEstabelecimento().getNome()));
        }
        return produtoMapper.produtosParaProdutoResponses(produtos);
    }

    public List<ProdutoResponseDTO> buscarTodos() {
        return produtoMapper.produtosParaProdutoResponses((List<Produto>) produtoRepository.findAll());
    }

    public List<ProdutoResponseDTO> buscarPorCategoria(Categoria categoria) {
        List<Produto> produtos = produtoRepository.findByCategoria(categoria);
        return produtoMapper.produtosParaProdutoResponses(produtos);
    }

    public Collection<ProdutoEstabelecimentoUsuarioResponseDTO> produtoPorEstabelecimentoUsuario(Long idUsuario,
            Long idEstabelecimento) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);
        if (usuarioOptional.isEmpty()) {
            throw new NoSuchElementException();
        }
        Usuario usuario = usuarioOptional.get();

        Estabelecimento estabelecimento = usuario.getEstabelecimentos().stream()
                .filter(e -> e.getId().equals(idEstabelecimento))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);

        Collection<Produto> produtos = estabelecimento.getProdutos();

        return produtoMapper.produtosParaProdutosEstabelecimentosUsuarioResponse(produtos);
    }

    public Collection<ProdutoEstabelecimentoUsuarioResponseDTO> produtoPorUsuario(Long idUsuario) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);
        if (usuarioOptional.isEmpty()) {
            throw new NoSuchElementException();
        }
        Usuario usuario = usuarioOptional.get();

        List<Produto> produtos = new ArrayList<>();
        for (Estabelecimento estabelecimento : usuario.getEstabelecimentos()) {
            produtos.addAll(estabelecimento.getProdutos());
        }

        return produtoMapper.produtosParaUsuariosResponse(produtos);
    }
}
