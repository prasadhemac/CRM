package com.ksoft.crm.core;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
/**
 * Created by prasadh on 11/25/2016.
 */
public class EmailSender {

    public EmailSender() {

    }
    public boolean SendTheEmail(String to, String heading, String text)
    {
        // Recipient's email ID needs to be mentioned.
        //String to = "mntprasad@gmail.com";

        // Sender's email ID needs to be mentioned
        String from = "dilinichathuranik@gmail.com";
        String password = "";
        // Assuming you are sending email from localhost
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);
        properties.put("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.user", from);
        properties.setProperty("mail.smtp.password", password);
        properties.setProperty("mail.smtp.auth", "true");

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties,new GMailAuthenticator(from, password));

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject(heading);

            // Now set the actual message
            message.setText(text);

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
            return true;
        }catch (MessagingException mex) {
            mex.printStackTrace();
        }
        return false;
    }
}

class GMailAuthenticator extends Authenticator {
    String user;
    String pw;
    public GMailAuthenticator (String username, String password)
    {
        super();
        this.user = username;
        this.pw = password;
    }
    public PasswordAuthentication getPasswordAuthentication()
    {
        return new PasswordAuthentication(user, pw);
    }
}