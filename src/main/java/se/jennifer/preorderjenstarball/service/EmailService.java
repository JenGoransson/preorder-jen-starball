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

    public void sendConfirmation(String to, String name, String street, String postalCode, String city, List<PreorderItem> items) {

        // ⭐ Kundens mail
        StringBuilder body = new StringBuilder();
        body.append("Hej ").append(name).append("!\n\n");
        body.append("Tack för din beställning av SwM 2025 Jennifer Göransson Starball!\n\n");

        body.append("Leveransadress:\n");
        body.append(street).append("\n");
        body.append(postalCode).append(" ").append(city).append("\n\n");

        body.append("--- Orderinformation ---\n\n");
        body.append("Du har beställt:\n");
        for (PreorderItem item : items) {
            body.append("- ").append(item.getQuantity())
                    .append(" st ").append(item.getType()).append("\n");
        }

        body.append("📦 Leverans & betalning:\n");
        body.append("När bollen är producerad och klar för leverans återkommer vi med betalningsinfo och leveransalternativ. Postpaket eller personlig leverans vid tävlingar är möjligt.\n\n");

        body.append("❓ Frågor:\n");
        body.append("Om något blivit fel i din beställning, vänligen skicka ett mail till: preorder.jennifer.goransson@gmail.com\n");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Bekräftelse på din beställning – SwM 2025 Starball");
        message.setText(body.toString());
       // mailSender.send(message);

        // ⭐ Admin-mail
        StringBuilder adminBody = new StringBuilder();
        adminBody.append("Ny order har mottagits.\n\n");
        adminBody.append("Namn: ").append(name).append("\n");
        adminBody.append("Email: ").append(to).append("\n\n");

        adminBody.append("Adress:\n");
        adminBody.append(street).append("\n");
        adminBody.append(postalCode).append(" ").append(city).append("\n\n");

        adminBody.append("Orderrader:\n");
        for (PreorderItem item : items) {
            adminBody.append("- ").append(item.getQuantity())
                    .append(" st ").append(item.getType()).append("\n");
        }

        SimpleMailMessage adminMessage = new SimpleMailMessage();
        adminMessage.setTo("order.jennifer.goransson@gmail.com");
        adminMessage.setSubject("Ny order mottagen – SwM 2025 Starball");
        adminMessage.setText(adminBody.toString());
       // mailSender.send(adminMessage);
    }
}


