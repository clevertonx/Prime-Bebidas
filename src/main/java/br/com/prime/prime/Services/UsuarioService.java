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
import br.com.prime.prime.security.password.PasswordResetTokenService;
import br.com.prime.prime.token.VerificationToken;
import br.com.prime.prime.token.VerificationTokenRepository;
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

    @Autowired
    PasswordResetTokenService passwordResetTokenService;

    @Autowired
    VerificationTokenRepository tokenRepository;


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

    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findOptionalByEmail(email);
    }

    public void createPasswordResetTokenForUser(Usuario user, String passwordToken) {
        passwordResetTokenService.createPasswordResetForUser(user, passwordToken);
    }

    public void saveUserVerificationToken(Usuario theUser, String token) {
        var verificationToken = new VerificationToken(token, theUser);
        tokenRepository.save(verificationToken);
    }

    public String validatePasswordResetToken(String passwordResetToken) {
        return passwordResetTokenService.validatePasswordResetToken(passwordResetToken);
    }

    public Usuario findUserByPasswordToken(String passwordResetToken) {
        return passwordResetTokenService.findUserByPasswordToken(passwordResetToken).get();
    }

    public void resetUserPassword(Usuario user, String newPassword) {
        user.setSenha(passwordEncoder.encode(newPassword));
        usuarioRepository.save(user);
    }

}
