package br.com.prime.prime.security.password;

import br.com.prime.prime.models.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PasswordResetTokenService {
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public void createPasswordResetForUser(Usuario user, String passwordToken) {
        PasswordResetToken passwordResetToken = new PasswordResetToken(passwordToken, user);
        passwordResetTokenRepository.save(passwordResetToken);
    }

    public String validatePasswordResetToken(String theToken) {

        PasswordResetToken token = passwordResetTokenRepository.findByToken(theToken);
        if (token == null) {
            return "Token de redefinição de senha inválido";
        }
        Usuario user = token.getUser();
        Calendar calendar = Calendar.getInstance();
        if ((token.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0) {
            return "Link já expirou, reenvie link" +
                    "Por favor, clique no link abaixo para receber um novo link de verificação";
        }
        return "válido";
    }

    public Optional<Usuario> findByPasswordToken(String passwordToken){
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(passwordToken).getUser());
    }

    public Optional<Usuario> findUserByPasswordToken(String passwordResetToken) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(passwordResetToken).getUser());
    }

}

