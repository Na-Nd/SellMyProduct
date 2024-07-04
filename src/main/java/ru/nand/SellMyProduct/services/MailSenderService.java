package ru.nand.SellMyProduct.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MailSenderService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public String getSender(){
        return sender;
    }


    @Autowired
    public MailSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public String generateAuthCode(){ // Метод для генерации кода
        Random random = new Random();
        StringBuilder code = new StringBuilder(4);

        for(int i=0; i<4; i++){
            int randomNumber = random.nextInt(10);
            code.append(randomNumber);
        }

        return code.toString();
    }


    public void sendEmail(String toEmail, String subject, String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(getSender());
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);

        System.out.println("Mail sent successfully :)");
    }
}
