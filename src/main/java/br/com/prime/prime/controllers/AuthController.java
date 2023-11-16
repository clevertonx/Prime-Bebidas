package br.com.prime.prime.controllers;

import br.com.prime.prime.Services.UsuarioService;
import br.com.prime.prime.dto.LoginTokenJWTDTO;
import br.com.prime.prime.dto.UsuarioRequestDTO;
import br.com.prime.prime.dto.UsuarioResponseDTO;
import br.com.prime.prime.event.listener.RegistrationCompleteEvent;
import br.com.prime.prime.event.listener.RegistrationCompleteEventListener;
import br.com.prime.prime.models.Usuario;
import br.com.prime.prime.security.TokenService;
import br.com.prime.prime.security.password.PasswordResetRequest;
import br.com.prime.prime.token.VerificationToken;
import br.com.prime.prime.token.VerificationTokenRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@RestController
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenservice;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RegistrationCompleteEventListener eventListener;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private ApplicationEventPublisher publisher;


    @Operation(summary = "Logar usuario")
    @ApiResponse(responseCode = "201")
    @PostMapping("/usuario/login")
    public ResponseEntity<LoginTokenJWTDTO> login(@RequestBody @Valid UsuarioRequestDTO usuarioRequestDTO) {

        var authenticationToken = new UsernamePasswordAuthenticationToken(usuarioRequestDTO.getEmail(), usuarioRequestDTO.getSenha());
        var authentication = authenticationManager.authenticate(authenticationToken);
        var tokenJWT = tokenservice.gerarToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new LoginTokenJWTDTO(tokenJWT));
    }

    @Operation(summary = "Cadastrar um novo usuario")
    @ApiResponse(responseCode = "201")
    @PostMapping(path = "/usuario/cadastro", consumes = {"application/json"})
    public ResponseEntity<UsuarioResponseDTO> cadastrarUsuario(@RequestBody @Valid UsuarioRequestDTO novoUsuario, HttpServletRequest request) throws Exception {
        UsuarioResponseDTO usuarioResponse = usuarioService.criar(novoUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioResponse);
    }

    @PostMapping("/usuario/password-reset-request")
    public String resetPasswordRequest(@RequestBody PasswordResetRequest passwordResetRequest, final HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
        Optional<Usuario> user = usuarioService.findByEmail(passwordResetRequest.getEmail());
        String passwordResetUrl = "";
        if (user.isPresent()) {
            String passwordResetToken = UUID.randomUUID().toString();
            usuarioService.createPasswordResetTokenForUser(user.get(), passwordResetToken);
            passwordResetUrl = passwordResetEmailLink(user.get(), applicationUrl(request), passwordResetToken);
        }
        return passwordResetUrl;
    }

    private String passwordResetEmailLink(Usuario usuario, String applicationUrl, String passwordResetToken) throws UnsupportedEncodingException, MessagingException {
        String url = applicationUrl + "/usuario/reset-password?token=" + passwordResetToken;
        eventListener.sendPasswordResetVerificationEmail(url);
        log.info("Click the link to reset your password :  {}", url);
        return url;
    }


    @PostMapping("/usuario/reset-password")
    public String resetPassword(@RequestBody PasswordResetRequest passwordResetRequest, @RequestParam("token") String passwordResetToken) {
        String tokenValidationResult = usuarioService.validatePasswordResetToken(passwordResetToken);
        if (!tokenValidationResult.equalsIgnoreCase("valid")) {
            return "Invalid password reset token";
        }
        Usuario user = usuarioService.findUserByPasswordToken(passwordResetToken);
        if (user != null) {
            usuarioService.resetUserPassword(user, passwordResetRequest.getNewPassword());
            return "Password has been successfully";
        }
        return "Invalid password reset token";
    }

    public String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":"
                + request.getServerPort() + request.getContextPath();
    }
}