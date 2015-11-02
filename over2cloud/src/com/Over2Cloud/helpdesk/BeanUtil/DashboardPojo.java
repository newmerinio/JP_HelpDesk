package com.Over2Cloud.helpdesk.BeanUtil;

import java.util.List;

public class DashboardPojo {

	private String id;
	private String pc="0";
	private String sc="0";
	private String hpc="0";
	private String igc="0";
	private String rc="0";
	private String deptName;
	private String subDeptName;
	private String level;
	
	
	private List<DashboardPojo> dashList=null;
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPc() {
		return pc;
	}
	public void setPc(String pc) {
		this.pc = pc;
	}
	public String getSc() {
		return sc;
	}
	public void setSc(String sc) {
		this.sc = sc;
	}
	public String getHpc() {
		return hpc;
	}
	public void setHpc(String hpc) {
		this.hpc = hpc;
	}
	public String getIgc() {
		return igc;
	}
	public void setIgc(String igc) {
		this.igc = igc;
	}
	public String getRc() {
		return rc;
	}
	public void setRc(String rc) {
		this.rc = rc;
	}
	public List<DashboardPojo> getDashList() {
		return dashList;
	}
	public void setDashList(List<DashboardPojo> dashList) {
		this.dashList = dashList;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getSubDeptName() {
		return subDeptName;
	}
	public void setSubDeptName(String subDeptName) {
		this.subDeptName = subDeptName;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
}
