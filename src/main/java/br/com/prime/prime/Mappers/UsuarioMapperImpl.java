package br.com.prime.prime.Mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.prime.prime.dto.UsuarioPutDTO;
import br.com.prime.prime.dto.UsuarioRequestDTO;
import br.com.prime.prime.dto.UsuarioResponseDTO;
import br.com.prime.prime.models.Usuario;
import br.com.prime.prime.repository.UsuarioRepository;

@Component
public class UsuarioMapperImpl implements UsuarioMapper {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario usuarioRequestParaUsuario(UsuarioRequestDTO usuarioRequestDTO) {
        return new Usuario(usuarioRequestDTO.getEmail(), usuarioRequestDTO.getSenha());
    }

    @Override
    public Usuario usuarioPutParaUsuario(UsuarioPutDTO usuarioPutDTO) {
        Usuario usuario = usuarioRepository.findById(usuarioPutDTO.getId()).get();
        usuario.setEmail(usuarioPutDTO.getEmail());
        usuario.setSenha(usuarioPutDTO.getSenha());
        return usuario;
    }

    @Override
    public UsuarioResponseDTO usuarioParaUsuarioResponse(Usuario usuario) {
        return new UsuarioResponseDTO(usuario.getId(), usuario.getEmail(), usuario.getSenha());
    }

    @Override
    public List<UsuarioResponseDTO> usuariosParaUsuarioResponseDTOs(List<Usuario> usuarios) {
        List<UsuarioResponseDTO> usuarioResponseDTOs = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            usuarioResponseDTOs.add(usuarioParaUsuarioResponse(usuario));
        }
        return usuarioResponseDTOs;
    }

}
