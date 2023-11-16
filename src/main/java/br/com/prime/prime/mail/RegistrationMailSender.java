package br.com.prime.prime.mail;

import br.com.prime.prime.event.listener.RegistrationCompleteEvent;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.UnsupportedEncodingException;

@RequiredArgsConstructor
public class RegistrationMailSender {
    private final JavaMailSender mailSender;
    private final RegistrationCompleteEvent event;

    public void sendVerificationEmail(String url) throws MessagingException, UnsupportedEncodingException {

        String subject = "Email Verification";
        String senderName = "Serviço do portal de registro de usuários";
        String mailContent = "<p> Olá, "+ event.getUser().getEmail()+ ", </p>"+
                "<p>Obrigado por se registrar conosco,"+"" +
                "Por favor, siga o link abaixo para concluir seu cadastro.</p>"+
                "<a href=\"" +url+ "\">Verifique seu e-mail para ativar sua conta</a>"+
                "<p> Obrigado <br> Serviço do portal de registro de usuários";
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("clevertonx@gmail.com", senderName);
        messageHelper.setTo(event.getUser().getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }
}
