package com.Over2Cloud.ctrl.wfpm.patient;

public class PatientDashSourcePojo {
private String xAxisId,xAxisName,corp="0",ref="0",walk="0";
private String lead="0",prospect="0",lost="0",existing="0";

public String getLead() {
	return lead;
}

public void setLead(String lead) {
	this.lead = lead;
}

public String getProspect() {
	return prospect;
}

public void setProspect(String prospect) {
	this.prospect = prospect;
}

public String getLost() {
	return lost;
}

public void setLost(String lost) {
	this.lost = lost;
}

public String getExisting() {
	return existing;
}

public void setExisting(String existing) {
	this.existing = existing;
}

public String getxAxisId() {
	return xAxisId;
}

public void setxAxisId(String xAxisId) {
	this.xAxisId = xAxisId;
}

public String getxAxisName() {
	return xAxisName;
}

public void setxAxisName(String xAxisName) {
	this.xAxisName = xAxisName;
}

public String getCorp() {
	return corp;
}

public void setCorp(String corp) {
	this.corp = corp;
}

public String getRef() {
	return ref;
}

public void setRef(String ref) {
	this.ref = ref;
}

public String getWalk() {
	return walk;
}

public void setWalk(String walk) {
	this.walk = walk;
}
}
