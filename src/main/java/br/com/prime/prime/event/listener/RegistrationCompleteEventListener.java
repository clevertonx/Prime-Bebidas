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
        theUser = event.getUser();
        String verificationToken = UUID.randomUUID().toString();
        userService.salvarTokenDeVerificacaoDoUsuario(theUser, verificationToken);
        String url = event.getApplicationUrl()+"/register/verifyEmail?token="+verificationToken;
        try {
            sendVerificationEmail(url);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        log.info("Clique no link para verificar seu cadastro :  {}", url);
    }
    public void sendVerificationEmail(String url) throws MessagingException, UnsupportedEncodingException {
        String subject = "Email Verification";
        String senderName = "Prime Bebidas";
        String mailContent = "<p> Olá, "+ theUser.getUsername()+ ", </p>"+
                "<p>Obrigado por se registrar conosco,"+"" +
                "Por favor, siga o link abaixo para concluir seu cadastro.</p>"+
                "<a href=\"" +url+ "\">Verifique seu e-mail para ativar sua conta</a>"+
                "<p> Obrigado <br> Prime Bebidas";
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
        String senderName = "Prime Bebidas";
        String mailContent = "<p> Hi, "+ theUser.getUsername()+ ", </p>"+
                "<p><b>Você solicitou recentemente a redefinição de sua senha,</b>"+"" +
                "Por favor, siga o link abaixo para concluir a ação.</p>"+
                "<a href=\"" +url+ "\">Redefinir senha</a>"+
                "<p> Prime Bebidas";
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("clevertonx@gmail.com", senderName);
        messageHelper.setTo(theUser.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }
}
