/**====================================================================
 * 
 * Filename: Mail.java
 * Purpose:	 Class which is used for generating the data for Email
 * Classes:  No Inner Classes
 *======================================================================
 */
package me.anichakra.poc.pilot.framework.notification.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonWriteNullProperties;

/**
 * <p>Purpose: Class which is used for generating the data for Email  </p>
 * @author TCS
 * @version 1.0
 */

public class Mail implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@JsonProperty( "from" )
	private String from;
	
	@JsonProperty( "replyTo" )
	private String replyTo;
	
	@JsonProperty( "toList" )
	private List<String> toList;
	
	@JsonProperty( "ccList" )
	private List<String> ccList;
	
	@JsonProperty( "subject" )
	private String subject;
	
	@JsonProperty( "msgBody" )
	private String msgBody;
	
	@JsonProperty( "templateName" )
	private String templateName ;
	
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getReplyTo() {
		return replyTo;
	}

	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}

	public List<String> getToList() {
		if(toList == null){
			return new ArrayList<String>();
		}
		return toList;
	}

	public void setToList(List<String> toList) {
		this.toList = toList;
	}


	public List<String> getCcList() {
		if(ccList == null){
			return new ArrayList<String>();
		}
		return ccList;
	}

	public void setCcList(List<String> ccList) {
		this.ccList = ccList;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMsgBody() {
		return msgBody;
	}

	public void setMsgBody(String msgBody) {
		this.msgBody = msgBody;
	}
	
	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

}