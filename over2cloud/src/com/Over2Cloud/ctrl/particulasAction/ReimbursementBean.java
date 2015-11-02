package com.Over2Cloud.ctrl.particulasAction;

public class ReimbursementBean {
	
	private String SNo;
	private String created_date;
    private String particulars;
	private String purpose;
    private String refDocName;
    private String amount;
    private String totalamount;
    int detected_amount=0;
	public String getSNo() {
		return SNo;
	}
	public void setSNo(String no) {
		SNo = no;
	}
	public String getCreated_date() {
		return created_date;
	}
	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}
	public String getParticulars() {
		return particulars;
	}
	public void setParticulars(String particulars) {
		this.particulars = particulars;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getRefDocName() {
		return refDocName;
	}
	public void setRefDocName(String refDocName) {
		this.refDocName = refDocName;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getTotalamount() {
		return totalamount;
	}
	public void setTotalamount(String totalamount) {
		this.totalamount = totalamount;
	}
	public int getDetected_amount() {
		return detected_amount;
	}
	public void setDetected_amount(int detected_amount) {
		this.detected_amount = detected_amount;
	}
    

}
