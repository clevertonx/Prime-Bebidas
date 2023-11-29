package br.com.prime.prime.controllers;

import br.com.prime.prime.dto.RegistrationRequest;
import br.com.prime.prime.event.listener.RegistrationCompleteEvent;
import br.com.prime.prime.event.listener.RegistrationCompleteEventListener;
import br.com.prime.prime.models.Usuario;
import br.com.prime.prime.security.password.PasswordRequestUtil;
import br.com.prime.prime.token.VerificationToken;
import br.com.prime.prime.token.VerificationTokenRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;
import br.com.prime.prime.Services.UserService;

import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
@CrossOrigin("*")
public class RegistrationController {

    private final UserService userService;
    private final ApplicationEventPublisher publisher;
    private final VerificationTokenRepository tokenRepository;
    private final RegistrationCompleteEventListener eventListener;
    private final HttpServletRequest servletRequest;
    @Operation(summary = "Cadastrar um novo usuario")
    @ApiResponse(responseCode = "201")
    @PostMapping
    public String registrarUsuario(@RequestBody RegistrationRequest registrationRequest, final HttpServletRequest request){
        Usuario user = userService.registrarUsuario(registrationRequest);
        publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));
        return "Sucesso! Por favor, verifique seu e-mail para concluir seu cadastro";
    }
    @Operation(summary = "Verificar Email")
    @ApiResponse(responseCode = "200")
    @GetMapping("/verifyEmail")
    public String sendVerificationToken(@RequestParam("token") String token){

        String url = applicationUrl(servletRequest)+"/register/resend-verification-token?token="+token;

        VerificationToken theToken = tokenRepository.findByToken(token);
        if (theToken.getUser().isEnabled()){
            return "\n" +
                    "Esta conta já foi verificada, por favor, faça login.";
        }
        String verificationResult = userService.validarToken(token);
        if (verificationResult.equalsIgnoreCase("valid")){
            return "\n" +
                    "E-mail verificado com sucesso. Agora você pode acessar sua conta";
        }
        return "\n" +
                "Link de verificação inválido, <a href=\"" +url+"\"> Obtenha um novo link de verificação. </a>";
    }

    @Operation(summary = "reenviar token de verificação")
    @ApiResponse(responseCode = "200")
    @GetMapping("/resend-verification-token")
    public String resendVerificationToken(@RequestParam("token") String oldToken,
                                          final HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        VerificationToken verificationToken = userService.gerarNovoTokenDeVerificacao(oldToken);
        Usuario theUser = verificationToken.getUser();
        reenviarEmailDoTokenDeVerificacaoDeRegistro(theUser, applicationUrl(request), verificationToken);
        return "Um novo link de verificação foi enviado para seu e-mail," +
                " por favor, cheque o email para ativar sua conta";
    }
    private void reenviarEmailDoTokenDeVerificacaoDeRegistro(Usuario theUser, String applicationUrl,
                                                          VerificationToken verificationToken) throws MessagingException, UnsupportedEncodingException {
        String url = applicationUrl+"/register/verifyEmail?token="+verificationToken.getToken();
        eventListener.sendVerificationEmail(url);
        log.info("Clique no link para verificar seu cadastro :  {}", url);
    }

    @Operation(summary = "solicitação de redefinição de senha")
    @ApiResponse(responseCode = "201")
    @PostMapping("/password-reset-request")
    public String resetPasswordRequest(@RequestBody PasswordRequestUtil passwordRequestUtil,
                                       final HttpServletRequest servletRequest)
            throws MessagingException, UnsupportedEncodingException {

        Optional<Usuario> user = userService.findByEmail(passwordRequestUtil.getEmail());
        String passwordResetUrl = "";
        if (user.isPresent()) {
            String passwordResetToken = UUID.randomUUID().toString();
            userService.criarTokenDeRedefinicaoDeSenhaParaUsuario(user.get(), passwordResetToken);
            passwordResetUrl = LinkDeEmailParaRedefinicaoDeSenha(user.get(), applicationUrl(servletRequest), passwordResetToken);
        }
        return passwordResetUrl;
    }

    private String LinkDeEmailParaRedefinicaoDeSenha(Usuario user, String applicationUrl,
                                          String passwordToken) throws MessagingException, UnsupportedEncodingException {
        String url = applicationUrl+"/register/reset-password?token="+passwordToken;
        eventListener.sendPasswordResetVerificationEmail(url);
        log.info("Clique no link para redefinir sua senha :  {}", url);
        return url;
    }
    @Operation(summary = "redefinir senha")
    @ApiResponse(responseCode = "201")
    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody PasswordRequestUtil passwordRequestUtil,
                                @RequestParam("token") String token){
        String tokenVerificationResult = userService.validarTokenDeRedefinicaoDeSenha(token);
        if (!tokenVerificationResult.equalsIgnoreCase("valid")) {
            return "Token de redefinição de senha de token inválido";
        }
        Optional<Usuario> theUser = Optional.ofNullable(userService.encontrarUsuarioPorTokenDeSenha(token));
        if (theUser.isPresent()) {
            userService.alterarSenha(theUser.get(), passwordRequestUtil.getNewPassword());
            return "A senha foi redefinida com sucesso";
        }
        return "Token de redefinição de senha inválido";
    }
    @Operation(summary = "Alterar Senha")
    @ApiResponse(responseCode = "201")
    @PostMapping("/change-password")
    public String changePassword(@RequestBody PasswordRequestUtil requestUtil){
        Usuario user = userService.findByEmail(requestUtil.getEmail()).get();
        if (!userService.oldPasswordIsValid(user, requestUtil.getOldPassword())){
            return "Senha antiga incorreta";
        }
        userService.alterarSenha(user, requestUtil.getNewPassword());
        return "Senha alterada com sucesso";
    }

    public String applicationUrl(HttpServletRequest request) {
        return "http://"+request.getServerName()+":"
                +request.getServerPort()+request.getContextPath();
    }
}