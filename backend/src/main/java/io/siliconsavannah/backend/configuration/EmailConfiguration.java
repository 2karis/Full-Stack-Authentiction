package io.siliconsavannah.backend.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfiguration {
//    @Value("${spring.mail.host}")
//    String host;
//
//    @Value("${spring.mail.port}")
//    int port;
//
//    @Value("${spring.mail.username}")
//    String username;
//
//    @Value("${spring.mail.password}")
//    String password;
//
//    @Value("${spring.mail.properties}")
//    Properties javaMailProperties;
    @Bean
    public JavaMailSender mailSender() {
        //        mailSender.setHost(host);
//        mailSender.setPort(port);
//        mailSender.setUsername(username);
//        mailSender.setPassword(password);
//        mailSender.getJavaMailProperties().putAll(javaMailProperties);
        return new JavaMailSenderImpl();
    }

    @Bean
    public SimpleMailMessage passwordResetTemplate() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Password Reset");
        return message;
    }
}
