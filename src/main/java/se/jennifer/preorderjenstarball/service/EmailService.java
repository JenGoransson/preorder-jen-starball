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
        body.append("Hej ").append(name).append("!\n\n");
        body.append("Tack för din beställning av SwM 2025 Jennifer Göransson Starball!\n\n");
        body.append("Du har beställt:\n");

        for (PreorderItem item : items) {
            body.append("- ").append(item.getQuantity())
                    .append(" st ").append(item.getType()).append("\n");
        }

        body.append("\nNär bollen är klar för leverans återkommer vi med mer information.\n");
        body.append("\nOm du har frågor eller om något blivit fel i din beställning, vänligen skicka ett nytt mail till: preorder.jennifer.goransson@gmail.com\n");

        // ⭐ Kundens mail
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Bekräftelse på din beställning – SwM 2025 Starball");
        message.setText(body.toString());
        mailSender.send(message);

        // ⭐ DITT ADMIN-MAIL (läggs direkt här)
        SimpleMailMessage adminMessage = new SimpleMailMessage();
        adminMessage.setTo("jennifer.goransson001@gmail.com"); // ⭐ Du får mailet
        adminMessage.setSubject("Ny order mottagen – SwM 2025 Starball");

        StringBuilder adminBody = new StringBuilder();
        adminBody.append("Ny order har mottagits.\n\n");
        adminBody.append("Namn: ").append(name).append("\n");
        adminBody.append("Email: ").append(to).append("\n\n");
        adminBody.append("Orderrader:\n");

        for (PreorderItem item : items) {
            adminBody.append("- ").append(item.getQuantity())
                    .append(" st ").append(item.getType()).append("\n");
        }

        adminMessage.setText(adminBody.toString());
        mailSender.send(adminMessage); // ⭐ Du får admin-mailet
    }
}

