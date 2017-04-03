package SendMail;
import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import Util.PropertyUtil;
public class SimpleMailSender{
	 public boolean sendTextMail(MailSenderInfo mailInfo)
	  {
	    MyAuthenticator authenticator = null;
	    Properties pro = mailInfo.getProperties();
	    if (mailInfo.isValidate())
	    {
	      authenticator = new MyAuthenticator(mailInfo.getUserName(), 
	        mailInfo.getPassword());
	      authenticator.getPasswordAuthentication();
	      System.out.println(authenticator);
	    }
	    Session sendMailSession = 
	      Session.getDefaultInstance(pro, authenticator);
	    System.out.println(sendMailSession);
	    try
	    {
	      Message mailMessage = new MimeMessage(sendMailSession);
	      
	      Address from = new InternetAddress(mailInfo.getFromAddress());
	      
	      System.out.println(from);
	      mailMessage.setFrom(from);
	      
	      Address to = new InternetAddress(mailInfo.getToAddress());
	      System.out.println(to);
	      mailMessage.setRecipient(Message.RecipientType.TO, to);
	      
	      mailMessage.setSubject(mailInfo.getSubject());
	      
	      mailMessage.setSentDate(new Date());
	      
	      String mailContent = mailInfo.getContent();
	      mailMessage.setText(mailContent);
	      
	      System.out.println(mailMessage);
	      Transport.send(mailMessage);
	      return true;
	    }
	    catch (MessagingException ex)
	    {
	      ex.printStackTrace();
	    }
	    return false;
	  }
	  
	  public static boolean sendHtmlMail(MailSenderInfo mailInfo)
	  {
	    MyAuthenticator authenticator = null;
	    Properties pro = mailInfo.getProperties();
	    if (mailInfo.isValidate()) {
	      authenticator = new MyAuthenticator(mailInfo.getUserName(), 
	        mailInfo.getPassword());
	    }
	    Session sendMailSession = 
	      Session.getDefaultInstance(pro, authenticator);
	    try
	    {
	      Message mailMessage = new MimeMessage(sendMailSession);
	      
	      Address from = new InternetAddress(mailInfo.getFromAddress());
	      
	      mailMessage.setFrom(from);
	      
	      Address to = new InternetAddress(mailInfo.getToAddress());
	      
	      mailMessage.setRecipient(Message.RecipientType.TO, to);
	      
	      mailMessage.setSubject(mailInfo.getSubject());
	      
	      mailMessage.setSentDate(new Date());
	      
	      mailMessage.setText(mailInfo.getContent());
	      
	      Multipart mainPart = new MimeMultipart();
	      BodyPart html = new MimeBodyPart();
	     	html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
	    	FileDataSource fds = new FileDataSource(
	      PropertyUtil.getPro("DataSource"));
	      String str = PropertyUtil.getPro("DataSource");
	      File file=new File(str);
	      if(!file.getParentFile().exists()){
	    	  file.getParentFile().mkdir();
	    	  	      }
	      DataHandler dh = new DataHandler(fds);
	      html.setFileName(str.substring(str.lastIndexOf("/") + 1));
	      html.setDataHandler(dh);
	      mainPart.addBodyPart(html);
	      mailMessage.setContent(mainPart);
	      mailMessage.saveChanges();
	      Transport.send(mailMessage);
	      return true;
	    }
	    catch (MessagingException ex)
	    {
	      ex.printStackTrace();
	    }
	    return false;
	  }
}
