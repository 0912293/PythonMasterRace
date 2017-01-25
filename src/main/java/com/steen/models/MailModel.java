package com.steen.models;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class MailModel implements Model {
    public void sendMail(String recipient, String subject, String body) throws AddressException, MessagingException {
        Properties mailServerProperties;
        Session mailSession;
        MimeMessage mailMessage;

        // Step1
        mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.port", "587");
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");

        // Step2
        mailSession = Session.getDefaultInstance(mailServerProperties, null);
        mailMessage = new MimeMessage(mailSession);
        mailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        mailMessage.setSubject(subject);
        mailMessage.setContent("<a href=\""+body+"\">Invoice</a>", "text/html; charset=utf-8");

        MimeBodyPart messageBodyPart = new MimeBodyPart();

        String html = "Invoice:\n<a href='"+body+"'>invoice</a>";
        messageBodyPart.setText(html, "UTF-8", "html");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        mailMessage.setContent(multipart);

        // Step3
        Transport transport = mailSession.getTransport("smtp");

        // Enter your correct gmail UserID and Password
        // if you have 2FA enabled then provide App Specific Password
        transport.connect("smtp.gmail.com", "defectopusbot", "qOL6w2dSjcN");
        transport.sendMessage(mailMessage, mailMessage.getAllRecipients());
        transport.close();
    }
}
