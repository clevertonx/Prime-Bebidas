package br.com.prime.prime.controllers;

import br.com.prime.prime.event.listener.RegistrationCompleteEventListener;
import br.com.prime.prime.models.Usuario;
import br.com.prime.prime.security.password.PasswordRequestUtil;
import br.com.prime.prime.token.VerificationTokenRepository;
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
public class RegistrationController {

    private final UserService userService;
    private final ApplicationEventPublisher publisher;
    private final VerificationTokenRepository tokenRepository;
    private final RegistrationCompleteEventListener eventListener;
    private final HttpServletRequest servletRequest;


    @PostMapping("/password-reset-request")
    public String resetPasswordRequest(@RequestBody PasswordRequestUtil passwordRequestUtil,
                                       final HttpServletRequest servletRequest)
            throws MessagingException, UnsupportedEncodingException {

        Optional<Usuario> user = userService.findByEmailOptional(passwordRequestUtil.getEmail());
        String passwordResetUrl = "";

        if (user.isPresent()) {
            String passwordResetToken = UUID.randomUUID().toString();

            // Log the generated token
            log.info("Generated password reset token for user {}: {}", user.get().getEmail(), passwordResetToken);

            try {
                userService.createPasswordResetTokenForUser(user.get(), passwordResetToken);
                passwordResetUrl = passwordResetEmailLink(user.get(), applicationUrl(servletRequest), passwordResetToken);
            } catch (Exception e) {
                // Log the exception for further investigation
                log.error("Error creating password reset token for user {}: {}", user.get().getEmail(), e.getMessage(), e);
            }
        }

        return passwordResetUrl;
    }

    private String passwordResetEmailLink(Usuario user, String applicationUrl,
                                          String passwordToken) throws MessagingException, UnsupportedEncodingException {
        String url = applicationUrl + "/register/reset-password?token=" + passwordToken;
        eventListener.sendPasswordResetVerificationEmail(url);
        log.info("Click the link to reset your password :  {}", url);
        return url;
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody PasswordRequestUtil passwordRequestUtil,
                                @RequestParam("token") String token) {
        String tokenVerificationResult = userService.validatePasswordResetToken(token);
        if (!tokenVerificationResult.equalsIgnoreCase("valid")) {
            return "Invalid token password reset token";
        }
        Optional<Usuario> theUser = Optional.ofNullable(userService.findUserByPasswordToken(token));
        if (theUser.isPresent()) {
            userService.changePassword(theUser.get(), passwordRequestUtil.getNewPassword());
            return "Password has been reset successfully";
        }
        return "Invalid password reset token";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestBody PasswordRequestUtil requestUtil) {
        Usuario user = userService.findByEmailOptional(requestUtil.getEmail()).get();
        if (!userService.oldPasswordIsValid(user, requestUtil.getOldPassword())) {
            return "Incorrect old password";
        }
        userService.changePassword(user, requestUtil.getNewPassword());
        return "Password changed successfully";
    }

    public String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":"
                + request.getServerPort() + request.getContextPath();
    }
}