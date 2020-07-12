package com.centerpoint.servicehelpdesk.util;

import javax.mail.internet.MimeMessage;

public interface EmailServiceInter {

	void sendmail(MimeMessage mimemessage);

}
