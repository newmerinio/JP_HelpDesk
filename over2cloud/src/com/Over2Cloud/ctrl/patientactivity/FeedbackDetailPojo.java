
package com.Over2Cloud.ctrl.patientactivity;

import java.util.List;

public class FeedbackDetailPojo {

	private String date;
	private String time;
	private String name;
	private String setNo;
	private String report,sections;
	private List<PatientActivityPojo> answerlist;
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSetNo() {
		return setNo;
	}
	public void setSetNo(String setNo) {
		this.setNo = setNo;
	}
	public List<PatientActivityPojo> getAnswerlist() {
		return answerlist;
	}
	public void setAnswerlist(List<PatientActivityPojo> answerlist) {
		this.answerlist = answerlist;
	}
	public String getReport() {
		return report;
	}
	public void setReport(String report) {
		this.report = report;
	}
	public void setSections(String sections) {
		this.sections = sections;
	}
	public String getSections() {
		return sections;
	}
	
}