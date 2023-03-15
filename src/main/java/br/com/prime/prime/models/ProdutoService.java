package br.com.prime.prime.models;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import br.com.prime.prime.repository.ProdutoRepository;

@Service
public class ProdutoService {
    @Autowired

    private ProdutoRepository produtorepo;

    public void saveProdutoToDB(MultipartFile file, String nome, String descricao, String marca, Double preço,
            Categoria categoria) {
        Produto p = new Produto();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (fileName.contains("..")) {
            System.out.println("não é um arquivo válido");
        }
        p.setCategoria(categoria);
        p.setDescricao(descricao);
        p.setMarca(marca);
        p.setNome(nome);
        p.setPreço(preço);
        try {
            p.setImagem(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        produtorepo.save(p);

    }

    public void updateProdutoToDB(MultipartFile file, String nome, String descricao, String marca, Double preço,
            Categoria categoria) {
    }

    public void deleteProdutoById(Long id) {
    }

    public Produto getProdutoById(Long id) {
        return null;
    }
}
