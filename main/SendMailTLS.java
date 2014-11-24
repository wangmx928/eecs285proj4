package eecs285.proj4;


import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class SendMailTLS {
 
    public void emailMe(String addressList, String ip) {
 
        final String username = "playlistmanagerinvites@gmail.com";
        final String password = "eecs285project";
 
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
 
        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
          });
 
        try {
 
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.BCC,
                InternetAddress.parse(addressList));
            message.setSubject("You have been invited to request songs for a playlist!");
            String messageText = "Hello,\n\n" + "You have been invited "
                    + "to request songs for a playlist.  In order to do so, "
                    + "open up the playlist manager program, select 'Submit a "
                    + "Request for an Existing Playlist', and type in the "
                    + "provided IP Address and Port Number\n\n"
                    + "IP Address: " + ip + "\n\n"
                    + "Have fun!";
            message.setText(messageText);
 
            Transport.send(message);
 
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}