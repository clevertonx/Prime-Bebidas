package br.com.prime.prime.Services;

import br.com.prime.prime.Mappers.EstabelecimentoMapper;
import br.com.prime.prime.Mappers.ProdutoMapper;
import br.com.prime.prime.Mappers.UsuarioMapper;
import br.com.prime.prime.dto.EstabelecimentoUsuarioResponseDTO;
import br.com.prime.prime.dto.UsuarioPutDTO;
import br.com.prime.prime.dto.UsuarioRequestDTO;
import br.com.prime.prime.dto.UsuarioResponseDTO;
import br.com.prime.prime.models.Usuario;
import br.com.prime.prime.repository.EstabelecimentoRepository;
import br.com.prime.prime.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    UsuarioMapper usuarioMapper;

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    EstabelecimentoMapper estabelecimentoMapper;

    @Autowired
    ProdutoMapper produtoMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void removerPorId(Long id) {
        usuarioRepository.deleteById(id);
    }

    public List<UsuarioResponseDTO> buscarTodos() {
        return usuarioMapper.usuariosParaUsuarioResponseDTOs((List<Usuario>) usuarioRepository.findAll());
    }

    public UsuarioResponseDTO criar(UsuarioRequestDTO usuarioRequestDTO) throws Exception {
        Usuario usuario = usuarioMapper.usuarioRequestParaUsuario(usuarioRequestDTO, passwordEncoder);
        usuarioRepository.save(usuario);
        return usuarioMapper.usuarioParaUsuarioResponse(usuario);
    }

    public UsuarioResponseDTO alterar(UsuarioPutDTO usuarioPutDTO, Long id) {

        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isEmpty()) {
            throw new NoSuchElementException();
        }
        Usuario usuario = usuarioOptional.get();
        usuario.setEmail(usuarioPutDTO.getEmail());
        usuario.setSenha(usuarioPutDTO.getSenha());
        usuarioRepository.save(usuario);

        return usuarioMapper.usuarioParaUsuarioResponse(usuario);
    }

    public Collection<EstabelecimentoUsuarioResponseDTO> estabelecimentoPorUsuario(Long idUsuario) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);
        if (usuarioOptional.isEmpty()) {
            throw new NoSuchElementException();
        }
        Usuario usuario = usuarioOptional.get();
        return estabelecimentoMapper
                .estabelecimentosParaEstabelecimentosUsuariosResponse(usuario.getEstabelecimentos());
    }
    
}
