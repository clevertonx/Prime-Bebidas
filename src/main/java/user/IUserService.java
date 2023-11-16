package user;

import br.com.prime.prime.dto.RegistrationRequest;
import br.com.prime.prime.models.Usuario;
import br.com.prime.prime.token.VerificationToken;

import java.util.List;
import java.util.Optional;

public interface IUserService {


    List<Usuario> getUsers();

    Usuario registerUser(RegistrationRequest request);

    Optional<Usuario> findByEmail(String email);

    void saveUserVerificationToken(Usuario theUser, String verificationToken);

    String validateToken(String theToken);

    VerificationToken generateNewVerificationToken(String oldToken);
    void changePassword(Usuario theUser, String newPassword);

    String validatePasswordResetToken(String token);

    Usuario findUserByPasswordToken(String token);

    void createPasswordResetTokenForUser(Usuario user, String passwordResetToken);

    boolean oldPasswordIsValid(Usuario user, String oldPassword);
}
