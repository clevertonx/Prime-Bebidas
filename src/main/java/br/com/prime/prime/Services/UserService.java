package br.com.prime.prime.Services;

import br.com.prime.prime.Exceptions.UserAlreadyExistsException;
import br.com.prime.prime.dto.RegistrationRequest;
import br.com.prime.prime.models.Usuario;
import br.com.prime.prime.repository.UserRepository;
import br.com.prime.prime.repository.UsuarioRepository;
import br.com.prime.prime.security.password.PasswordResetTokenService;
import br.com.prime.prime.token.VerificationToken;
import br.com.prime.prime.token.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import user.IUserService;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository tokenRepository;
    private final PasswordResetTokenService passwordResetTokenService;


    @Override
    public List<Usuario> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public Usuario registerUser(RegistrationRequest request) {
        Optional<Usuario> user = this.findByEmail(request.email());
        if (user.isPresent()){
            throw new UserAlreadyExistsException(
                    "User with email "+request.email() + " already exists");
        }
        var newUser = new Usuario();
        newUser.setEmail(request.email());
        newUser.setSenha(passwordEncoder.encode(request.senha()));
        return userRepository.save(newUser);
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveUserVerificationToken(Usuario theUser, String token) {
        var verificationToken = new VerificationToken(token, theUser);
        tokenRepository.save(verificationToken);
    }
    @Override
    public String validateToken(String theToken) {
        VerificationToken token = tokenRepository.findByToken(theToken);
        if(token == null){
            return "Invalid verification token";
        }
        Usuario user = token.getUser();
        Calendar calendar = Calendar.getInstance();
        if ((token.getExpirationTime().getTime()-calendar.getTime().getTime())<= 0){
            return "Verification link already expired," +
                    " Please, click the link below to receive a new verification link";
        }
        user.setEnabled(true);
        userRepository.save(user);
        return "valid";
    }

    @Override
    public VerificationToken generateNewVerificationToken(String oldToken) {
        VerificationToken verificationToken = tokenRepository.findByToken(oldToken);
        var verificationTokenTime = new VerificationToken();
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationToken.setExpirationTime(verificationTokenTime.getTokenExpirationTime());
        return tokenRepository.save(verificationToken);
    }

    public void changePassword(Usuario theUser, String newPassword) {
        theUser.setSenha(passwordEncoder.encode(newPassword));
        userRepository.save(theUser);
    }

    @Override
    public String validatePasswordResetToken(String token) {
        return passwordResetTokenService.validatePasswordResetToken(token);
    }

    @Override
    public Usuario findUserByPasswordToken(String token) {
        return passwordResetTokenService.findUserByPasswordToken(token).get();
    }

    @Override
    public void createPasswordResetTokenForUser(Usuario user, String passwordResetToken) {
        passwordResetTokenService.createPasswordResetTokenForUser(user, passwordResetToken);
    }
    @Override
    public boolean oldPasswordIsValid(Usuario user, String oldPassword){
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }
}
