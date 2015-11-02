<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%
	String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();
%>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true"  jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/compliance/compliance.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>
<link href="js/multiselect/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>
<meta   http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style>
  .red {
      color: red;
      font-size: 2em;
  }
  .blue {
      color: blue;
      font-weight: bold;
  }

	#ctable tr{
		padding: 5px;
	}
	
	#ctable td{
		padding: 5px;
	}
 </style>
<SCRIPT type="text/javascript">
   $.subscribe('makeEffect', function(event,data)
  {
	 setTimeout(function(){ $("#resultComes").fadeIn(); }, 10);
	 setTimeout(function(){ $("#resultComes").fadeOut();}, 4000); 
	//  cancelButton();
	 /*  $('select').find('option:first').attr('selected', 'selected');
		$.ajax({
		    	type : "post",
		    url : "view/Over2Cloud/Compliance/compliance_pages/createConfigurationComplView.action",
		    success : function(data) {
		   $("#"+"data_part").html(data);
		},
		   error: function() {
		        alert("error");
		    }
		 }); 
	 }, 1000);  */
	
  });  
  function backPage()
  {
	  setTimeout(function(){ $("#resultComes").fadeIn(); }, 10);
	  setTimeout(function(){ $("#resultComes").fadeOut();cancelButton();}, 4000); 
  }
 $(document).ready(function()
	 {
	 	$("#emp_id").multiselect({
	 	   show: ["", 200],
	 	   hide: ["explode", 1000]
	 	});
	 	$("#escalation_level_1").multiselect({
	 	   show: ["", 200],
	 	   hide: ["explode", 1000]
	 	});
	 	$("#escalation_level_2").multiselect({
	 	   show: ["", 200],
	 	   hide: ["explode", 1000]
	 	});
	 	$("#escalation_level_3").multiselect({
	 	   show: ["", 200],
	 	   hide: ["explode", 1000]
	 	});
	 	$("#escalation_level_4").multiselect({
	 	   show: ["", 200],
	 	   hide: ["explode", 1000]
	 	});
	 	
	 	$("#emp_id").multiselect({
	 	   show: ["", 200],
	 	   hide: ["explode", 1000]
	 	});
	 	$("#emp_id1").multiselect({
	 	   show: ["", 200],
	 	   hide: ["explode", 1000]
	 	});
	 	
	 });
function getTotalday(selectedDate)
{
	$.ajax({
	type :"post",
	url :"/over2cloud/view/Over2Cloud/Compliance/compliance_pages/getTotalDays.action?selectedDate="+selectedDate,
	success : function(subDeptData)
	{
	$( "#fromDate" ).datepicker( "option", "maxDate", subDeptData);
	    },
	    error : function () {
	alert("Somthing is wrong to get Sub Department");
	}
	});
}
function changeEnable(timeId,ddId)
{
	$( "#"+timeId).prop("disabled", false);
	$( "#"+ddId).prop("disabled", false);
}
function getReminderValue(freqId,remId1,remId2,remId3,divId,duDate)
{
       var dueDate=$("#"+duDate).val();
       var frequency=$("#"+freqId).val();
       var var1=parseInt($("#"+remId1).val());
       var var2=parseInt($("#"+remId2).val());
       var var3=parseInt($("#"+remId3).val());
       var totalReminder=var1+var2+var3;
       $.ajax({
               type :"post",
               url :"/over2cloud/view/Over2Cloud/Compliance/compliance_pages/getReminder.action?frequency="+frequency+"&totalReminder="+totalReminder+"&divId="+divId+"&dueDate="+dueDate,
               success : function(subDeptData)
               {
                       $("#"+divId).html(subDeptData);
           },
           error : function () {
                       alert("Somthing is wrong to get Sub Department");
               }
       });
}
function changeDDValue(dropdownId)
{
	$('#reminder2').empty();
}
function uploadDocument(fileName1)
{
	var fileName = $("#"+fileName1).val();
	$.ajax({
	type :"post",
	url :"/over2cloud/view/Over2Cloud/Compliance/compliance_pages/uploadDocument.action?fileName="+fileName,
	success : function(subDeptData)
	{
	alert("success");
	    },
	    error : function () {
	alert("Somthing is wrong to get Sub Department");
	}
	});
}
function resetCompl(formId)
{
	$('#'+formId).trigger("reset");
	var date=new Date(); 
	var dd = date.getDate();
	var mm = date.getMonth()+1; //January is 0!
	var yyyy = date.getFullYear();
	var ddd;
	var mmm;
	if(dd<10)
	{
		ddd = "0"+dd;
	}
	else
	{
		ddd = dd;
	}
	if(mm<10)
	{
		mmm="0"+mm;
	}
	else
	{
		mmm=mm;
	}
	var finalDate ;
	finalDate = ddd+"-"+mmm+"-"+yyyy;
	document.getElementById("due_date").value =finalDate;
	document.getElementById("file").style.display="none";
	document.getElementById("configCompliance1").style.display="none";
	document.getElementById("configCompliance2").style.display="none";
	document.getElementById("escalationDIV").style.display="none";
	document.getElementById("escalationDIV1").style.display="none";
	document.getElementById("escalationDIV2").style.display="none";
	document.getElementById("escalationDIV3").style.display="none";
	document.getElementById("remToOther").style.display="none";
	document.getElementById("remToBoth").style.display="none";
	document.getElementById("ownerShip").style.display="none";
	document.configCompliance.reminder1.disabled=false;
}
function configureExcel()
{
	 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Compliance/compliance_pages/beforeComplExcelupload.action",
	    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
	            alert("error");
	        }
	 });
}
function CompletionTip() 
{
	var taskId=$("#taskName").val();
	if (taskId=='-1')
	{
	alert("Please Select Task Name First");
	} 
	else 
	{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Compliance/compl_task_page/ViewCompletionTipKRAction.action?dataId="+taskId+"&dataFor=CompletionTip",
	    success : function(data) 
	    {
	$("#completionTipDialog").dialog({title: 'Check List',width: 700,height: 400});
	$("#completionTipDialog").dialog('open');
	$("#completionTipDialog").html(data);
	},
	    error: function()
	    {
	        alert("error");
	    }
	 });
	}
}
function KR(taskId) 
{
	var taskId=$("#taskName").val();
	
	if (taskId=='-1')
	{
	alert("Please Select Task Name First");
	} 
	else 
	{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Compliance/compl_task_page/ViewCompletionTipKRAction.action?dataId="+taskId+"&dataFor=KRName",
	    success : function(data) 
	    {
	$("#completionTipDialog").dialog({title: 'KR Name',width: 400,height: 200});
	$("#completionTipDialog").dialog('open');
	$("#completionTipDialog").html(data);
	},
	    error: function()
	    {
	        alert("error");
	    }
	 });
	}
	
}

function referenceRec() 
{
	var taskId=$("#taskName").val();
	
	if (taskId=='-1')
	{
	alert("Please Select Task Name First");
	} 
	else 
	{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Compliance/compl_task_page/ViewCompletionTipKRAction.action?dataId="+taskId+"&dataFor=RefRec",
	    success : function(data) 
	    {
	$("#completionTipDialog").dialog({title: 'Reference Record',width: 400,height: 200});
	$("#completionTipDialog").dialog('open');
	$("#completionTipDialog").html(data);
	},
	    error: function()
	    {
	        alert("error");
	    }
	 });
	}
}
function showRemindDay(freq,div)
{
	if (freq=='O') 
	{
		$("#"+div).show();
	}
	else 
	{
		$("#"+div).hide();
	}
}

function getTicketSeries(taskType)
{
	$.ajax
	({
	type: "post",
	url: "view/Over2Cloud/Compliance/compl_task_page/getTicketSeries.action?taskId="+taskType,
	 success : function(data) 
	   	{
	 	if(data.IsExist=="No")
	 	{
	 	alert("Please configure ticket series of respected department");
	 	$('#ticketSeriesActiveDiv :input').attr('disabled', true);
	 	$('#ticketSeriesActiveDiv1 :input').attr('disabled', true);
	 	}
	 	else
	 	{
	 	$('#ticketSeriesActiveDiv :input').attr('disabled', false);
	 	$('#ticketSeriesActiveDiv1 :input').attr('disabled', false);
	 	}
	},
	    error: function()
	    {
	        alert("error");
	    }
	});
}

function getWorkingTime(moduleName)
{
	$.ajax({
	    type : "post",
	    url :"/over2cloud/view/Over2Cloud/WorkingHrs/getWorkingTime.action?dataFor="+moduleName,
	    success : function(data) 
	    {
			$("#completionTipDialog").dialog({title: 'Working Hour Details For Operational Task',width: 700,height: 400});
			$("#completionTipDialog").dialog('open');
			$("#completionTipDialog").html(data);
		},
	    error: function()
	    {
	        alert("error");
	    }
	 });
}


function cancelButton()
{
    $('#completionTipDialog').dialog('close');
    $('#completionResult').dialog('close');
    $('#confirmationDialog').dialog('close');
}
function onloadData()
{
	$('#taskType1').append($("<option></option>").attr("value","Configure New").text("Configure New").css( "color", "green"));
}
function addData(targetid)
{
	$('#'+targetid).append('<option value="Configure New" style="color:green;">Configure New</option>');
}
onloadData();

	function openSubmitConfirmation()
	{
		$("#messageDiv111").empty();
		$('#confirmationDialog').dialog('open');
		$('#confirmationDialog').dialog({title: 'Confirmation Summary',width: 700,height: 400});
		var temp='';
			temp+='<table align="center" id="ctable">';
			temp+='<tr style="background: #F6F6F6;"><td><b>Task Type(KRA): </b> '+$("#taskType1 :selected").text()+'</td><td><b>Task Name(KPI): </b>'+$("#taskName :selected").text()+'</td></tr>';
			temp+='<tr><td><b>Due Date: </b> '+$("#due_date").val()+'</td><td><b>Due Time: </b>'+$("#due_time").val()+'</td></tr>';
			temp+='<tr style="background: #F6F6F6;"><td><b>Frequency: </b> '+$("#frequency :selected").text()+'</td>';
			if($("#reminder1").val().trim()=='')
			{
				temp+='<td><b>3rd Reminder: </b>NA</td></tr>';
			}
			else
			{
				temp+='<td><b>3rd Reminder: </b>'+$("#reminder1").val()+'</td></tr>';	
			}
			if($("#reminder2").val().trim()=='')
			{
			}
			else
			{
				temp+='<tr><td><b>2nd Reminder: </b>'+$("#reminder2").val()+'</td>';	
			}
			if($("#reminder3").val().trim()=='')
			{
				temp+='<td></td></tr>';
			}
			else
			{
				temp+='<td><b>1st Reminder: </b>'+$("#reminder3").val()+'</td></tr>';	
			}
			temp+='<tr style="background: #F6F6F6;"><td><b>Due Date Before: </b> '+$("#start_date").val()+'</td><td><b>Start Time: </b>'+$("#start_time").val()+'</td></tr>';
			var allotTo=$("input[name=reminder_for]:checked").val();
			if(allotTo=='remToSelf')
			{
				allotTo="Self";
			}
			else if(allotTo=='remToOther')
			{
				allotTo="Other";
			}
			else if(allotTo=='remToBoth')
			{
				allotTo="Both";
			}
			temp+='<tr><td><b>Notification: </b> '+$("input[name=remind_mode]:checked").val()+'</td><td><b>Allot Type: </b>'+allotTo+'</td></tr>';
			var ownerType=$("input[name=action_type]:checked").val();
			if($("input[name=reminder_for]:checked").val()=='remToSelf')
			{
				temp+='<tr style="background: #F6F6F6;"><td><b>Ownership Type: </b>'+ownerType.substring(0,1).toUpperCase()+ownerType.substring(1)+'</td></tr>';
			}	
			else
			{
				temp+='<tr style="background: #F6F6F6;"><td><b>Ownership Type: </b>'+ownerType.substring(0,1).toUpperCase()+ownerType.substring(1)+'</td><td><b>Allot To: </b> '+$("#emp_id :selected").map(function() { return this.text; }).get().join(',')+'</td></tr>';					
			}	
			if($("#escalation :selected").text()=='Yes')
			{
				temp+='<tr><td><b>L2: </b> '+$("#escalation_level_1 :selected").map(function() { return this.text; }).get().join(',')+'</td><td><b>L2 TAT After: </b>'+$("#l1EscDuration").val()+'</td></tr>';
				if($("#escalation_level_2 :selected").text().trim()=='')
				{
				}
				else
				{
					temp+='<tr style="background: #F6F6F6;"><td><b>L3: </b> '+$("#escalation_level_2 :selected").map(function() { return this.text; }).get().join(',')+'</td><td><b>L3 TAT After: </b>'+$("#l2EscDuration").val()+'</td></tr>';
				}	
				if($("#escalation_level_3 :selected").text().trim()=='')
				{
				}
				else
				{
					temp+='<tr><td><b>L4: </b> '+$("#escalation_level_3 :selected").map(function() { return this.text; }).get().join(',')+'</td><td><b>L4 TAT After: </b>'+$("#l3EscDuration").val()+'</td></tr>';					
				}	
				if($("#escalation_level_4 :selected").text().trim()=='')
				{
				}
				else
				{
					temp+='<tr style="background: #F6F6F6;"><td><b>L5: </b> '+$("#escalation_level_4 :selected").map(function() { return this.text; }).get().join(',')+'</td><td><b>L5 TAT After: </b>'+$("#l4EscDuration").val()+'</td></tr>';
				}	
			}	
			
			temp+='</table>';
			$("#messageDiv111").append(temp); 
		}
	
	function saveButton(id)
	{
		$('#'+id).dialog('close');
		var datastring = $("#configCompliance").serialize();
		$.ajax({
			      type: "POST",
			      url : "view/Over2Cloud/Compliance/compliance_pages/configureCompl.action",
			      data: datastring,
			      dataType: "json",
			      success: function(data) 
			      {
			           $("#completionResult").dialog("open");
			           var a= JSON.stringify(data);
			           var text = $(a).find('span');
			           $("#resultComes").html(text);
			           backPage();
			      },
			      error: function(data)
			      {
			           $("#completionResult").dialog("open");
			           var a= JSON.stringify(data);
			           var text = $(a).find('span');
			           $("#resultComes").html(text);
			           backPage();
			       }
			 });
		 /* document.getElementById("configCompliance").reset(); */
		resetCompl('configCompliance');
    }
	function closeButton(id)
	{
		$('#'+id).dialog('close');
	}
</script>
</head>
<body>

<sj:dialog
          id="confirmationDialog"
          showEffect="slide"
          targets="resultComes" 
          hideEffect="explode"
          autoOpen="false"
          modal="true"
          closeTopics="closeEffectDialog"
          width="700"
          height="400"
          closeOnEscape="true"
          onCompleteTopics="makeEffect"
          buttons="{ 'Save':function() 
          {
           		saveButton(this.id);
          },
          'Cancel':function()
           { 
           		closeButton(this.id); }
           }"
          >
          <div id="messageDiv111"></div>
	</sj:dialog>

	<sj:dialog
          id="completionTipDialog"
          showEffect="slide"
          targets="complTarget" 
          hideEffect="explode"
          autoOpen="false"
          title="tip"
          modal="true"
          closeTopics="closeEffectDialog"
          width="900"
          height="350"
          closeOnEscape="true"
          buttons="{ 
    	'Close':function() { cancelButton(); } 
    	}" 
          >
	</sj:dialog>

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
          cssStyle="overflow:hidden;"
          draggable="true"
    	  resizable="true"
    	   buttons="{ 
    		'Close':function() { cancelButton(); } 
    		}" 
          >
        <s:div id="resultComes" ></s:div>
</sj:dialog>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">
	 Operational Task</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">Configure for</div>   <s:if test="%{checkdept}">
	  	 <s:select
	        id="department"
	        list="deptList"
	        headerKey="-1"
	        headerValue="Select Department"
	        cssClass="button"
	        theme="simple"
			cssStyle="width:12%"
			onchange="commonSelectAjaxCall('otm_task_type','id','task_type','sub_type_id',this.value,'','','task_type','ASC','taskType1','Select Task Type');"
	        >
	        </s:select>
	 </s:if>
	 <div id="test"  class="alignright printTitle">
	 	<sj:a id="sendButton111"  cssClass="button"  cssStyle="margin-top: -45px;"  button="true" title="Configure through Excel"  onclick="configureExcel();">Upload</sj:a>
	   <sj:a id="sendActivity"  cssClass="button" button="true" title="Activity Board" cssStyle="margin-top: -45px;" onclick="activtyBoard();">Activity Board</sj:a>
	</div>	
	 
</div>
<div class="clear"></div>
<div style="overflow-x:hidden; height:460px; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">

   <s:form id="configCompliance" target="resultComes" windowState=""  name="configCompliance" action=""  namespace="/view/Over2Cloud/Compliance/compliance_pages" theme="simple"  method="post" enctype="multipart/form-data" >
        <center>
	  	<div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 50%; height: 10%; font-size: small; border: 0px solid red; border-radius: 6px;">
	<div id="errZone" style="float:center; margin-left: 7px"></div>
	  	</div>
	    </center>
	   
	    <div id="taskNameDIV" style="display:block">
	    <s:hidden id="taskNameDiv11" value="%{deptId}"/>
        <s:iterator value="complianceDropDown" begin="0" end="1" status="status">
          <span id="complSpan" class="pIds" style="display: none; ">taskName#Task Name#D#,</span>
           <span id="complSpan" class="pIds" style="display: none; ">taskType1#Task Type#D#,</span>
	        <s:if test="%{mandatory}">
				<span id="complSpan" class="pIds" style="display: none; "><s:property value="%{field_value}"/>1#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
				<span id="remind2Other" class="rem2otherpIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
				<span id="remind2Both" class="rem2bothpIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
			    
	      	</s:if>
	  	    <s:if test="#status.odd">
			  	<div class="newColumn">
			      <div class="leftColumn"><s:property value="%{field_name}"/>:&nbsp;</div>
			            <div class="rightColumn"><span class="needed"></span>
					        <s:select  
								id="taskType1"
								name="%{field_value}"
						        list="taskTypeMap"
						        headerKey="-1"
						        headerValue="Select Task Type" 
						        cssClass="select"
								cssStyle="width:82%"
						        onchange="getTaskNameDetails(this.value,'taskNameDiv11','taskName');getTicketSeries(this.value)"
						        >
					        </s:select>
			        </div>
			    </div>
	  	    </s:if>
	  	   <s:else>
	  	   <div class="newColumn">
	  	   <div class="leftColumn"><s:property value="%{field_name}"/>:&nbsp;</div>
	       <div class="rightColumn">
	       <span class="needed"></span>
	       <s:select  
				id="taskName"
				name="task_name"
		        list="{'Configure New'}"
		        headerKey="-1"
		        headerValue="Select Task Name" 
		        cssClass="select"
				cssStyle="width:82%"
				onchange="getTaskTypeDetails(this.value);"
		        >
		  </s:select>
	       </div>
	       </div>        
	  	   </s:else>
  	   </s:iterator>
  	   <div class="clear"></div>
  	  
  	   <div id="ticketSeriesActiveDiv1">
		   <s:iterator value="complianceTxtArea">
			   <s:if test="%{mandatory}">
				   <span id="complSpan" class="pIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
			       <span id="remind2Other" class="rem2otherpIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
			       <span id="remind2Both" class="rem2bothpIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
			   </s:if>
			   <s:if test="#status.odd">
				   <div class="newColumn">
			       <div class="leftColumn"><s:property value="%{field_name}"/>:&nbsp;</div>
			       <div class="rightColumn">
			       <s:textarea name="%{field_value}" rows="1" id="%{field_value}"  placeholder="Enter %{field_name}" cssClass="textField"/>
			       </div>
			       </div>
			   </s:if>
			   <s:else>
				   <div class="newColumn">
				   <div class="leftColumn"><s:property value="%{field_name}"/>:&nbsp;</div>
			       <div class="rightColumn">
			       <s:textarea name="%{field_value}" rows="1"  id="%{field_value}"  placeholder="Enter Data " cssClass="textField"/>
			       </div>
			       </div>
			   </s:else>
	  	   </s:iterator>
  	   </div>
  	   </div>
  	   <div id="ticketSeriesActiveDiv">
  	   <s:iterator value="complianceTextBox" begin="0" end="0">
		   <s:if test="%{mandatory}">
		   <span id="complSpan" class="pIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	       <span id="remind2Other" class="rem2otherpIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	       <span id="remind2Both" class="rem2bothpIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
		   </s:if>
		   <s:if test="#status.odd">
		   <div class="newColumn">
	       <div class="leftColumn"><s:property value="%{field_name}"/>:&nbsp;</div>
	       <div class="rightColumn">
	       <s:textfield name="%{field_value}" rows="1" readonly="true" id="%{field_value}"  placeholder="Enter %{field_name}" cssClass="textField"/>
	       </div>
	       </div>
		   </s:if>
		   <s:else>
		   <div id="budgetText">
		   <div class="newColumn">
		   <div class="leftColumn"><s:property value="%{field_name}"/>:&nbsp;</div>
	       <div class="rightColumn">
	       <s:textfield name="%{field_value}" rows="1" readonly="true" id="%{field_value}"  placeholder="Enter Data" cssClass="textField"/>
	       </div>
	       </div>
	       </div>
		   </s:else>
  	   </s:iterator>
       <!-- </div> -->	
  	

  	   <div id="txtAreaHidden" style="display:none">
   	   <s:iterator value="complianceDropDown" begin="0" end="1" status="status">
	       <s:if test="%{mandatory}">
		   <span id="newcomplSpan" class="newpIds" style="display: none; ">new<s:property value="%{field_value}"/>#new<s:property value="%{field_name}"/>#T#sc,</span>
	       <span id="remind2OtherNew" class="rem2othernewpIds" style="display: none; ">new<s:property value="%{field_value}"/>#new<s:property value="%{field_name}"/>#T#sc,</span>
	       <span id="remind2BothNew" class="rem2bothnewpIds" style="display: none; ">new<s:property value="%{field_value}"/>#new<s:property value="%{field_name}"/>#T#sc,</span>
	       </s:if>
	  	   <s:if test="#status.odd">
		   <div class="newColumn">
	       <div class="leftColumn"><s:property value="%{field_name}"/>:&nbsp;</div>
	       <div class="rightColumn"><span class="needed"></span>
	       <s:textfield name="new%{field_value}" id="new%{field_value}"  placeholder="Enter Data" cssClass="textField"/>
	       </div>
	       </div>
		   </s:if>
		   <s:else>
		   <div class="newColumn">
	       <div class="leftColumn"><s:property value="%{field_name}"/>:&nbsp;</div>
	       <div class="rightColumn"><span class="needed"></span>
	       <s:textfield name="new%{field_value}" id="new%{field_value}"  placeholder="Enter Data" cssClass="textField"/>
	       </div>
	       </div>
		   </s:else>
       </s:iterator>
    

  	   <s:iterator value="complianceTxtArea">
	  	   <s:if test="%{mandatory}">
		   <span id="newcomplSpan" class="newpIds" style="display: none; ">new<s:property value="%{field_value}"/>#new<s:property value="%{field_name}"/>#T#sc,</span>
	       <span id="remind2OtherNew" class="rem2othernewpIds" style="display: none; ">new<s:property value="%{field_value}"/>#new<s:property value="%{field_name}"/>#T#sc,</span>
	       <span id="remind2BothNew" class="rem2bothnewpIds" style="display: none; ">new<s:property value="%{field_value}"/>#new<s:property value="%{field_name}"/>#T#sc,</span>
		   </s:if>
		   <s:if test="#status.odd">
		   <div class="newColumn">
	       <div class="leftColumn"><s:property value="%{field_name}"/>:&nbsp;</div>
	       <div class="rightColumn"><span class="needed"></span>
	       <s:textarea name="new%{field_value}" id="new%{field_value}"  placeholder="Enter %{field_name}" cssClass="textField"/>
	       </div>
		   </div>
		   </s:if>
		   <s:else>
		   <div class="newColumn">
	       <div class="leftColumn"><s:property value="%{field_name}"/>:</div>
	       <div class="rightColumn"><span class="needed"></span>
	       <s:textarea name="new%{field_value}" id="new%{field_value}"  placeholder="Enter Data" cssClass="textField"/>
	       </div>
		   </div>
		   </s:else>
  	   </s:iterator>
  	   
  	   <s:iterator value="complianceTextBox" begin="2">
		  <s:if test="%{mandatory}">
		   <span id="newcomplSpan" class="newpIds" style="display: none; ">new<s:property value="%{field_value}"/>#new<s:property value="%{field_name}"/>#T#sc,</span>
	       <span id="remind2OtherNew" class="rem2othernewpIds" style="display: none; ">new<s:property value="%{field_value}"/>#new<s:property value="%{field_name}"/>#T#sc,</span>
	       <span id="remind2BothNew" class="rem2bothnewpIds" style="display: none; ">new<s:property value="%{field_value}"/>#new<s:property value="%{field_name}"/>#T#sc,</span>
	       </s:if>
		  <s:if test="#status.odd">
		   <div class="newColumn">
	       <div class="leftColumn"><s:property value="%{field_name}"/>:&nbsp;</div>
	       <div class="rightColumn"><span class="needed"></span>
	       <s:textfield name="new%{field_value}" id="new%{field_value}" placeholder="Enter Data" cssClass="textField"/>
	       </div>
	       </div>
		   </s:if>
		   <s:else>
		   <div class="newColumn">
	       <div class="leftColumn"><s:property value="%{field_name}"/>:&nbsp;</div>
	       <div class="rightColumn"><span class="needed"></span>
	       <s:textfield name="new%{field_value}" id="new%{field_value}"  placeholder="Enter Data" cssClass="textField"/>
	       </div>
	       </div>
		   </s:else>
  	   </s:iterator>
       </div>

	   <!-- KR & Completion Tip -->
	   
	   <table align="left"  style="padding-down:3cm ; padding-right:2cm">
	  <!--  <th>
	   <div class="newColumn">
       <td>
       <div class="leftColumn"><font color="blue">Check&nbsp;List&nbsp;</font></div>
       </td><td>
       <div class="rightColumn">
       	<img id="checkList" alt="Check List" src="images/OTM/check.jpg" height="30" width="30" onclick="CompletionTip();">
       	
       </div>
       </td>
       </div>	
        <div class="newColumn">
       <td>
       <div class="leftColumn"><font color="blue">KR&nbsp;Name&nbsp;</font></div>
       </td><td>
       <div class="rightColumn">
       <img id="krName" alt="Check List" src="images/share.jpg" height="25" width="50" onclick="KR();">
       </div>
       </td>
       </div> -->
       <tr>
        <td>
       	<img id="checkList" alt="Check List" src="images/OTM/check.jpg" height="30" width="30" onclick="CompletionTip();" style="margin-left: 286px;" title="Check List"/>
       </td>
       <td>
       	<img id="krName" alt="KR List" src="images/share.jpg" height="25" width="50" onclick="KR();" title="KR Name">
       </td>
       <td>
       	<img id="refRec" alt="Reference Record" src="images/OTM/refrec.jpg" height="30" width="30" onclick="referenceRec();" title="Reference Record">
       </td>
       <td>
       	<img id="workingHrs" alt="Working Hrs" src="images/OTM/timmer.jpg" height="30" width="30" onclick="getWorkingTime('COMPL');" title="Working Hours">
       </td>
       </tr>	
	</table>

 	   <s:iterator value="complianceFile" begin="0" end="0">
  	   <s:if test="%{mandatory}">
       </s:if>
       <div class="newColumn" style="margin-left: 50%">
       <div class="leftColumn"><s:property value="%{field_name}"/>:&nbsp;</div>
       <div class="rightColumn">
	   <s:if test="%{mandatory}"><span class="needed"></span></s:if>
       <s:file name="%{field_value}" id="%{field_value}" onchange="getDocu('file');"  placeholder="Enter Data"  cssClass="textField"/>
       </div>
       </div>
       </s:iterator> 
        
       <div id="file" style="display:none;">
       <s:iterator value="complianceFile" begin="1">
	   <div class="newColumn">
       <div class="leftColumn"><s:property value="%{field_name}"/>:&nbsp;</div>
       <div class="rightColumn">
       <s:file name="%{field_value}" id="%{field_value}" maxlength="50"  placeholder="Enter Data" cssClass="textField"/>
       </div>
       </div>
  	   </s:iterator>  
  	   </div>
  	   
       <s:iterator value="complianceCalender" begin="0" end="0" status="status">
       <s:if test="%{mandatory}">
	   <span id="complSpan" class="pIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
       <span id="newcomplSpan" class="newpIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
       <span id="remind2Other" class="rem2otherpIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
       <span id="remind2Both" class="rem2bothpIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
       <span id="remind2OtherNew" class="rem2othernewpIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
       <span id="remind2BothNew" class="rem2bothnewpIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	   </s:if>
       <s:if test="#status.odd">
       <div class="newColumn">
       <div class="leftColumn"><s:property value="%{field_name}"/>:&nbsp;</div>
       <div class="rightColumn">
       <s:if test="%{mandatory}"><span class="needed"></span></s:if>
       <sj:datepicker id="%{field_value}" name="%{field_value}" onChangeTopics="clearDatePicker" readonly="true" cssClass="textField"size="20" value="today" minDate="0" changeMonth="true" changeYear="true" onchange="getTotalday(this.value)" yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Due Date"
       	/>
       </div>
       </div>
       </s:if>
  	   <s:else>
  	   <div class="newColumn">
       <div class="leftColumn"><s:property value="%{field_name}"/>:&nbsp;</div>
       <div class="rightColumn">
       <sj:datepicker id="%{field_value}" name="%{field_value}" onChangeTopics="clearDatePicker" readonly="true" cssClass="textField"size="20" minDate="0" value="today" changeMonth="true" changeYear="true" yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Due Date"/>
       </div>
       </div>
  	   </s:else>
       </s:iterator>
  
       <s:iterator value="complianceTime" begin="0" end="0" status="status">
       <s:if test="%{mandatory}">
	   <span id="complSpan" class="pIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
       <span id="newcomplSpan" class="newpIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
       <span id="remind2Other" class="rem2otherpIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
       <span id="remind2Both" class="rem2bothpIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
       <span id="remind2OtherNew" class="rem2othernewpIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
       <span id="remind2BothNew" class="rem2bothnewpIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	   </s:if>
       <s:if test="#status.odd">
       <div class="newColumn">
       <div class="leftColumn"><s:property value="%{field_name}"/>:&nbsp;</div>
       <div class="rightColumn">
	   <s:if test="%{mandatory}"><span class="needed"></span></s:if>
       <sj:datepicker id="%{field_value}" name="%{field_value}" readonly="true" placeholder="Enter Time" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssClass="textField"/>
       </div>
       </div>
  	   </s:if>
  	   <s:else>
  	   <div class="newColumn">
       <div class="leftColumn"><s:property value="%{field_name}"/>:&nbsp;</div>
       <div class="rightColumn">
	   <s:if test="%{mandatory}"><span class="needed"></span></s:if>
       <sj:datepicker id="%{field_value}" name="%{field_value}" readonly="true" placeholder="Enter Time" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssClass="textField"/>
       </div>
       </div>
  	   </s:else>
       </s:iterator>

   	   <s:iterator value="complianceDropDown" begin="2" end="2" status="status">
   	   			<s:if test="%{mandatory}">
			   	   <span id="complSpan" class="pIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
			       <span id="newcomplSpan" class="newpIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
			       <span id="remind2Other" class="rem2otherpIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
			       <span id="remind2Both" class="rem2bothpIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
			       <span id="remind2OtherNew" class="rem2othernewpIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
			       <span id="remind2BothNew" class="rem2bothnewpIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
				   </s:if>
			  	   <div class="newColumn">
			       <div class="leftColumn"><s:property value="%{field_name}"/>:&nbsp;</div>
			       <div class="rightColumn">
			       <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				   <s:select  
                        id	=	"frequency"
                        name	=	"frequency"
                        list	=	"frequencyMap"
                        headerKey	=	"-1"
                        headerValue	=	"Frequency" 
                        cssClass	=	"select"
						cssStyle	=	"width:82%"
						onchange    =   "disableReminder(this.value),validateReminder('due_date','frequency','reminder1');showRemindDay(this.value,'remindDays');disableDayBefore(this.value)"
				   >
				   </s:select>
				   </div>
				   </div>
	   
				 <div id="remindDays" style="display: none;">
				  <s:iterator value="complianceTextBox" begin="1" end="1">
					  <s:if test="%{mandatory}">
					   <span id="newcomplSpan" class="newpIds" style="display: none; ">new<s:property value="%{field_value}"/>#new<s:property value="%{field_name}"/>#T#sc,</span>
				       <span id="remind2OtherNew" class="rem2othernewpIds" style="display: none; ">new<s:property value="%{field_value}"/>#new<s:property value="%{field_name}"/>#T#sc,</span>
				       <span id="remind2BothNew" class="rem2bothnewpIds" style="display: none; ">new<s:property value="%{field_value}"/>#new<s:property value="%{field_name}"/>#T#sc,</span>
				       </s:if>
					  <s:if test="#status.odd">
						   <div class="newColumn">
					       <div class="leftColumn"><s:property value="%{field_name}"/>:&nbsp;</div>
					       <div class="rightColumn"><span class="needed"></span>
					       <s:textfield name="%{field_value}" id="%{field_value}"  placeholder="Enter Data" cssClass="textField"/>
					       </div>
					       </div>
					   </s:if>
					   <s:else>
						   <div class="newColumn">
					       <div class="leftColumn"><s:property value="%{field_name}"/>:&nbsp;</div>
					       <div class="rightColumn"><span class="needed"></span>
					       <s:textfield name="%{field_value}" id="%{field_value}"  placeholder="Enter Data" cssClass="textField"/>
					       </div>
					       </div>
					   </s:else>
			  	   </s:iterator>
			  	   <div class="newColumn">
			       <div class="leftColumn">Customised&nbsp;Frequency&nbsp;On:</div>
			       <div class="rightColumn"><span class="needed"></span>
			  	   <s:select  
			                  id	     =	"customFrqOn"
			                  name	     =	"customFrqOn"
			                  list	     =	"#{'D':'Day','M':'Month','Y':'Year'}"
			                  headerKey	 =	"-1"
				              headerValue=	"Select Customised Frequency On" 
			                  cssClass	 =	"select"
				  cssStyle	 =	"width:82%"
				  onchange   =  "validateReminder('dueDate','frequency','reminder1')"
			                     >
			      
				   </s:select>
				   </div>
			       </div>
				   </div> 
	   
	   <s:hidden id="dateDiff" value="0"/>
	   <s:iterator value="complianceCalender" begin="1" end="1" status="status">
	   <div class="newColumn">
	   <div class="leftColumn">3rd&nbsp;Reminder:&nbsp;</div>
	   <div class="rightColumn">
       	<sj:datepicker disabled="false"  onChangeTopics="getSecondReminder" id="%{field_value}" name="%{field_value}" onclick="getDocu('configCompliance1');" readonly="true" cssClass="textField"size="20" minDate="0" maxDate="0"  changeMonth="true" changeYear="true" onchange="getTotalday(this.value)" yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Date"/>
	   </div>
	   </div>
  	   </s:iterator>
	   </s:iterator>
	   
	   
	   
	<%--    <div id="workDayDiv" style="display: none">
       <s:iterator value="complianceDropDown" begin="4" end="4" status="status">
       	   <div class="newColumn">
	       <div class="leftColumn"><s:property value="%{field_name}"/>:&nbsp;</div>
	       <div class="rightColumn">
	       <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	   <s:select  
                    id	=	"%{field_value}"
                    name	=	"%{field_value}"
                    list	=	"workingDayMap"
                    cssClass	=	"select"
	cssStyle	=	"width:82%"
	                           >
	   </s:select>
	   </div>
	   </div>
       </s:iterator>
       </div> --%>
	<div class="clear"></div>
  	   <div id="configCompliance1" style="display: none;">
  	   <s:iterator value="complianceCalender" begin="2" end="2" status="status">
  	   <s:if test="%{mandatory}">
       </s:if>
	   <s:if test="#status.odd">
       <div class="newColumn">
       <div class="leftColumn"><s:property value="%{field_name}"/>:&nbsp;</div>
       <div class="rightColumn">
       	<sj:datepicker id="%{field_value}"  onChangeTopics="getThirdReminder" name="%{field_value}" minDate="-30" maxDate="30" onclick="getDocu('configCompliance2');" readonly="true" cssClass="textField" size="20"   changeMonth="true" changeYear="true" onchange="getTotalday(this.value)" yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Date"/>
	   </div>
	   </div>
	   </s:if>
	   </s:iterator>
       </div> 
        <div id="configCompliance2" style="display: none;">
  	   <s:iterator value="complianceCalender" begin="3" end="3" status="status">
  	   <s:if test="%{mandatory}">
       </s:if>
	  	   <div class="newColumn">
	       <div class="leftColumn">1st&nbsp;Reminder:&nbsp;</div>
	       <div class="rightColumn">
	       <sj:datepicker id="%{field_value}" name="%{field_value}" readonly="true" cssClass="textField"size="20"  minDate="0" maxDate="-1" changeMonth="true" changeYear="true" onchange="getTotalday(this.value)" yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Date"/>
	       </div>
	       </div>
       </s:iterator>
       </div> 
     
       <s:iterator value="complianceCalender" begin="4" end="4" status="status">
  	    	<s:if test="%{mandatory}">
	   <span id="complSpan" class="pIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	       <span id="newcomplSpan" class="newpIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	       <span id="remind2Other" class="rem2otherpIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	       <span id="remind2Both" class="rem2bothpIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	       <span id="remind2OtherNew" class="rem2othernewpIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	       <span id="remind2BothNew" class="rem2bothnewpIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	   </s:if>
	  	   <div class="newColumn">
	       <div class="leftColumn"><s:property value="%{field_name}"/>:&nbsp;</div>
	       <div class="rightColumn">
	        <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	       <s:textfield name="%{field_value}" id="%{field_value}" value="0" placeholder="Enter Day Before" cssClass="textField" onchange="checkDayBefore()"/>
	       </div>
	       </div>
       </s:iterator>
       
       
       <s:iterator value="complianceTime" begin="5" end="5" status="status">
       <s:if test="%{mandatory}">
	   <span id="complSpan" class="pIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
       <span id="newcomplSpan" class="newpIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
       <span id="remind2Other" class="rem2otherpIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
       <span id="remind2Both" class="rem2bothpIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
       <span id="remind2OtherNew" class="rem2othernewpIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
       <span id="remind2BothNew" class="rem2bothnewpIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	   </s:if>
       <div class="newColumn">
       <div class="leftColumn"><s:property value="%{field_name}"/>:&nbsp;</div>
       <div class="rightColumn">
	   <s:if test="%{mandatory}"><span class="needed"></span></s:if>
       <sj:datepicker id="%{field_value}" name="%{field_value}" readonly="true" placeholder="Enter Time" showOn="focus" timepicker="true" timepickerOnly="true" onchange="checkStartTime()" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10"  cssClass="textField"/>
       </div>
       </div>
       </s:iterator>
     
       <s:iterator value="complianceRadio">
       <s:if test="%{mandatory}">
       </s:if>
       <span id="complSpan" class="pIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
       <span id="remind2Other" class="rem2otherpIds" style="display: none; ">emp_id#Employee#D#,</span>
       <span id="remind2Both" class="rem2bothpIds" style="display: none; ">emp_id1#Employee#D#,</span>
       <span id="remind2OtherNew" class="rem2othernewpIds" style="display: none; ">emp_id#Employee#D#,</span>
       <span id="remind2BothNew" class="rem2bothnewpIds" style="display: none; ">emp_id1#Employee#D#,</span>
	   
	   <s:if test="%{field_value == 'remind_mode'}">
       <div class="newColumn">
       <div class="leftColumn"><s:property value="%{field_name}"/>:&nbsp;</div>
       <div class="rightColumn">
       <s:if test="%{mandatory}"><span class="needed"></span></s:if>
       <s:radio list="viaFrom" name="%{field_value}" id="%{field_value}" value="%{'Both'}" onclick="findHiddenDiv(this.value);" />
       </div>
       </div>
	   </s:if>
	   </s:iterator>
	   
	   <s:iterator value="complianceRadio">
       <s:if test="%{mandatory}">
       </s:if>
       <span id="complSpan" class="pIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
       <span id="remind2Other" class="rem2otherpIds" style="display: none; ">emp_id#Employee#D#,</span>
       <span id="remind2Both" class="rem2bothpIds" style="display: none; ">emp_id1#Employee#D#,</span>
       <span id="remind2OtherNew" class="rem2othernewpIds" style="display: none; ">emp_id#Employee#D#,</span>
       <span id="remind2BothNew" class="rem2bothnewpIds" style="display: none; ">emp_id1#Employee#D#,</span>
	   
	    <s:if test="%{field_value == 'reminder_for'}">
       <div class="newColumn">
       <div class="leftColumn"><s:property value="%{field_name}"/>:&nbsp;</div>
       <div class="rightColumn">
       <s:if test="%{mandatory}"><span class="needed"></span></s:if>
       <s:radio list="remindToMap" name="%{field_value}" id="%{field_value}" value="%{'remToSelf'}" onclick="findHiddenDiv(this.value);"/>
       </div>
       </div>
       
       <div id="remToOther" style="display: none">
	   <div class="newColumn">
       <div class="leftColumn">Employee:&nbsp;</div>
       <div class="rightColumn">
       <span class="needed"></span>
	   <s:select  
                              	id	="emp_id"
                              	name	="emp_id"
                              	list	="emplMap"
                              	multiple	="true"
                              	cssClass	="select"
	cssStyle	="width:82%;height:40%"
                          />
  	</div>
      	</div>
      	</div>
      	
        <div id="remToBoth" style="display:none">
      	<div class="newColumn">
        <div class="leftColumn">Employee:&nbsp;</div>
        <div class="rightColumn">
        <span class="needed"></span>
	<s:select  
                              	id	="emp_id1"
                              	name	="emp_id"
                              	list	="emplMap"
                              	multiple	="true"
                              	cssClass	="select"
	cssStyle	="width:82%;height:40%"
                          />
  	</div>
        </div>
  	</div>
	   </s:if>
       </s:iterator>
       
       <div id="ownerShip" style="display:none;">
       <s:iterator value="complianceRadio">
       <s:if test="%{mandatory}">
       </s:if>
       <span id="complSpan" class="pIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
       <span id="remind2Other" class="rem2otherpIds" style="display: none; ">emp_id#Employee#D#,</span>
       <span id="remind2Both" class="rem2bothpIds" style="display: none; ">emp_id1#Employee#D#,</span>
       <span id="remind2OtherNew" class="rem2othernewpIds" style="display: none; ">emp_id#Employee#D#,</span>
       <span id="remind2BothNew" class="rem2bothnewpIds" style="display: none; ">emp_id1#Employee#D#,</span>
	   
	   <s:if test="%{field_value == 'action_type'}">
       <div class="newColumn">
       <div class="leftColumn"><s:property value="%{field_name}"/>:&nbsp;</div>
       <div class="rightColumn">
       <s:if test="%{mandatory}"><span class="needed"></span></s:if>
       <s:radio list="actionType" name="%{field_value}" id="%{field_value}" value="%{'individual'}"/>
       </div>
       </div>
	   </s:if>
	   </s:iterator>
       </div>
       
	<s:iterator value="complianceCheckBox">
	       <div class="newColumn">
	       <div class="leftColumn"><s:property value="%{field_name}"/>:&nbsp;</div>
	       <div style="display: block;" id="self">
	       <div style="margin-left: -4px; margin-top: 11px;">Self:</div>
	       <div class="rightColumn" style="margin-left: 348px; margin-top: -37px;">
	       <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	       Mail <s:checkbox name="selfmail" id="mail" />
	       SMS <s:checkbox name="selfsms" id="sms"/>
	       </div>
	       </div>
	       <div style="display: none;" id="other">
	       <div class="leftColumn" style="margin-left: 33px; margin-top: -12px;">Other:</div>
	       <div class="rightColumn" style="margin-left: 348px; margin-top: -37px;">
	       <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	       Mail <s:checkbox name="othermail" id="mail" />
	       SMS <s:checkbox name="othersms" id="sms"/>
	       </div>
	       </div>
	       </div>
       </s:iterator>
  	
        <s:iterator value="complianceDropDown" begin="3" status="status">
        <s:if test="%{mandatory}">
	<span id="complSpan" class="pIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	<span id="remind2Both" class="rem2bothpIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	<span id="remind2Other" class="rem2otherpIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	<span id="newcomplSpan" class="newpIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>        
	<span id="remind2OtherNew" class="rem2othernewpIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	<span id="remind2Other" class="rem2otherpIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	<span id="remind2Both" class="rem2bothpIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
        <span id="remind2BothNew" class="rem2bothnewpIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	
        </s:if>
  	<s:if test="#status.odd">
        <div class="newColumn">
        <div class="leftColumn"><s:property value="%{field_name}"/>:&nbsp;</div>
        <div class="rightColumn">
        <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	    			<s:select  
	                        id	= "escalation"
	                        name	= "escalation"
	                        list	= "escalationMap"
	                        headerKey	= "-1"
	                        headerValue = "Escalation" 
                            onchange	= "findHiddenDiv(this.value);getNextEscMap('escalation_level_1','escalation_level_2','escalation_level_3','l1');"
                            cssClass	="select"
							cssStyle	="width:82%"
	
	                  >
	    </s:select>
	    </div>
	    </div>
  	    </s:if>
  	    
  	    <div id="escalationDIV" style="display:none">
  	    <div class="clear"></div>
  	<div class="newColumn">
        <div class="leftColumn">L 2:&nbsp;</div>
        <div class="rightColumn"><span class="needed"></span>
          <div id="l1">
						<s:select  
                              	id	="escalation_level_1"
                              	name	="escalation_level_1"
                              	list	="escL1Emp"
                              	cssClass	="select"
								cssStyle	="width:82%;height:40%"
                              	multiple	="true"
                              	onchange="getNextEscMap('escalation_level_1','escalation_level_2','escalation_level_3','l2');"
                          />
          </div>
          </div>
      	  </div>
      	  <div class="newColumn">
          <div class="leftColumn">L&nbsp;2&nbsp;TAT&nbsp;After:&nbsp;</div>
          <div class="rightColumn"><span class="needed"></span>
	  <sj:datepicker id="l1EscDuration" name="l1EscDuration" readonly="true" onchange="changeEnable('l2EscDuration','escalation_level_2');getDocu('escalationDIV1');" placeholder="Enter Time" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssClass="textField"/>
          </div>
          </div>
          </div>
          
          <div id="escalationDIV1" style="display:none">
     	  <div class="clear"></div>
          <div class="newColumn">
          <div class="leftColumn">L 3:&nbsp;</div>
          <div class="rightColumn">
          <div id="l2">
						<s:select  
                              	id	="escalation_level_2"
                              	name	="escalation_level_2"
                              	list	="emplMap"
								cssClass	="select"
								cssStyle	="width:82%;height:40%"
                              	multiple	="true"
                              	disabled	="false"
                              	onchange="getNextEscMap('escalation_level_1','escalation_level_2','escalation_level_3','l3');"
                              	
                          />
          </div>
      	  </div>
      	  </div>
          <div class="newColumn">
          <div class="leftColumn">L&nbsp;3&nbsp;TAT&nbsp;After:&nbsp;</div>
          <div class="rightColumn">
          <sj:datepicker id="l2EscDuration" name="l2EscDuration" disabled="true" readonly="true" onchange="changeEnable('l3EscDuration','escalation_level_3');getDocu('escalationDIV2');" placeholder="Enter Time" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssClass="textField"/>
          </div>
          </div>
          </div>
          
          <div id="escalationDIV2" style="display:none">
     	  <div class="clear"></div>
     	  <div class="newColumn">
          <div class="leftColumn">L 4:&nbsp;</div>
          <div class="rightColumn">
          <div id="l3">
	<s:select  
                              	id	="escalation_level_3"
                              	name	="escalation_level_3"
                              	list	="emplMap"
                              	cssClass	="select"
	cssStyle	="width:82%;height:40%"
                              	multiple	="true"
                              	disabled	="true"
                              	onchange="getNextEscMap('escalation_level_1','escalation_level_2','escalation_level_3','l4');"
                              	
                          />
          </div>
          </div>
          </div>
          <div class="newColumn">
          <div class="leftColumn">L&nbsp;4&nbsp;TAT&nbsp;After:&nbsp;</div>
          <div class="rightColumn">
          <sj:datepicker id="l3EscDuration" name="l3EscDuration" disabled="true" readonly="true" onchange="changeEnable('l4EscDuration','escalation_level_4');getDocu('escalationDIV3');" placeholder="Enter Time" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssClass="textField"/>
          </div>
          </div>
          </div>
        
          <div id="escalationDIV3" style="display:none">
          <div class="clear"></div>
          <div class="newColumn">
          <div class="leftColumn">L 5:&nbsp;</div>
          <div class="rightColumn">
          <div id="l4">
					<s:select  
                              	id	="escalation_level_4"
                              	name	="escalation_level_4"
                              	list	="emplMap"
                              	cssClass	="select"
								cssStyle	="width:82%;height:40%"
                              	multiple	="true"
                              	disabled="true"
                              	
                          />
          </div>
          </div>
          </div>
          <div class="newColumn">
          <div class="leftColumn">L&nbsp;5&nbsp;TAT&nbsp;After:&nbsp;</div>
          <div class="rightColumn">
          <sj:datepicker id="l4EscDuration" name="l4EscDuration" disabled="true" readonly="true" placeholder="Enter Time" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssClass="textField"/>
          </div>
          </div>
          </div>
  	      </s:iterator>
         <div class="clear"></div>
         <div class="clear"></div>
           <br>
     
         <center>
	 <ul>
	 <li class="submit" style="background:none;">
	 <div class="type-button">
	  
      <sj:a 
     id="tsagdasgh"
     targets="resultComes"
	button="true" href="#"
	onBeforeTopics="configValidate"
	
	>Save</sj:a>
	    <sj:a 
		button="true" href="#"
		onclick="resetCompl('configCompliance');"
		>
		Reset
		</sj:a>
		 <sj:a 
		button="true" href="#"
		onclick="activtyBoard();"
		>
		View
		</sj:a>
	      </div>
	      </li>
	  </ul></center>
	  
          
	       </div> 
          </s:form>
</div>
</div>

</body>
<script type="text/javascript">
function abc()
{
	alert(">>>>");
}

</script>
</html>