package br.com.estudo.message_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;
    private final String warning="\nThis email is a test email, you shold not recive this\n" +
            "Contact the email owner";
    @Value(value = "${spring.mail.username}")
    private String emailSender;

    public String sendUserCreatedEmail(String toEmail, String name) {
            SimpleMailMessage message = new SimpleMailMessage();
            String text="You're welcome to our service. Mr/Ms "+name+warning;

            message.setFrom(emailSender);
            message.setTo(toEmail);
            message.setSubject("Welcome " + name+"(test)");
            message.setText(text);

            mailSender.send(message);

            return text;
    }

    public String sendOrderPaidEmail(String toEmail, String name) {
        SimpleMailMessage message = new SimpleMailMessage();
        String text="Your order was paid. Mr/Ms "+name+warning;

        message.setFrom(emailSender);
        message.setTo(toEmail);
        message.setSubject("Order paid " + name+"(test)");
        message.setText(text);

        mailSender.send(message);

        return text;
    }

    public String sendDeliveryUpdateEmail(String toEmail, String status) {
        SimpleMailMessage message = new SimpleMailMessage();
        String text="You delivery status was updated. Mr/Ms "+status+warning;

        message.setFrom(emailSender);
        message.setTo(toEmail);
        message.setSubject("Delivery updated " + status+"(test)");
        message.setText(text);

        mailSender.send(message);

        return text;
    }
}
