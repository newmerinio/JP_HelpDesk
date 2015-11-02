<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/menucontrl.js"/>"></script>
<title>Vehicle Entry Add</title>
<script type="text/javascript">
	$.subscribe('presult', function(event,data)
		       {
		         $("#vehicleaddresult").dialog('open');
		       });
</script>
<script type="text/javascript">
	function resetForm(formId)
	{
		$('#'+formId).trigger("reset");
	}
</script>
<script type="text/javascript">
	function getEntryFields(vehiclestatus) {
		$.ajax({
			type : "post",
		    url : "view/Over2Cloud/VAM/master/vehicleDetailadd.action?modifyFlag=0&deleteFlag=0"+"&vehiclestatus="+vehiclestatus+"&vehicleExitStatus=0",
		    success : function(data) {
	     	  $("#"+"data_part").html(data);
	     	  document.getElementById("showbuttons").style.display="block";
			},
			error: function() {
				alert("error");
			}
			});
	  }
</script>
</head>
<body>
	<sj:dialog id="vehicleaddresult"  showEffect="slide" hideEffect="explode" autoOpen="false" modal="true" title="Vehicle Pass" openTopics="openEffectDialog" closeTopics="closeEffectDialog" modal="true" width="590" height="420"/>
	<div class="list-icon">
	<div class="head"><s:property value="%{mainHeaderName}"/></div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Add</div>
	</div>
	<div class="clear"></div>
	<div style=" float:left; padding:10px 0%; width:100%;">
	<div class="clear"></div>
	<div class="border">
	<div class="container_block">
	<div style=" float:left; padding:20px 1%; width:98%;">
	
			                  
	        <div class="clear"></div>
	        <s:hidden name="vehicleExitStatus" id="vehicleExitStatus" value="%{vehicleExitStatus}"></s:hidden> 
	           			<s:if test="%{dropdownformaterial == true}">
						<div class="newColumn">
						<div class="leftColumn1">Vehicle Status:</div>
						<div class="rightColumn1">
						<s:if test="%{dropdownformaterial == true}"><span class="needed"></span></s:if>
						         <s:radio  name="vehiclestatus" id="vehiclestatus" list="#{'1':'With Material','2':'Without Material'}"  onclick="getEntryFields(this.value);"/>
						</div>
						</div>
         			</s:if>	
         				
					 	
         				<!--<s:hidden name="alert_after"  id="alert_after"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
         				<s:hidden name="sr_number"  id="sr_number" value="%{sr_no}"  cssClass="textField" readonly="true" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
         				<s:hidden name="trip_no"  id="trip_no" value="%{trip}"  cssClass="textField" readonly="true" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
         	--><div class="clear"></div>
			<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
			<center>	
			<div class="fields" id="showbuttons" style="display: none;">
			<div class="buttonStyle" style="width: 212px;">
			<div class="type-button">
	        
			</div>
			</div>
					
			</div>
	   		</center>
	

	</div>
	</div>
	</div>
	</div>
</body>
</html>