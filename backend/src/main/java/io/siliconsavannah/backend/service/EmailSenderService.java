package io.siliconsavannah.backend.service;

import io.siliconsavannah.backend.configuration.EmailConfiguration;
import io.siliconsavannah.backend.model.PasswordResetToken;
import io.siliconsavannah.backend.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailSenderService {
    @Autowired
    private EmailConfiguration emailConfiguration;


    public void sendPasswordResetEmail(PasswordResetToken passwordResetToken , User user) throws RuntimeException {

        SimpleMailMessage msg = new SimpleMailMessage(emailConfiguration.passwordResetTemplate());
        msg.setTo(user.getEmail());
        msg.setText(
                "Dear " + user.getFirstname()
                        +  user.getLastname()
                        + ", click the link below to reset your password: http://localhost:8080/reset-password/"
                        + passwordResetToken.getToken());
        try {
            emailConfiguration.mailSender().send(msg);
        }
        catch (MailException ex) {
            log.error(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
    }
}
