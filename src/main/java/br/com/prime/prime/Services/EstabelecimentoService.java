package br.com.prime.prime.Services;

import br.com.prime.prime.dominio.Usuario;
import br.com.prime.prime.dominio.estabelecimento.Estabelecimento;
import br.com.prime.prime.dominio.estabelecimento.EstabelecimentoAtualizacao;
import br.com.prime.prime.dominio.estabelecimento.EstabelecimentoCriacao;
import br.com.prime.prime.dominio.estabelecimento.EstabelecimentoDTO;
import br.com.prime.prime.repository.EstabelecimentoRepository;
import br.com.prime.prime.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class EstabelecimentoService {

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    public EstabelecimentoDTO criar(EstabelecimentoCriacao estabelecimentoCriacao) {
        Usuario usuario = usuarioRepository.findById(estabelecimentoCriacao.idUsuario())
                .orElseThrow(() -> new NoSuchElementException());

        Estabelecimento estabelecimento = new Estabelecimento(
                estabelecimentoCriacao.nome(),
                estabelecimentoCriacao.telefone(),
                estabelecimentoCriacao.horarioAtendimento(),
                estabelecimentoCriacao.numero(),
                estabelecimentoCriacao.cidade(),
                estabelecimentoCriacao.logradouro(),
                estabelecimentoCriacao.estado(),
                estabelecimentoCriacao.cnpj(),
                usuario
        );

        estabelecimentoRepository.save(estabelecimento);

        return new EstabelecimentoDTO(
                estabelecimento.getId(),
                estabelecimento.getNome(),
                estabelecimento.getTelefone(),
                estabelecimento.getHorarioAtendimento(),
                estabelecimento.getNumero(),
                estabelecimento.getCidade(),
                estabelecimento.getLogradouro(),
                estabelecimento.getEstado(),
                estabelecimento.getCnpj(),
                estabelecimento.getUsuario().getId()
        );
    }


    public EstabelecimentoDTO editar(EstabelecimentoAtualizacao dados, Long id) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException());

        Usuario usuario = usuarioRepository.findById(dados.idUsuario())
                .orElseThrow(NoSuchElementException::new);

        estabelecimento.setUsuario(usuario);
        estabelecimento.setNome(dados.nome());
        estabelecimento.setTelefone(dados.telefone());
        estabelecimento.setHorarioAtendimento(dados.horarioAtendimento());
        estabelecimento.setNumero(dados.numero());
        estabelecimento.setCidade(dados.cidade());
        estabelecimento.setLogradouro(dados.logradouro());
        estabelecimento.setEstado(dados.estado());
        estabelecimento.setCnpj(dados.cnpj());

        estabelecimentoRepository.save(estabelecimento);

        return new EstabelecimentoDTO(
                estabelecimento.getId(),
                estabelecimento.getNome(),
                estabelecimento.getTelefone(),
                estabelecimento.getHorarioAtendimento(),
                estabelecimento.getNumero(),
                estabelecimento.getCidade(),
                estabelecimento.getLogradouro(),
                estabelecimento.getEstado(),
                estabelecimento.getCnpj(),
                dados.idUsuario()
        );
    }
}