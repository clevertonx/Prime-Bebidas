package br.com.prime.prime.security.password;

import br.com.prime.prime.models.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenService {
    private final PasswordResetTokenRepository passwordResetTokenRepository;


    public void criarTokenDeRedefinicaoDeSenhaParaUsuario(Usuario user, String passwordToken) {
        PasswordResetToken passwordRestToken = new PasswordResetToken(passwordToken, user);
        passwordResetTokenRepository.save(passwordRestToken);
    }

    public String validarTokenDeRedefinicaoDeSenha(String passwordResetToken) {
        PasswordResetToken passwordToken = passwordResetTokenRepository.findByToken(passwordResetToken);
        if(passwordToken == null){
            return "Token de verificação inválido";
        }
        Usuario user = passwordToken.getUser();
        Calendar calendar = Calendar.getInstance();
        if ((passwordToken.getExpirationTime().getTime()-calendar.getTime().getTime())<= 0){
            return "O link já expirou, reenvie o link";
        }
        return "valid";
    }
    public Optional<Usuario> encontrarUsuarioPorTokenDeSenha(String passwordResetToken) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(passwordResetToken).getUser());
    }

    public PasswordResetToken encontrarTokenDeRedefinicaoDeSenha(String token){
        return passwordResetTokenRepository.findByToken(token);
    }
}

