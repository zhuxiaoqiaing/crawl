package SendMail;
import Util.PropertyUtil;
public class TimingMail
{
  public static void Send(){
    MailSenderInfo mailInfo = new MailSenderInfo();
    SimpleMailSender sms = new SimpleMailSender();
    //System.out.println("mailserver host:"+PropertyUtil.getPro("MailServerHost"));
    mailInfo.setMailServerHost(PropertyUtil.getPro("MailServerHost"));
    mailInfo.setMailServerPort(PropertyUtil.getPro("MailServerPort"));
    mailInfo.setValidate(true);
    mailInfo.setUserName(PropertyUtil.getPro("UserName"));
    mailInfo.setPassword(PropertyUtil.getPro("Password"));
    
    mailInfo.setFromAddress(PropertyUtil.getPro("FromAddress"));
    
    mailInfo.setToAddress(PropertyUtil.getPro("ToAddress"));
    
    mailInfo.setSubject(PropertyUtil.getPro("Subject"));
    
    mailInfo.setContent(PropertyUtil.getPro("buffer"));
    
    //sms.sendTextMail(mailInfo);
    
   SimpleMailSender.sendHtmlMail(mailInfo);
    
    System.out.println("邮件发送完毕");
  }
}
