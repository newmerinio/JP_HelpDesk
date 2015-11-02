package com.Over2Cloud.ctrl.feedback.beanUtil;

public class FeedbackReportPojo {

	private String posCount;
	private String negCount;
	
	
	private String deptName;
	private int CFP;
	private int CDT;
	private int CDP;
	private int TS;
	private int CDR;
	
	
	private int pt;
	private int pp;
	private int pn;
	
	private int st;
	private int sto=0;
	private int snr;
	private int sn;
	private int sneg=0;
	private int spos=0;
	
	
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public int getCFP() {
		return CFP;
	}
	public void setCFP(int cfp) {
		CFP = cfp;
	}
	public int getCDT() {
		return CDT;
	}
	public void setCDT(int cdt) {
		CDT = cdt;
	}
	public int getCDP() {
		return CDP;
	}
	public void setCDP(int cdp) {
		CDP = cdp;
	}
	public int getTS() {
		return TS;
	}
	public void setTS(int ts) {
		TS = ts;
	}
	public int getCDR() {
		return CDR;
	}
	public void setCDR(int cdr) {
		CDR = cdr;
	}
	public int getPt() {
		return pt;
	}
	public void setPt(int pt) {
		this.pt = pt;
	}
	public int getPp() {
		return pp;
	}
	public void setPp(int pp) {
		this.pp = pp;
	}
	public int getPn() {
		return pn;
	}
	public void setPn(int pn) {
		this.pn = pn;
	}
	public int getSt() {
		return st;
	}
	public void setSt(int st) {
		this.st = st;
	}
	public int getSnr() {
		return snr;
	}
	public void setSnr(int snr) {
		this.snr = snr;
	}
	public int getSn() {
		return sn;
	}
	public void setSn(int sn) {
		this.sn = sn;
	}
	public String getPosCount() {
		return posCount;
	}
	public void setPosCount(String posCount) {
		this.posCount = posCount;
	}
	public String getNegCount() {
		return negCount;
	}
	public void setNegCount(String negCount) {
		this.negCount = negCount;
	}
	public int getSto()
	{
		return sto;
	}
	public void setSto(int sto)
	{
		this.sto = sto;
	}
	public int getSneg()
	{
		return sneg;
	}
	public void setSneg(int sneg)
	{
		this.sneg = sneg;
	}
	public int getSpos()
	{
		return spos;
	}
	public void setSpos(int spos)
	{
		this.spos = spos;
	}
}
