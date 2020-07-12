package com.centerpoint.servicehelpdesk.util;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService  implements EmailServiceInter{
	
	@Autowired
    private JavaMailSenderImpl emailSender;
	
	@Override
	public void sendmail(MimeMessage mimemessage) {
	        emailSender.send(mimemessage);
	}
}
