<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="/struts-jquery-tags" prefix="sj" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<!--  -->
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
	var monthCounter = ${currDate};
	var jsonObject = ${jsonObject};
	var temp = ${temp};
	
</script>
	<script type="text/javascript">
	  
		function showToDate()
			{
			var status = $("#status option:selected").text().trim();
			if(status == "05 Intro Meeting" || status == "06 Intro Presentation")
			{
				document.getElementById("showToDateId").style.display="block";
			}
			else
			{
				document.getElementById("showToDateId").style.display="none";
			}
			}
		function cancelButton()
		 {
		//	 $('#completionResult').dialog('close');
			 back();
		 }
  </script>
<script type="text/javascript" src="js/WFPM/dashboard/beforeTakeAction.js"></script>
</head>
<body>
<sj:dialog
          id="completionResult"
          showEffect="slide" 
    	  hideEffect="explode" 
    	  openTopics="openEffectDialog"
    	  closeTopics="closeEffectDialog"
          autoOpen="false"
          title="Confirmation Message"
          modal="true"
          width="400"
          height="150"
          draggable="true"
    	  resizable="true"
    	   buttons="{ 
    		'Close':function() { cancelButton(); } 
    		}" 
          >
        <sj:div id="resultIdOuter"  effect="fold"><div id="resultId"></div></sj:div>
</sj:dialog>

<div class="border">
<center>
	<div id="clientNameDIV" style="font-size: large; font-weight: bold;">
	</div>
</center>
<br><br>
	
<s:form id="actionForm" namespace="/view/Over2Cloud/wfpm/dashboard" action="dashboardAction" 
	    theme="simple" method="post" enctype="multipart/form-data">
	<div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
             <div id="errZone" style="float:left; margin-left: 7px">
             </div> 
         </div>
         <br>
<!--<sj:dialog
	id="MOMDialog"
	autoOpen="false"
	modal="true"
	width="1000"
	height="600"
	onCompleteTopics="fillMomFields"
></sj:dialog>
	
	--><s:hidden id="clientId" name="clientId"></s:hidden>
	<s:hidden id="contactId" name="contactId"></s:hidden>
	<s:hidden id="offeringId" name="offeringId"></s:hidden>
	<s:hidden id="temp" name="temp" value="%{temp}"></s:hidden>
	<s:hidden id="id" name="id" value="%{id}"></s:hidden>
	<s:hidden id="statusId" name="statusId"></s:hidden>
	<s:hidden id="maxDateTimeOld" name="maxDateTimeOld"></s:hidden>
	
	<s:hidden id="referedby" name="referedby"></s:hidden>
	<s:hidden id="referedbyID" name="referedbyID"></s:hidden>
	<s:hidden id="offeringNameID" name="offeringName"></s:hidden>
	<s:hidden id="currentStatusID" name="currentStatus"></s:hidden>
	<s:hidden id="clientNameID" name="clientName"></s:hidden>
	<s:hidden id="clientAddressID" name="clientAddress"></s:hidden>
                   		<span id="mandatoryFields" class="pIds" style="display: none; ">actionType#Action#D#,</span>
	
	<div class="menubox">
		<div class="clear"></div>
		<div class="newColumn">
			<div class="leftColumn1">Offering:</div>
			<div class="rightColumn1">
			     <s:label name="offName"  id="offName" />
			</div>
		</div>   
		<div class="newColumn">
			<div class="leftColumn1">Contact Person:</div>
			<div class="rightColumn1">
			     <s:label name="cp"  id="contactName" />
			</div>
		</div>   
		<div class="clear"></div>
		
		<div class="newColumn">
			<div class="leftColumn1">Current Status:</div>
			<div class="rightColumn1">
			     <s:label name="cs"  id="currentStatus" />
			</div>
		</div>   
		<div class="newColumn">
			<div class="leftColumn1">Action:</div>
			<div class="rightColumn1"><span class="needed"></span>
			     <s:select
			      list="#{'0':'New Activity','1':'Reschedule','2':'Lost','3':'Reassign','4':'Convert To Existing'}"
			     		   id="actionType"
			     		   name="actionType"	
			     		   cssClass="textField"
			     		   headerKey="-1"			     	
			     		   headerValue="  Select Action"
			     		   onchange="selectAction(this.value);">
			     	</s:select>
			</div>
		</div>   
		<div class="clear"></div>
		<div class="clear"></div>
		
		<div id="resultDiv" Style="display: none;"></div>
		
		<div class="clear"></div>
		<div class="newColumn" id="chkContainer1">
			<div class="leftColumn1">Other Offering Activity:</div>
			<div class="rightColumn1">
			     <s:checkbox name="chkOther" fieldValue="1" onclick="createOtherOfferingActivity(1,this.checked)"></s:checkbox> 
			</div>
		</div>
		<s:if test="%{referedby== 'NA'}">
		<div class="newColumn">
			<div class="leftColumn1"></div>
			<div class="rightColumn1">
			    <b>No Reference Available.</b>
			</div>
		</div>
		</s:if>
		<s:else>
			<div class="newColumn" id="chkContainer2">
			<div class="leftColumn1">Mail To Reference:</div>
			<div class="rightColumn1">
			     <s:checkbox name="chkmail_to_contact" fieldValue="1" onclick="showReferenceContacts(1,this.checked)"></s:checkbox> 
			</div>
		</div>
		<div class="newColumn" style="display: none;" id="showReferenceContactID">
				<div class="leftColumn1">Reference Contact: </div>
				<div class="rightColumn1">
					 <s:select
                      id="referenceID"
                      name="personName"
                      list="referenceContactMap"
	  				  headerKey="-1"
                      headerValue="Select Contact"
                      cssClass="select"
                      value="%{presalesStage}"
                      cssStyle="width:82%" 
                      >
                      </s:select>
				</div>
				</div>
		</s:else>
		<!-- Activity Action -->
		<div id="activityDiv0"></div>
		<!-- Activity Action -->
		
		<div class="clear"></div>
		<div class="newColumn">
			<div class="leftColumn1">Attach Doc.:</div>
			<div class="rightColumn1">
			     <s:file id="attachedDoc" name="attachedDoc"></s:file>
			</div>
		</div>   
		<div class="newColumn">
			<div class="leftColumn1">Fill MOM:</div>
			<div class="rightColumn1">
			     <img alt="star" title="Click To Fill MOM" style="cursor: pointer;" onclick="fillMOM(${id});" 
			          src="images/WFPM/commonDashboard/attach.jpg">
			</div>
		</div>
		
		<!-- MOM -->
		<div id="MOMDialog"></div>
		<!-- MOM -->
		
	</div>
		
	</s:form>
	
		<div class="buttonStyle" style="float:left;">
		      <sj:a 
	       				formIds="actionForm"
	       				targets="resultId"
				     	name="Reset"  
						href="#" 
						cssClass="submit" 
						button="true" 
						onSuccessTopics="completeAction"
						onBeforeTopics="validate1"
					>
					  	Save
					</sj:a>
	          <sj:a 
	          
				     	name="Reset"  
						href="#" 
						cssClass="submit" 
						button="true" 
						onclick="resetForm('actionForm');"
					>
					  	Reset
					</sj:a>
	          <sj:a
	     	    name="Cancel"  
				href="#" 
				cssClass="submit" 
				indicator="indicator" 
				button="true" 
				onclick="back()"
	
	>
	  	Back
	</sj:a>
<sj:div id="resultIdOuter"  effect="fold">
   		<div id="resultId"></div>
 	</sj:div>
<br><br><br>
   	
</div>
</body>
<script type="text/javascript">
fillFields_AUTO();
</script>
</html>