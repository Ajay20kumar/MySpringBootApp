package com.centerpoint.servicehelpdesk.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.logging.log4j.Logger;

public class EmailSendingManager {
	//static Logger logger = Logger.getLogger(EmailSendingManager.class.getName());

	/**
	 * mailSender() used to send mail
	 * @param mailMapper
	 * @return boolean if true,mail has sent otherwise fail
	 */
	public static boolean mailSender(MailMapper mailMapper) {
		//logger.debug("mailSender() is executing in EmailSendingManager");
		String apikey = SchedulerConstants.EMAIL_API_KEY;
		String myurl = SchedulerConstants.EMAIL_API_URL;
		String to = mailMapper.getTo();
		String from = mailMapper.getFrom();
		String fromname = mailMapper.getFromname();
		String content = mailMapper.getContent();
		String subject = mailMapper.getSubject();
		try {
			String postData = "api_key=" + apikey;
			postData += "&subject=" + URLEncoder.encode(subject, "UTF-8");
			postData += "&fromname=" + URLEncoder.encode(fromname, "UTF-8");
			postData += "&from=" + from;
			postData += "&recipients=" + to;
			postData += "&content=" + URLEncoder.encode(content, "UTF-8");

			String urlParameters = postData;

			URL url = new URL(myurl);
			HttpURLConnection connection;

			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
			connection.setRequestProperty("Content-Language", "en-US");

			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();

			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
		} catch (Exception e) {
			//logger.error("Exception raised in mailSender() of EmailSendingManager", e);
			return false;
		}
		return true;
	}

	public void setTo(String email) {
		String apikey = SchedulerConstants.EMAIL_API_KEY;
		String myurl = SchedulerConstants.EMAIL_API_URL;
		
	}
}
