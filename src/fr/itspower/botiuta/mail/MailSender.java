package fr.itspower.botiuta.mail;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import fr.itspower.botiuta.utils.PrivateData;

// https://www.jmdoudoux.fr/java/dej/chap-javamail.htm
public class MailSender {
	
	private static final MailSender instance = new MailSender();
	
	private Properties props;
	
	private MailSender() {
		this.props = System.getProperties();
		this.props.put("mail.transport.protocol", "smtp");
		this.props.put("mail.smtp.port", PrivateData.MAIL_PORT);
		this.props.put("mail.debug", "true");
		
		this.props.put("mail.smtp.auth", "true");
		this.props.put("mail.smtp.ssl.enable", "true");
		this.props.put("mail.smtp.starttls.enable","true");
	}
	
	public void testMail() {
		
		String BODY = String.join(
	    	    System.getProperty("line.separator"),
	    	    "<h1>coucou</h1>",
	    	    "<p>jej</a>"
	    	);
		
		
		Session session = Session.getDefaultInstance(props);
		session.setDebug(true);
		
        // Create a message with the specified information. 
        MimeMessage msg = new MimeMessage(session);
        
        try {
        	msg.setFrom(new InternetAddress("localhost","contact@itspower.fr"));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress("contact@itspower.fr"));
            msg.setSubject("sujet");
			//msg.setContent(BODY,"text/html");
			msg.setText("testMailContent","utf-8");
			msg.saveChanges();
		} catch (MessagingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        // Add a configuration set header. Comment or delete the 
        // next line if you are not using a configuration set
        //msg.setHeader("X-SES-CONFIGURATION-SET", CONFIGSET);
            
        // Create a transport.
                    
        Transport transport = null;
		// Send the message.
        try
        {

            transport = session.getTransport();
            System.out.println("Sending...");
            
            // Connect to Amazon SES using the SMTP username and password you specified above.
            transport.connect(PrivateData.MAIL_HOST, PrivateData.MAIL_USER, PrivateData.MAIL_PASS);
        	
            // Send the email.
            transport.sendMessage(msg, msg.getAllRecipients());
            System.out.println("Email sent!");
        }
        
        catch (Exception ex) {
            System.out.println("The email was not sent.");
            System.out.println("Error message: " + ex.getMessage());
        }
        finally
        {
            // Close and terminate the connection.
            try {
				transport.close();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}
	
	public static MailSender getInstance() {
		return instance;
	}
	
}
