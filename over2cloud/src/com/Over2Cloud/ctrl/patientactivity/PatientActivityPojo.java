package com.Over2Cloud.ctrl.patientactivity;

import java.util.List;


public class PatientActivityPojo
{
	public String questionId;
	public String questionSet;
	public String questions;
	public String answers;
	public boolean upload;
	public boolean mandatory;
	public boolean subquestion;
	public List<String> optionsList;
	
	public List<PatientActivityPojo> subQuestion;
	public String subquestions;
	private String subquestionId;
	public String subanswers;
	private String sno;
	public boolean subupload;
	public boolean submandatory;
	public List<String> suboptionsList;
	
	public String getQuestionId()
	{
		return questionId;
	}
	public void setQuestionId(String questionId)
	{
		this.questionId = questionId;
	}
	public String getQuestionSet()
	{
		return questionSet;
	}
	public void setQuestionSet(String questionSet)
	{
		this.questionSet = questionSet;
	}
	public String getQuestions()
	{
		return questions;
	}
	public void setQuestions(String questions)
	{
		this.questions = questions;
	}
	public String getAnswers()
	{
		return answers;
	}
	public void setAnswers(String answers)
	{
		this.answers = answers;
	}
	public boolean isUpload()
	{
		return upload;
	}
	public void setUpload(boolean upload)
	{
		this.upload = upload;
	}
	public boolean isMandatory()
	{
		return mandatory;
	}
	public void setMandatory(boolean mandatory)
	{
		this.mandatory = mandatory;
	}
	public boolean isSubquestion()
	{
		return subquestion;
	}
	public void setSubquestion(boolean subquestion)
	{
		this.subquestion = subquestion;
	}
	public List<String> getOptionsList()
	{
		return optionsList;
	}
	public void setOptionsList(List<String> optionsList)
	{
		this.optionsList = optionsList;
	}
	public List<PatientActivityPojo> getSubQuestion()
	{
		return subQuestion;
	}
	public void setSubQuestion(List<PatientActivityPojo> subQuestion)
	{
		this.subQuestion = subQuestion;
	}
	public String getSubquestions()
	{
		return subquestions;
	}
	public void setSubquestions(String subquestions)
	{
		this.subquestions = subquestions;
	}
	public String getSubanswers()
	{
		return subanswers;
	}
	public void setSubanswers(String subanswers)
	{
		this.subanswers = subanswers;
	}
	public boolean isSubupload()
	{
		return subupload;
	}
	public void setSubupload(boolean subupload)
	{
		this.subupload = subupload;
	}
	public boolean isSubmandatory()
	{
		return submandatory;
	}
	public void setSubmandatory(boolean submandatory)
	{
		this.submandatory = submandatory;
	}
	public List<String> getSuboptionsList()
	{
		return suboptionsList;
	}
	public void setSuboptionsList(List<String> suboptionsList)
	{
		this.suboptionsList = suboptionsList;
	}
	public String getSubquestionId()
	{
		return subquestionId;
	}
	public void setSubquestionId(String subquestionId)
	{
		this.subquestionId = subquestionId;
	}
	public void setSno(String sno) {
		this.sno = sno;
	}
	public String getSno() {
		return sno;
	}
	
	
}