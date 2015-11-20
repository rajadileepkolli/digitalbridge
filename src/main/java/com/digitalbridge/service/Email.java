package com.digitalbridge.service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RestController;

@RestController
/**
 * <p>Email class.</p>
 *
 * @author rajakolli
 * @version 1:0
 */
public class Email {

  @Autowired JavaMailSender javaMailSender;
  
  /**
   * <p>sendEmail.</p>
   *
   * @param to a {@link java.lang.String} object.
   * @param text a {@link java.lang.String} object.
   */
  public void sendEmail(String to, String text){
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
    try {
      helper.setTo(new InternetAddress(to, true));
      helper.setText(text);
      helper.setFrom(new InternetAddress("no-reply@deloitte.com", true));
      javaMailSender.send(mimeMessage);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
    
  }
}
