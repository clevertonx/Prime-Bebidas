package br.com.prime.prime.Services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.prime.prime.Mappers.UsuarioMapper;
import br.com.prime.prime.dto.UsuarioPutDTO;
import br.com.prime.prime.dto.UsuarioRequestDTO;
import br.com.prime.prime.dto.UsuarioResponseDTO;
import br.com.prime.prime.models.Usuario;
import br.com.prime.prime.repository.UsuarioRepository;

@Service
public class UsuarioService {
    
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    UsuarioMapper usuarioMapper;

    public void removerPorId(Long id) {
        usuarioRepository.deleteById(id);
    }

    public List<UsuarioResponseDTO> buscarTodos() {
        return usuarioMapper.usuariosParaUsuarioResponseDTOs((List<Usuario>) usuarioRepository.findAll());
    }

    public UsuarioResponseDTO criar(UsuarioRequestDTO usuarioRequestDTO) throws Exception {
        Usuario usuario = usuarioMapper.usuarioRequestParaUsuario(usuarioRequestDTO);
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

    public Usuario loginUsuario(String email, String senha) {
        Usuario usuariologin = usuarioRepository.login(email, senha);
        return usuariologin;
    }
}
