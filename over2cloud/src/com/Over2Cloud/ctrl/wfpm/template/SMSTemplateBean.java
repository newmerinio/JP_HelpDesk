package com.Over2Cloud.ctrl.wfpm.template;

public class SMSTemplateBean {
	private int id;
	private String User;
	private String date;
	private String time;
	private String paramName;
	private String shortCodes;
	private String validation;
	private String length;
	private String revertMessage;
	private String generatedTemplate;
	private String keyword="NA";
	private String userName="NA";
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUser() {
		return User;
	}
	public void setUser(String user) {
		User = user;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public String getShortCodes() {
		return shortCodes;
	}
	public void setShortCodes(String shortCodes) {
		this.shortCodes = shortCodes;
	}
	public String getValidation() {
		return validation;
	}
	public void setValidation(String validation) {
		this.validation = validation;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public String getRevertMessage() {
		return revertMessage;
	}
	public void setRevertMessage(String revertMessage) {
		this.revertMessage = revertMessage;
	}
	public String getGeneratedTemplate() {
		return generatedTemplate;
	}
	public void setGeneratedTemplate(String generatedTemplate) {
		this.generatedTemplate = generatedTemplate;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	public String getUserName()
	{
		return userName;
	}
	
}
