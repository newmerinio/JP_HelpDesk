<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/feedback.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/asset/assetComplaint.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/asset/AssetCommonValidation.js"/>"></script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	<div class="head">Online</div><img alt="" src="images/forward.png" style="margin-top:10px; float: left;"><div class="head"> Ticket Lodge</div> 
</div>
<div class="clear"></div>
<div style="overflow:auto; height:450px; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">

<s:form id="formone" name="formone" action="FeedbackViaOnline" theme="css_xhtml"  method="post" enctype="multipart/form-data" >
<s:hidden name="viaFrom" value="online"></s:hidden>
<center><div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;"><div id="errZone" style="float:center; margin-left: 7px"></div></div></center>  
		  <center>
	  	<div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 50%; height: 10%; font-size: small; border: 0px solid red; border-radius: 6px;">
		<div id="errZone" style="float:center; margin-left: 7px"></div>
	  	</div>
	    </center>
		 <div class="newColumn">
		 <span id="mandatoryFields" class="pIds" style="display: none; ">assetId#Asset#D#a,</span>
                <div class="leftColumn">Asset</div>
				<div class="rightColumn">
				    <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				    <s:select  
	                           id="assetId"
                               name="assetid"
                               list="assetDetailList"
                               headerKey="-1"
                               headerValue="Select Asset" 
                               cssClass="select"
	                           cssStyle="width:82%"
	                           onchange="getComplaintType(this.value,'feedType','Select Feedback Type');">
				   </s:select>
				</div>
		 </div>
		 
	      <div class="newColumn">
                <div class="leftColumn">Feedback Type</div>
				<div class="rightColumn">
				    <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				    <s:select  
	                           id="feedType"
                               name="feedtype"
                               list="{'No Data'}"
                               headerKey="-1"
                               headerValue="Select Feedback Type" 
                               cssClass="select"
	                           cssStyle="width:82%"
                               onchange="getCategory('assetId',this.value,'category','Select Category');">
				   </s:select>
				</div>
		 </div>
	      
	      <div class="newColumn">
                <div class="leftColumn">Category</div>
				<div class="rightColumn">
				    <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				    <s:select  
	                           id="category"
                               name="subCatg"
                               list="{'No Data'}"
                               headerKey="-1"
                               headerValue="Select Category" 
                               cssClass="select"
	                           cssStyle="width:82%"
	                           onchange="getFeedBreifViaAjax(this.value);"
	                           >
				   </s:select>
				</div>
		 </div>
		 
		 <div class="newColumn">
		  <div class="leftColumn">Brief</div>
	            <div class="rightColumn">
		            <s:textfield name="feed_brief"  id="feed_brief" cssClass="textField" placeholder="Enter Data" />
	             </div>
      </div>
      
       <div class="newColumn">
	     <div class="leftColumn"></div>
	     	<div class="rightColumn" >
	     		<div style="width: auto; float: left; text-align: left; line-height:25px; margin-right: 10px;">SMS:</div><div style="width: auto; float: left; text-align: left;  line-height:25px; margin-right: 10px; vertical-align: middle; padding-top: 5px;"><s:checkbox name="recvSMS" id="recvSMS"></s:checkbox></div>
	     		<div style="width: auto; float: left; text-align: left; line-height:25px; margin-right: 10px; margin-left: 20px;">Email:</div><div style="width: auto; float: left; text-align: left;  line-height:25px; margin-right: 10px; vertical-align: middle; padding-top: 5px;"><s:checkbox name="recvEmail" id="recvEmail"></s:checkbox></div>
	     	</div>
     </div>
	 
<div class="clear"></div>
   
	 <br>
		 <div class="fields" align="center">
		 <ul>
			<li class="submit" style="background:none;">
			<div class="type-button">
	        <sj:submit 
	                    id="onlineSubmitId"
                        targets="confirmEscalationDialog" 
                        clearForm="true"
                        value="Register" 
                        button="true"
                        onBeforeTopics="validateOnline"
                        onCompleteTopics="beforeFirstAccordian"
                        cssStyle="margin-left: -40px;"
				        />
			            <sj:a cssStyle="margin-left: 200px;margin-top: -45px;" button="true" href="#" onclick="resetForm('formone');">Reset</sj:a>
			            <sj:a cssStyle="margin-left: 7px;margin-top: -45px;" button="true" href="#" onclick="viewAssetData();">View</sj:a>
	        </div>
	        </li>
		 </ul>
		 </div>
	     
	     <div align="center">
			<sj:dialog id="confirmEscalationDialog" autoOpen="false"  closeOnEscape="true" modal="true" title="Confirm Feedback Via Call" width="750" height="100" showEffect="slide" hideEffect="explode" position="['center','top']"></sj:dialog>
         </div>
</s:form>
</div>
</div>
</body>
</html>
