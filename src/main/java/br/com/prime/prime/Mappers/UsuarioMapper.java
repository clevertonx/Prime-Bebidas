package br.com.prime.prime.Mappers;

import java.util.List;

import org.mapstruct.Mapper;

import br.com.prime.prime.dto.UsuarioPutDTO;
import br.com.prime.prime.dto.UsuarioRequestDTO;
import br.com.prime.prime.dto.UsuarioResponseDTO;
import br.com.prime.prime.models.Usuario;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    public Usuario usuarioRequestParaUsuario(UsuarioRequestDTO usuarioRequestDTO, PasswordEncoder passwordEncoder);

    public Usuario usuarioPutParaUsuario(UsuarioPutDTO usuarioPutDTO);

    public UsuarioResponseDTO usuarioParaUsuarioResponse(Usuario usuario);

    public List<UsuarioResponseDTO> usuariosParaUsuarioResponseDTOs(List<Usuario> usuarios);

    
}
