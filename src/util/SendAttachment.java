package util;

import java.util.*;  
import javax.mail.*;  
import javax.mail.internet.*;  
import javax.activation.*;  
  
class SendAttachment{  
 public static void main(String [] args){  
  
  String to="kapilgyanvihar@gmail.com";//change accordingly  
  final String user="kapilgyanvihar@gmail.com";//change accordingly  
  final String password="Kapildevsharmamika";//change accordingly  
   
  //1) get the session object     
  Properties props = System.getProperties(); 
	props.put("mail.smtp.host", "smtp.gmail.com");
	props.put("mail.smtp.port", "25");
	// props.put("mail.debug", "true");
	props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.starttls.enable", "true");
	props.put("mail.smtp.EnableSSL.enable", "true");
	props.setProperty("mail.smtp.socketFactory.class",	"javax.net.ssl.SSLSocketFactory");
	props.setProperty("mail.smtp.socketFactory.fallback", "false");
	props.setProperty("mail.smtp.port", "465");
	props.setProperty("mail.smtp.socketFactory.port", "465");
	
  
  
  Session session = Session.getDefaultInstance(props,  
   new javax.mail.Authenticator() {  
   protected PasswordAuthentication getPasswordAuthentication() {  
   return new PasswordAuthentication(user,password);  
   }  
  });  
     
  //2) compose message     
  try{  
    MimeMessage message = new MimeMessage(session);  
    message.setFrom(new InternetAddress(user));  
    message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
    message.setSubject("Message Aleart");  
      
    //3) create MimeBodyPart object and set your message text     
    BodyPart messageBodyPart1 = new MimeBodyPart();  
    messageBodyPart1.setText("This is message body");  
      
    //4) create new MimeBodyPart object and set DataHandler object to this object      
    MimeBodyPart messageBodyPart2 = new MimeBodyPart();  
  
    /*String filename = "SendAttachment.java";//change accordingly  
    DataSource source = new FileDataSource(filename);  
    messageBodyPart2.setDataHandler(new DataHandler(source));  
    messageBodyPart2.setFileName(filename);*/  
    String filePath = "D:/Testing/";
	String filename = "aboutUs-97.gif";
	DataSource source = new FileDataSource(filePath+filename);
	messageBodyPart2.setDataHandler(new DataHandler(source));
	messageBodyPart2.setFileName(filename);

     
    //5) create Multipart object and add MimeBodyPart objects to this object      
    Multipart multipart = new MimeMultipart();  
    multipart.addBodyPart(messageBodyPart1);  
    multipart.addBodyPart(messageBodyPart2);  
  
    //6) set the multiplart object to the message object  
    message.setContent(multipart );  
     
    //7) send message  
    Transport.send(message);  
   
   System.out.println("message sent....");  
   }catch (MessagingException ex) {ex.printStackTrace();}  
 }  
}  
