package com.example.javamailoci.service;

import com.example.javamailoci.model.EmailModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Slf4j
@RequiredArgsConstructor
@Service
public class OCIEmailService {

    @Value("${oci.smtp.username}")
    private String smtpUsername;

    @Value("${oci.smtp.password}")
    private String smtpPassword;

    // Oracle Cloud Infrastructure Email Delivery hostname.
    @Value("${oci.smtp.host}")
    private String host;

    // The port you will connect to on the SMTP endpoint. Port 25 or 587 is allowed.
    @Value("${oci.smtp.port}")
    private int port;

    public void sendMessage(EmailModel email) throws MessagingException, UnsupportedEncodingException {

        Properties props = getProperties();

        // Create a Session object to represent a mail session with the specified properties.
        Session session = Session.getDefaultInstance(props);
        // Create a message with the specified information.
        MimeMessage msg = emailPreparator(email, session);

        Transport transport = getTransport(session);

        try{

            // Send email.
            transport.sendMessage(msg, msg.getAllRecipients());

        }catch (Exception ex) {
            log.info("The email was not sent.");
            log.info("Error message: " + ex.getMessage());
        }finally {
            // Close & terminate the connection.
            transport.close();
        }
    }

    private Transport getTransport(Session session) throws MessagingException {
        // Create a transport.
        Transport transport = session.getTransport();
        // Connect to OCI Email Delivery using the SMTP credentials specified.
        transport.connect(host, smtpUsername, smtpPassword);
        return transport;
    }

    /**
     * Prepares the mail model to send through javamail
     * @param email
     * @param session
     * @return
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    private static MimeMessage emailPreparator(EmailModel email, Session session) throws MessagingException, UnsupportedEncodingException {
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(email.getSender(), email.getSenderName()));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(email.getRecipient()));
        msg.setSubject(email.getSubject());
        msg.setContent(email.getContent(),"text/html");

        log.info("email preparated!");
        return msg;
    }

    /**
     * Set the properties that javamail reads.
     * It is mandatory that the key-value respects the SMTP connection standard.
     * For more
     * @return
     */
    private Properties getProperties() {
        Properties props = System.getProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.username", smtpUsername);
        props.put("mail.smtp.password", smtpPassword);
        props.put("mail.smtp.host", host);

        //props.put("mail.smtp.ssl.enable", "true"); //the default value is false if not set
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.auth.login.disable", "true");  //the default authorization order is "LOGIN PLAIN DIGEST-MD5 NTLM". 'LOGIN' must be disabled since Email Delivery authorizes as 'PLAIN'
        props.put("mail.smtp.starttls.enable", "true");   //TLSv1.2 is required
        props.put("mail.smtp.starttls.required", "true");  //Oracle Cloud Infrastructure required

        log.info("properties setted!");
        return props;
    }
}
