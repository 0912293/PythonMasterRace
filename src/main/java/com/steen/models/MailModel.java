package com.steen.models;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class MailModel implements Model {
    public void sendMail(String recipient, String subject, String body) throws AddressException, MessagingException {
        Properties mailServerProperties;
        Session mailSession;
        MimeMessage mailMessage;
        mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.port", "587");
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");
        mailSession = Session.getDefaultInstance(mailServerProperties, null);
        mailMessage = new MimeMessage(mailSession);
        mailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        mailMessage.setSubject(subject);
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        String html = "<a href='"+body+"'>"+body+"</a>";
        messageBodyPart.setText(html, "UTF-8", "html");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        mailMessage.setContent(multipart);
        Transport transport = mailSession.getTransport("smtp");
        transport.connect("smtp.gmail.com", "steensupp0rt", "***");
        transport.sendMessage(mailMessage, mailMessage.getAllRecipients());
        transport.close();
    }
}
