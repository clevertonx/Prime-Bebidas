package user;

import br.com.prime.prime.models.Usuario;
import br.com.prime.prime.token.VerificationToken;

import java.util.Optional;

public interface IUserService {


    Optional<Usuario> findByEmailOptional(String email);

    void saveUserVerificationToken(Usuario theUser, String verificationToken);


    VerificationToken generateNewVerificationToken(String oldToken);

    void changePassword(Usuario theUser, String newPassword);

    String validatePasswordResetToken(String token);

    Usuario findUserByPasswordToken(String token);

    void createPasswordResetTokenForUser(Usuario user, String passwordResetToken);

    boolean oldPasswordIsValid(Usuario user, String oldPassword);
}
