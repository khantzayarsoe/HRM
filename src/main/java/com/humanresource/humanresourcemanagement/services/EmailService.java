package com.humanresource.humanresourcemanagement.services;

import com.humanresource.humanresourcemanagement.exceptions.EmailServiceErrorException;
import com.humanresource.humanresourcemanagement.utils.EmailDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public void sendSimpleMail(EmailDetails details) throws EmailServiceErrorException {

        try {

            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());

            javaMailSender.send(mailMessage);

            System.out.println("mail sending successfully");
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            throw new EmailServiceErrorException(e.getMessage());
        }


    }

}
