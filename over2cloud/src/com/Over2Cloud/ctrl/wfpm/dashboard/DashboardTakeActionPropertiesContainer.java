package com.Over2Cloud.ctrl.wfpm.dashboard;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.Over2Cloud.ctrl.wfpm.common.SessionProviderClass;

/**
 * @author Anoop Properties for class 'DashboardTakeAction' so as to remove
 *         complexity from there
 */
class DashboardTakeActionPropertiesContainer extends SessionProviderClass
{

	protected int									id;
	protected String							contactId;
	protected String							offeringId;
	protected String							clientId;
	protected String							statusId;
	protected String							temp;
	protected JSONArray						jsonArray;
	protected JSONArray						jsonArrayOther;
	protected ActivityType				activityType;
	protected JSONObject					jsonObject;
	protected String							currDate;
	protected Map<String, String>	clientStatus;
	protected String							comment;
	protected String							maxDateTime;
	protected String							poNumber;
	protected String							poDate;
	protected ActionType					actionType;
	protected CloseType						closeType;
	protected String							RCA;
	protected String							CAPA;
	protected Map<String, String>	empMap;
	protected String							lostId;
	protected String							empId;
	protected String							statusIdNew;
	protected String							maxDateTimeOld;
	protected Map<String, String>	statusMap;
	protected Map<String, String>	offeringMap;
	protected int									count;
	protected Map<String, String>	contactMap;
	protected String							chkOther;
	protected String							statusIdOther;
	protected String							offeringIdOther;
	protected String							maxDateTimeOther;
	protected String							commentOther;

	protected String							dateMOM;
	protected String							fromTimeMOM;
	protected String							toTimeMO;
	protected String							agendaMOM;

	protected String							clientContactMOM;
	protected String							employeeMOM;
	protected String							discussionMOM;
	protected String							futureActionMOM;
	protected File								attachedDoc;
	protected String							attachedDocFileName;
	protected String							attachedDocContentType;
	protected String	comments;
	protected String	po_attachFileName;
	protected String	po_attachContentType;
	protected File po_attach;
	protected String	ammount;
	protected ArrayList<ArrayList<String>> missedActivityList;
	
	protected String chkmail_to_contact;
	protected Map<String, String> referenceContactMap;
	protected String personName;
	protected String referedby;
	protected String referedbyID;
	protected String clientName;
	protected String offeringName;
	protected String currentStatus;
	protected String clientAddress;

	public String getRCA()
	{
		return RCA;
	}

	public void setRCA(String rca)
	{
		RCA = rca;
	}

	public String getCAPA()
	{
		return CAPA;
	}

	public void setCAPA(String capa)
	{
		CAPA = capa;
	}

	public Map<String, String> getEmpMap()
	{
		return empMap;
	}

	public void setEmpMap(Map<String, String> empMap)
	{
		this.empMap = empMap;
	}

	public String getLostId()
	{
		return lostId;
	}

	public void setLostId(String lostId)
	{
		this.lostId = lostId;
	}

	public String getEmpId()
	{
		return empId;
	}

	public void setEmpId(String empId)
	{
		this.empId = empId;
	}

	public String getStatusIdNew()
	{
		return statusIdNew;
	}

	public void setStatusIdNew(String statusIdNew)
	{
		this.statusIdNew = statusIdNew;
	}

	public String getMaxDateTimeOld()
	{
		return maxDateTimeOld;
	}

	public void setMaxDateTimeOld(String maxDateTimeOld)
	{
		this.maxDateTimeOld = maxDateTimeOld;
	}

	public Map<String, String> getStatusMap()
	{
		return statusMap;
	}

	public void setStatusMap(Map<String, String> statusMap)
	{
		this.statusMap = statusMap;
	}

	public Map<String, String> getOfferingMap()
	{
		return offeringMap;
	}

	public void setOfferingMap(Map<String, String> offeringMap)
	{
		this.offeringMap = offeringMap;
	}

	public int getCount()
	{
		return count;
	}

	public void setCount(int count)
	{
		this.count = count;
	}

	public JSONArray getJsonArrayOther()
	{
		return jsonArrayOther;
	}

	public void setJsonArrayOther(JSONArray jsonArrayOther)
	{
		this.jsonArrayOther = jsonArrayOther;
	}

	public Map<String, String> getContactMap()
	{
		return contactMap;
	}

	public void setContactMap(Map<String, String> contactMap)
	{
		this.contactMap = contactMap;
	}

	public String getChkOther()
	{
		return chkOther;
	}

	public void setChkOther(String chkOther)
	{
		this.chkOther = chkOther;
	}

	public String getStatusIdOther()
	{
		return statusIdOther;
	}

	public void setStatusIdOther(String statusIdOther)
	{
		this.statusIdOther = statusIdOther;
	}

	public String getOfferingIdOther()
	{
		return offeringIdOther;
	}

	public void setOfferingIdOther(String offeringIdOther)
	{
		this.offeringIdOther = offeringIdOther;
	}

	public String getMaxDateTimeOther()
	{
		return maxDateTimeOther;
	}

	public void setMaxDateTimeOther(String maxDateTimeOther)
	{
		this.maxDateTimeOther = maxDateTimeOther;
	}

	public String getCommentOther()
	{
		return commentOther;
	}

	public void setCommentOther(String commentOther)
	{
		this.commentOther = commentOther;
	}

	public String getDateMOM()
	{
		return dateMOM;
	}

	public void setDateMOM(String dateMOM)
	{
		this.dateMOM = dateMOM;
	}

	public String getFromTimeMOM()
	{
		return fromTimeMOM;
	}

	public void setFromTimeMOM(String fromTimeMOM)
	{
		this.fromTimeMOM = fromTimeMOM;
	}

	public String getToTimeMO()
	{
		return toTimeMO;
	}

	public void setToTimeMO(String toTimeMO)
	{
		this.toTimeMO = toTimeMO;
	}

	public String getClientContactMOM()
	{
		return clientContactMOM;
	}

	public void setClientContactMOM(String clientContactMOM)
	{
		this.clientContactMOM = clientContactMOM;
	}

	public String getEmployeeMOM()
	{
		return employeeMOM;
	}

	public void setEmployeeMOM(String employeeMOM)
	{
		this.employeeMOM = employeeMOM;
	}

	public String getAgendaMOM()
	{
		return agendaMOM;
	}

	public void setAgendaMOM(String agendaMOM)
	{
		this.agendaMOM = agendaMOM;
	}

	public String getDiscussionMOM()
	{
		return discussionMOM;
	}

	public void setDiscussionMOM(String discussionMOM)
	{
		this.discussionMOM = discussionMOM;
	}

	public String getFutureActionMOM()
	{
		return futureActionMOM;
	}

	public void setFutureActionMOM(String futureActionMOM)
	{
		this.futureActionMOM = futureActionMOM;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getContactId()
	{
		return contactId;
	}

	public void setContactId(String contactId)
	{
		this.contactId = contactId;
	}

	public String getOfferingId()
	{
		return offeringId;
	}

	public void setOfferingId(String offeringId)
	{
		this.offeringId = offeringId;
	}

	public String getTemp()
	{
		return temp;
	}

	public void setTemp(String temp)
	{
		this.temp = temp;
		if (temp.equals("0")) activityType = ActivityType.client;
		else if (temp.equals("1")) activityType = ActivityType.associate;
	}

	public JSONArray getJsonArray()
	{
		return jsonArray;
	}

	public void setJsonArray(JSONArray jsonArray)
	{
		this.jsonArray = jsonArray;
	}

	public ActivityType getActivityType()
	{
		return activityType;
	}

	public void setActivityType(ActivityType activityType)
	{
		this.activityType = activityType;
	}

	public JSONObject getJsonObject()
	{
		return jsonObject;
	}

	public void setJsonObject(JSONObject jsonObject)
	{
		this.jsonObject = jsonObject;
	}

	public String getClientId()
	{
		return clientId;
	}

	public void setClientId(String clientId)
	{
		this.clientId = clientId;
	}

	public String getCurrDate()
	{
		return currDate;
	}

	public void setCurrDate(String currDate)
	{
		this.currDate = currDate;
	}

	public Map<String, String> getClientStatus()
	{
		return clientStatus;
	}

	public void setClientStatus(Map<String, String> clientStatus)
	{
		this.clientStatus = clientStatus;
	}

	public String getComment()
	{
		return comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}

	public String getStatusId()
	{
		return statusId;
	}

	public void setStatusId(String statusId)
	{
		this.statusId = statusId;
	}

	public String getMaxDateTime()
	{
		return maxDateTime;
	}

	public void setMaxDateTime(String maxDateTime)
	{
		this.maxDateTime = maxDateTime;
	}

	public String getPoNumber()
	{
		return poNumber;
	}

	public void setPoNumber(String poNumber)
	{
		this.poNumber = poNumber;
	}

	public String getPoDate()
	{
		return poDate;
	}

	public void setPoDate(String poDate)
	{
		this.poDate = poDate;
	}

	public CloseType getCloseType()
	{
		return closeType;
	}

	public void setCloseType(String closeType)
	{
		if (closeType.equals("0")) this.closeType = CloseType.NEXT_ACTIVITY;
		else if (closeType.equals("1")) this.closeType = CloseType.CLOSE_ACTIVITY;
	}

	public ActionType getActionType()
	{
		return actionType;
	}

	public void setActionType(String actionType)
	{
		if (actionType.equals("0")) this.actionType = ActionType.ACTIVITY;
		else if (actionType.equals("1")) this.actionType = ActionType.RESCHEDULE;
		if (actionType.equals("2")) this.actionType = ActionType.LOST;
		else if (actionType.equals("3")) this.actionType = ActionType.REASSIGN;
		else if (actionType.equals("4")) this.actionType = ActionType.CONVERT_TO_EXISTING;
	}

	public File getAttachedDoc()
	{
		return attachedDoc;
	}

	public void setAttachedDoc(File attachedDoc)
	{
		this.attachedDoc = attachedDoc;
	}

	public String getAttachedDocFileName()
	{
		return attachedDocFileName;
	}

	public void setAttachedDocFileName(String attachedDocFileName)
	{
		this.attachedDocFileName = attachedDocFileName;
	}

	public String getAttachedDocContentType()
	{
		return attachedDocContentType;
	}

	public void setAttachedDocContentType(String attachedDocContentType)
	{
		this.attachedDocContentType = attachedDocContentType;
	}

	public String getComments()
	{
		return comments;
	}

	public void setComments(String comments)
	{
		this.comments = comments;
	}

	public String getPo_attachFileName()
	{
		return po_attachFileName;
	}

	public void setPo_attachFileName(String po_attachFileName)
	{
		this.po_attachFileName = po_attachFileName;
	}

	public String getPo_attachContentType()
	{
		return po_attachContentType;
	}

	public void setPo_attachContentType(String po_attachContentType)
	{
		this.po_attachContentType = po_attachContentType;
	}

	public File getPo_attach()
	{
		return po_attach;
	}

	public void setPo_attach(File po_attach)
	{
		this.po_attach = po_attach;
	}

	public String getAmmount()
	{
		return ammount;
	}

	public void setAmmount(String ammount)
	{
		this.ammount = ammount;
	}

	public void setMissedActivityList(ArrayList<ArrayList<String>> missedActivityList)
	{
		this.missedActivityList = missedActivityList;
	}

	public ArrayList<ArrayList<String>> getMissedActivityList()
	{
		return missedActivityList;
	}

	public String getChkmail_to_contact() {
		return chkmail_to_contact;
	}

	public void setChkmail_to_contact(String chkmail_to_contact) {
		this.chkmail_to_contact = chkmail_to_contact;
	}

	public Map<String, String> getReferenceContactMap() {
		return referenceContactMap;
	}

	public void setReferenceContactMap(Map<String, String> referenceContactMap) {
		this.referenceContactMap = referenceContactMap;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getReferedby() {
		return referedby;
	}

	public void setReferedby(String referedby) {
		this.referedby = referedby;
	}

	public String getReferedbyID() {
		return referedbyID;
	}

	public void setReferedbyID(String referedbyID) {
		this.referedbyID = referedbyID;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getOfferingName() {
		return offeringName;
	}

	public void setOfferingName(String offeringName) {
		this.offeringName = offeringName;
	}

	public String getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}

	public String getClientAddress() {
		return clientAddress;
	}

	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
	}
	
}
