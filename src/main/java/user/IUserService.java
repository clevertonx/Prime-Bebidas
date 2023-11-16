package user;

import br.com.prime.prime.dto.RegistrationRequest;
import br.com.prime.prime.models.Usuario;
import br.com.prime.prime.token.VerificationToken;

import java.util.List;
import java.util.Optional;

public interface IUserService {


    List<Usuario> getUsers();

    Usuario registrarUsuario(RegistrationRequest request);

    Optional<Usuario> findByEmail(String email);

    void salvarTokenDeVerificacaoDoUsuario(Usuario theUser, String verificationToken);

    String validarToken(String theToken);

    VerificationToken gerarNovoTokenDeVerificacao(String oldToken);
    void alterarSenha(Usuario theUser, String newPassword);

    String validarTokenDeRedefinicaoDeSenha(String token);

    Usuario encontrarUsuarioPorTokenDeSenha(String token);

    void criarTokenDeRedefinicaoDeSenhaParaUsuario(Usuario user, String passwordResetToken);

    boolean oldPasswordIsValid(Usuario user, String oldPassword);
}
