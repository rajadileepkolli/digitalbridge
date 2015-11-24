package com.digitalbridge.service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbridge.exception.DigitalBridgeException;
import com.digitalbridge.exception.DigitalBridgeExceptionBean;

@RestController
/**
 * <p>
 * Email class.
 * </p>
 *
 * @author rajakolli
 * @version 1:0
 */
public class Email {

	private static final Logger LOGGER = LoggerFactory.getLogger(Email.class);

	@Autowired
	JavaMailSender javaMailSender;

	/**
	 * <p>
	 * sendEmail.
	 * </p>
	 *
	 * @param to a {@link java.lang.String} object.
	 * @param text a {@link java.lang.String} object.
	 * @throws com.digitalbridge.exception.DigitalBridgeException if any.
	 */
	public void sendEmail(String to, String text) throws DigitalBridgeException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
		try {
			helper.setTo(new InternetAddress(to, true));
			helper.setText(text);
			helper.setFrom(new InternetAddress("no-reply@deloitte.com", true));
			javaMailSender.send(mimeMessage);
		}
		catch (MessagingException e) {
			LOGGER.error("MessagingException :: {}", e.getMessage(), e);
			DigitalBridgeExceptionBean bean = new DigitalBridgeExceptionBean();
			bean.setFaultCode("1101");
			bean.setFaultString("MessagingException");
			throw new DigitalBridgeException(bean);
		}
	}
}
