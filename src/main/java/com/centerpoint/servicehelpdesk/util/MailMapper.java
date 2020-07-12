package com.centerpoint.servicehelpdesk.util;

/**
 *
 *
 */
public class MailMapper {

	private String to;
	private String from;
	private String fromname;
	private String content;
	private String subject;
	
	/**
	 * @return the to
	 */
	public String getTo() {
		return to;
	}
	/**
	 * @param to the to to set
	 */
	public void setTo(String to) {
		this.to = to;
	}
	/**
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}
	/**
	 * @param from the from to set
	 */
	public void setFrom(String from) {
		this.from = from;
	}
	/**
	 * @return the fromname
	 */
	public String getFromname() {
		return fromname;
	}
	/**
	 * @param fromname the fromname to set
	 */
	public void setFromname(String fromname) {
		this.fromname = fromname;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
}