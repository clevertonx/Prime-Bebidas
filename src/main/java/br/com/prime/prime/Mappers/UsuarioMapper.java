package br.com.prime.prime.Mappers;

import java.util.List;

import org.mapstruct.Mapper;

import br.com.prime.prime.dto.UsuarioPutDTO;
import br.com.prime.prime.dto.UsuarioRequestDTO;
import br.com.prime.prime.dto.UsuarioResponseDTO;
import br.com.prime.prime.models.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    public Usuario usuarioRequestParaUsuario(UsuarioRequestDTO usuarioRequestDTO);

    public Usuario usuarioPutParaUsuario(UsuarioPutDTO usuarioPutDTO);

    public UsuarioResponseDTO usuarioParaUsuarioResponse(Usuario usuario);

    public List<UsuarioResponseDTO> usuariosParaUsuarioResponseDTOs(List<Usuario> usuarios);
}
