package br.com.prime.prime.event.listener;

import br.com.prime.prime.models.Usuario;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import br.com.prime.prime.Services.UserService;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {
    @Autowired
    private UserService userService;
    private final JavaMailSender mailSender;
    private  Usuario theUser;
    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        // 1. Get the newly registered user
        theUser = event.getUser();
        //2. Create a verification token for the user
        String verificationToken = UUID.randomUUID().toString();
        //3. Save the verification token for the user
        userService.saveUserVerificationToken(theUser, verificationToken);
        //4 Build the verification url to be sent to the user
        String url = event.getApplicationUrl()+"/register/verifyEmail?token="+verificationToken;
        //5. Send the email.
        try {
            sendVerificationEmail(url);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        log.info("Clique no link para verificar seu cadastro :  {}", url);
    }
    public void sendVerificationEmail(String url) throws MessagingException, UnsupportedEncodingException {
        String subject = "Email Verification";
        String senderName = "Serviço do portal de registro de usuários";
        String mailContent = "<p> Olá, "+ theUser.getUsername()+ ", </p>"+
                "<p>Obrigado por se registrar conosco,"+"" +
                "Por favor, siga o link abaixo para concluir seu cadastro.</p>"+
                "<a href=\"" +url+ "\">Verifique seu e-mail para ativar sua conta</a>"+
                "<p> Obrigado <br> Serviço do portal de registro de usuários";
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("clevertonx@gmail.com", senderName);
        messageHelper.setTo(theUser.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }

    public void sendPasswordResetVerificationEmail(String url) throws MessagingException, UnsupportedEncodingException {
        String subject = "Password Reset Request Verification";
        String senderName = "Serviço do portal de registro de usuários";
        String mailContent = "<p> Hi, "+ theUser.getUsername()+ ", </p>"+
                "<p><b>Você solicitou recentemente a redefinição de sua senha,</b>"+"" +
                "Por favor, siga o link abaixo para concluir a ação.</p>"+
                "<a href=\"" +url+ "\">Redefinir senha</a>"+
                "<p> Serviço do portal de registro de usuários";
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("clevertonx@gmail.com", senderName);
        messageHelper.setTo(theUser.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }
}
