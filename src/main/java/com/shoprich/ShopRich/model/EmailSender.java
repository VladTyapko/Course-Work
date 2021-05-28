package com.shoprich.ShopRich.model;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailSender implements Runnable {
    String recipient;
    String textToSend;

    public EmailSender(String recipient, String textToSend) {
        this.recipient = recipient;
        this.textToSend = textToSend;
    }

    public static void sendEmail(String recipient, String textToSend) throws MessagingException {
        System.out.println("Preparing message!");
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        String myEmail = "shoprich3@gmail.com";
        String password = "privet123qwaszx";
        String subject = "ShopRich";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myEmail, password);
            }
        });
        Message message = prepareMessage(session, myEmail, recipient, subject,
                textToSend);
        assert message != null;
        Transport.send(message);

    }

    private synchronized static Message prepareMessage(Session session, String myEmail, String recipient,
                                          String subject, String text) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(subject);
            message.setText(text);
            return message;
        } catch (MessagingException ignored) {
        }
        return null;
    }

    @Override
    public void run() {
        try {
            sendEmail(recipient, textToSend);
        } catch (MessagingException ignored) {
        }
    }
}
