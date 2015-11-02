<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjr" uri="/struts-jquery-richtext-tags"%>

<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/communication/CommunicationScheduleMessage.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>
<script type="text/javascript" src="<s:url value="js/WFPM/CRM/sendPage.js"/>"></script>
<link href="js/multiselect/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
<script>
$(document).ready(function(){
$("#employeeCHK").multiselect();
});

$.subscribe('level1', function(event,data)
{
	$("#confirmContent").dialog("open");
});
$.subscribe('level2', function(event,data)
{
	$('#formtwo').trigger("reset");
});
function cancelButton()
{
	$("#confirmContent").dialog("close");
}
function saveButton()
{
	var entityType = $("#entityTypeNEW").val();
	var employee = $("#employeeNEW").val();
	var mobileNo = $("#mobileNoNEW").val();
	var root = $("#rootNEW").val();
	var templateid = $("#templateidNEW").val();
	var frequencys = $("#frequencysNEW").val();
	var writeMessage = $("#writeMessageNEW").val();
	var date = $("#dateNEW").val();
	var hours = $("#hoursNEW").val();
	var day = $("#dayNEW").val();
	var entityContacts = $("#entityContactsNEW").val();

	var URL = encodeURI("view/Over2Cloud/WFPM/CRM/sendMessage.action?entityType="+entityType+"&employee="+employee+"&mobileNo="+mobileNo+"&root="+root+"&templateid="+templateid+"&frequencys="+frequencys+"&writeMessage="+writeMessage+"&date="+date+"&hours="+hours+"&day="+day+"&entityContacts="+entityContacts);
	$.ajax({
	    type : "post",
	    url : URL, 
		success : function(data) {   
		$("#confirmContent").html(data);
	},
	   error: function() {
            alert("error");
        }
	 });
}

</script>
</head>
<body>
<div class="clear"></div>
<div class="list-icon">
	<!-- <div class="head">Add Prospective Client</div> -->
	<div class="head">SMS</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:60%; float: left;"></div><div class="head">Send</div>
	</div>


<div class="container_block">
<div style=" float:left; padding:20px 1%; width:90%;">
       <s:form id="formtwo" name="formtwo" namespace="/view/Over2Cloud/WFPM/CRM" action="insertDataInstantMsg" 
       		theme="simple"  method="post" >
       		
       		<s:hidden name="entityType" value="%{entityType}"></s:hidden>
       		<s:hidden name="entityContacts" value="%{entityContacts}"></s:hidden>
		       <div class="clear"></div>	
				   <div class="newColumn">
						<div class="leftColumn1">Employees:</div>
						<div class="rightColumn"> <span class="needed"></span>
						<s:select 
                              id="employeeCHK"
                              name="employee" 
                              list="employeeList"  
                              cssClass="select"
                              multiple="true"
                              
                              >
                  </s:select>
				 </div>
				 </div>

				   <div class="newColumn">
					<div class="leftColumn1">Mobile:</div>
					<div class="rightColumn"> <span class="needed"></span>
						<s:textfield name="mobileNo"  id="mobileNo" onkeyup="mobileValidate1()"  cssClass="textField" placeholder="Enter Data in CSV Format" 
							  cssStyle="width:72%" />
				 </div>
				 </div>

				   <div class="newColumn">
				    <span id="mandatoryFields" class="pIds" style="display: none; ">root#Message Template Route#D#a,</span>
						<div class="leftColumn1">Template Route:</div>
						<div class="rightColumn"> <span class="needed"></span>
						<s:select 
                              id="root"
                              name="root" 
                              list="messagerootList"  
                              headerKey="-1"
                              headerValue="-Select Route Type-" 
                              cssClass="select"
                              cssStyle="width:75%"
                              onchange="fetchTemplate(this.value,'templateidss');"
                              >
                  </s:select>
				 </div>
				 </div>
				 
	                  <div class="newColumn">
						<div class="leftColumn1">Template Name:</div>
						<div class="rightColumn">
						<s:select 
                               name="templateid" 
                               id="templateidss"
                               list="#{'-1':'-Select Template Name-'}" 
                              headerKey="-1-"
                              cssClass="select"
                              cssStyle="width:74%"
                              onchange="getMessageText(this.value)"
                              >
                  </s:select>
				 </div>
				 </div>
				
<div class="clear"></div>
		  <div class="newColumn">
     	  <div class="leftColumn1">Schedule Time:</div>
          <div class="rightColumn" style="width:51%;" >
          <span class="needed"></span>
      		 <s:radio list="#{'One-Time':'One-Time','Daily':'Daily','Weekly':'Weekly','Monthly':'Monthly','Yearly':'Yearly','None':'None'}" 
      		 	name="frequencys" id="frequencys" onclick="changeFrequency(this.value,'dateDiv','dayDiv','timeDiv','dateDiv1')" 
      		 	value="'None'" />
          </div>
          </div>
       
					<div class="clear"></div>
							<div id=dateDiv style="display: none">
							<div class="newColumn">
							<div class="leftColumn1">Date</div>
							<div class="rightColumn">
								  <span class="needed"></span>
       								<sj:datepicker id="date" name="date" readonly="true" placeholder="Enter Date" showOn="focus" displayFormat="yy-mm-dd"  cssClass="textField" />
      						 </div>
							</div>
							</div>
							
							<div id=dateDiv1 style="display: none">
							<div class="newColumn">
							<div class="leftColumn1">Date</div>
							<div class="rightColumn">
								  <span class="needed"></span>
      							 <sj:datepicker id="dates" name="date" readonly="true" placeholder="Enter Date" showOn="focus" displayFormat="yy-mm-dd" cssClass="textField" />
     						  </div>
							</div>
							</div>
							
						<div id=timeDiv style="display: none">	
					     <div class="newColumn">
							<div class="leftColumn1">Time:</div>
							<div class="rightColumn">
						    <span class="needed"></span>
      					 		<sj:datepicker id="hours" name="hours" readonly="true" placeholder="Enter Time" showOn="focus" timepicker="true" timepickerOnly="true"  timepickerFormat="hh:mm:ss" cssClass="textField"/>
      						 </div>
							</div></div>
							
							<div id=dayDiv style="display: none">
							<div class="newColumn">
							<div class="leftColumn1">Day:</div>
							<div class="rightColumn">
								  <span class="needed"></span>
								  <s:select 
                              id="day"
                              name="day" 
                              list="#{'Monday':'Monday','Tuesday':'Tuesday','Wednesday':'Wednesday','Thursday':'Thursday','Friday':'Friday','Saturday':'Saturday'}"
                              headerKey="Sunday"
                              headerValue="Sunday" 
                              cssClass="select"
                              cssStyle="width:82%"
                              >
                   </s:select>
      					 </div>
							</div></div>
						

<div class="clear"></div>	
           <div id="destAjaxDiv">
           </div>
           <div id="divid">
                   <div class="newColumn">
					<div class="leftColumn1">Message:</div>
					<div class="rightColumn"><span class="needed"></span>
				    	<s:textarea name="writeMessage" id="writeMessage" cols="95" rows="20" class="form_menu_inputtext" 
				    		onkeydown="textCounter(this.form.writeMessage,this.form.remLen,160)" style="margin: 0px 0px 10px; width: 600px; height: 50px;"/>
			    		<div style="float: left;"><input readonly type="text" id="remLen" name="remLen" size=3 maxlength="3" value="0" ></div>
					</div>
				     </div>
		   </div>
					   	   
<div class="form_menubox">
			<b>Note :</b> For Transactional Template
			Dynamic Fields should be placed inside <> and Length should be placed inside() 
			<a id="hlOpenMe" href="#" onclick="abc();">Example</a></div>
				<div class="clear"></div>	
				
                
		<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
			<div class="type-button" style="margin-left: -200px;">
			<center>

			<sj:submit 
          			   targets="confirmContent" 
                       clearForm="true"
                       value="Send" 
                       effect="highlight"
                       effectOptions="{ color : '#222222'}"
                       effectDuration="5000"
                       button="true"
                       onBeforeTopics="level1"
                       onCompleteTopics="level2"
                       cssClass="submit"
                       cssStyle="margin-right: 2px;margin-bottom: 29px;"
                       indicator="indicator2"
         />
         
         <sj:a 
				     	href="#" 
		               	button="true" 
		               	onclick="resetForm('formtwo');"
						cssStyle="margin-top: -28px;"						>
					  	Reset
		   </sj:a>
		   
		   <sj:a 
				     	href="#" 
		               	button="true" 
		               	onclick="cancelMe();"
						cssStyle="margin-right: -137px;margin-top: -28px;"
						>
					  	Back
		   </sj:a>
        
		  </center> 
   </div>

</s:form>  
</div>
</div>
</body>
<sj:dialog
          id="confirmContent"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="Confirmation Page"
          modal="true"
          width="900"
   	      height="430"
          draggable="true"
    	  resizable="true"
    	  	buttons="{ 
				'Save':function() { saveButton(); } ,
				'Back':function() { cancelButton(); } 
				}"
          >
</sj:dialog>
</html>

