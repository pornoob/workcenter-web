package workcenter.util.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import workcenter.util.components.Constantes;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by claudio on 30-10-14.
 */
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MailSender {
    private Properties properties;

    @Autowired
    private Constantes constantes;

    public void send(String[] destinos, String asunto, String mensaje) {
        if (properties == null) writeProperties();
        Session session = Session.getDefaultInstance(properties);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(properties.get("mail.smtp.user").toString()));
            InternetAddress[] toAddress = new InternetAddress[destinos.length];

            // To get the array of addresses
            for( int i = 0; i < destinos.length; i++ ) {
                toAddress[i] = new InternetAddress(destinos[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(asunto);
            message.setContent(mensaje, "text/html");
            Transport transport = session.getTransport("smtp");
            transport.connect(
                    properties.getProperty("mail.smtp.host").toString()
                    , properties.get("mail.smtp.user").toString()
                    , properties.get("mail.smtp.password").toString()
            );
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }

    private void writeProperties() {
        properties = new Properties();
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", constantes.getServidorCorreo());
        properties.put("mail.smtp.user", constantes.getCorreo());
        properties.put("mail.smtp.password", constantes.getClaveCorreo());
        properties.put("mail.smtp.port", constantes.getPuertoCorreo());
        properties.put("mail.smtp.auth", "true");

    }
}
