package se.jennifer.preorderjenstarball.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import se.jennifer.preorderjenstarball.dto.PreorderItem;

import java.util.List;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendConfirmation(String to, String name, List<PreorderItem> items) {

        StringBuilder body = new StringBuilder();
        body.append("Hej!").append(name).append(",\n\n");
        body.append("Tack för din beställning av SwM 2025 Jennifer Göransson Starball!\n\n");
        body.append("Du har beställt:\n");

        for (PreorderItem item : items) {
            body.append("- ").append(item.getQuantity())
                    .append(" st ").append(item.getType()).append("\n");
        }

        body.append("\nNär bollen är klar för leverans återkommer vi med mer information.\n");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Bekräftelse på din beställning – SwM 2025 Starball");
        message.setText(body.toString());

        mailSender.send(message);
    }
}

