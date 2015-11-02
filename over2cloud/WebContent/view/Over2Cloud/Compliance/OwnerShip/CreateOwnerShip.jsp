<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%
int temp=0;
%>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<meta   http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/compliance/compliance.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>
<link href="js/multiselect/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>
	<meta   http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<SCRIPT type="text/javascript">

	$.subscribe('openDialog', function(event,data)
	{
		$("#completionResult").dialog('open');
	});
  $.subscribe('makeEffect11', function(event,data)
  {
	 setTimeout(function(){ $("#complTarget").fadeIn(); }, 10);
	 setTimeout(function(){ $("#complTarget").fadeOut();
	 cancelButton();
	 resetTaskName('complTask');}, 5000);
  });
 	$.subscribe('makeEffect', function(event,data)
	{
 		 setTimeout(function(){ $("#complTarget").fadeIn(); }, 10);
 		 setTimeout(function(){ $("#complTarget").fadeOut();
 		 cancelButton();
 		 resetTaskName('complTask');}, 5000);
	});
	function cancelButton()
	{
		$('#completionResult').dialog('close');
		viewOwnerShip();
	}
 function viewOwnerShip()
	{
	 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
			type : "post",
		    url : "view/Over2Cloud/Compliance/OwnerShip/ViewOwnership.jsp",
		    success : function(data) 
		    {
				$("#"+"data_part").html(data);
		    },
		    error: function() 
		    {
	            alert("error");
	        }
		 });
	}
 function addInKR()
 {
 	
 	$('#openKRDialog').dialog('open');
 	$('#openKRDialog').dialog({height: '450',width:'1250',position:'center'});
 	$("#krview").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
 	$.ajax({
 	    type : "post",
 	    url : "/over2cloud/view/Over2Cloud/KRLibraryOver2Cloud/beforeKRUpload.action?dataFrom=Other",
 	    success : function(data) 
 	    {
 			$("#"+"krview").html(data);
 	    },
 	    error: function() 
 	    {
             alert("error");
         }
 	 });
 }
 function fromKRAdd()
 {
	 $("#krBlock").show();
 }
 function resetTaskName(formId)
 {
 	$('#'+formId).trigger("reset");
 }
 function getKRName(subGroupId)
 {
	 $.ajax({
		    type : "post",
		    url : "view/Over2Cloud/Compliance/compl_task_page/getKRNameAction.action?subGroupId="+subGroupId,
		    success : function(data) 
		    {
				$("#krNameDiv").html(data);
		    },
		    error: function() 
		    {
	            alert("error");
	        }
		 });
 }

 function hideBudgetDiv()
 {
	 if($('#accountingNo').is(':checked'))
	 {
		 $("#budgetDiv").hide();
		 $("#budgetary").val('');
		 
	 }
	 else if($('#accountingYes').is(':checked'))
	 {
		 $("#budgetDiv").show();
	 }
	 
 }

 function getEmployee()
 {
	 var deptId = $("#fromDept").val();
	 $.ajax({
		    type : "post",
		    url : "view/Over2Cloud/Compliance/OwnerShip/getEmployee.action?deptId="+deptId,
		    success : function(data) 
		    {
				$("#replceEmpDiv").html(data);
		    },
		    error: function() 
		    {
	            alert("error");
	        }
		 });
 }
 
 $.subscribe('validateCommunication', function(event,data)
			{
			    var mystring=null;
			    mystring=$(".pIds1").text();
			    var fieldtype = mystring.split(",");
			    for(var i=0; i<fieldtype.length; i++)
			    {
			        var fieldsvalues   = fieldtype[i].split("#")[0];
			        var fieldsnames    = fieldtype[i].split("#")[1];
			        var colType        = fieldtype[i].split("#")[2];   
			        $("#"+fieldsvalues).css("background-color","");
			        errZone1.innerHTML="";
			        if(fieldsvalues!= "" )
			        {
			            if(colType=="D")
			            {
			                if($("#"+fieldsvalues).val()==null || $("#"+fieldsvalues).val()=="" || $("#"+fieldsvalues).val()=="-1")
			                {
			                errZone1.innerHTML="<div class='user_form_inputError2'>Please Select "+fieldsnames+" Value from Drop Down </div>";
			                setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
			                setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
			                $("#"+fieldsvalues).focus();
			                $("#"+fieldsvalues).css("background-color","#ff701a");
			                event.originalEvent.options.submit = false;
			                break;   
			                }
			            }
			        }
			    }
			 });
 
</SCRIPT>
<script type="text/javascript">
$(document).ready(function()
	{
	$("#forDept").multiselect({
		   show: ["", 200],
		   hide: ["explode", 1000]
		});
	});
	
$(document).ready(function()
		{
		$("#forDept11").multiselect({
			   show: ["", 200],
			   hide: ["explode", 1000]
			});
		});

$(document).ready(function()
		{
		$("#fromDept").multiselect({
			   show: ["", 200],
			   hide: ["explode", 1000]
			});
		});
$(document).ready(function()
		{
		$("#empDiv").multiselect({
			   show: ["", 200],
			   hide: ["explode", 1000]
			});
		});
	
</script>
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
          cssStyle="overflow:hidden;"
          draggable="true"
    	  resizable="true"
    	   buttons="{ 
    		'Close':function() { cancelButton(); } 
    		}" 
          >
          <center>
	 	  <img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/>
	      </center>
          <sj:div id="complTarget" cssStyle="width:90%"  effect="fold"> </sj:div>
</sj:dialog>


<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">
	 Configure Ownership</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">Add</div> 
</div>

<div class="clear"></div>
<div style="width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">
<div class="form_inner" id="form_reg" style="width:100%; padding:0px;">
<div class="page_form">

  <s:form id="ownership" name="ownership" action="ownerShipAction" namespace="/view/Over2Cloud/Compliance/OwnerShip" theme="simple"  method="post"enctype="multipart/form-data" >
    <center>
	  	<div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 50%; height: 10%; font-size: small; border: 0px solid red; border-radius: 6px;">
			<div id="errZone" style="float:center; margin-left: 7px"></div>
	  	</div>
	</center>
    
			 <span id="mandatoryFields" class="pIds" style="display: none; ">forDept#For Department#D#a#,</span>
	  		<div class="newColumn">
 			 <div class="leftColumn">For Department:</div>
         	 <div class="rightColumn">
             <span class="needed"></span>
             <s:select 
                   cssClass="select"
                   id="forDept"
                   name="forDept" 
                   list="dataMap" 
                   multiple="true"
             >
    		 </s:select>
             </div>
             </div>
	  
	  		<span id="mandatoryFields" class="pIds" style="display: none; ">fromDept#Owner From#D#a#,</span>
	  		 <div class="newColumn">
 			 <div class="leftColumn">Owner From:</div>
         	 <div class="rightColumn">
             <span class="needed"></span>
             <s:select 
                   cssClass="select"
                   id="fromDept"
                   name="fromDept" 
                   list="deptMap" 
                   multiple="true"
                   onchange="getEmployee();"
             >
    		 </s:select>
             </div>
             </div>
             
             <span id="mandatoryFields" class="pIds" style="display: none; ">empDiv11#Owner#D#a#,</span>
             <div class="newColumn">
 			 <div class="leftColumn">Owner:</div>
         	 <div class="rightColumn">
             <span class="needed"></span>
             <div id="replceEmpDiv">
             <s:select 
                   id="empDiv"
                   name="empDiv" 
                   list="{'No Data'}" 
                   multiple="true"
             >
    		 </s:select>
    		 </div>
             </div>
             </div>
		  
		<div class="clear"></div>
        <div class="clear"></div>
        <br>
		<div class="fields">
		<center>
		 <ul>
			<li class="submit" style="background:none;">
			<div class="type-button">
	        <sj:submit 
         				targets			=	"complTarget" 
         				clearForm		=	"true"
         				value			=	" Save " 
         				button			=	"true"
         				cssClass		=	"submit"
                 		indicator		=	"indicator2"
                 		effect			=	"highlight"
                 		effectOptions	=	"{ color : '#222222'}"
                 		effectDuration	=	"1000"
                 		onCompleteTopics=	"openDialog,makeEffect11"
                 		onBeforeTopics="validateMapKr"
     		  	  />
     		  	 <sj:a 
                        button="true" href="#"
                        onclick="resetTaskName('ownership');resetColor('.pIds');"
                        >
                        Reset
                    </sj:a>
     		  	  <sj:a 
						button="true" href="#"
						onclick="viewOwnerShip();"
						>
						Back
					</sj:a>
	        </div>
	        </li>
		 </ul>
		 
		 </center>
		 <br>
		 <br>
		 	 <center><sj:div id="complTarget11"  effect="fold"> </sj:div></center>
		 </div>
		 <i>Note: All department & employee comes from primary contact</i>
   </s:form>
</div>
</div>
</div>

<div class="clear" ></div>
 <div align="left" style="margin-left: 17px;"><b>Configure Communication</b></div>
<div style="width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">
<div class="form_inner" id="form_reg" style="width:100%; padding:0px;">
<div class="page_form">

  <s:form id="alertMailOwnerOnLeave" name="alertMailOwnerOnLeave" action="alertMailOwnerOnLeave"  theme="simple"  method="post"enctype="multipart/form-data" >
    <center>
	  	<div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 50%; height: 10%; font-size: small; border: 0px solid red; border-radius: 6px;">
			<div id="errZone1" style="float:center; margin-left: 7px"></div>
	  	</div>
	</center>
    		<span id="mandatoryFields1" class="pIds1" style="display: none; ">forDept11#For Department#D#a#,</span>
	  		 <div class="newColumn">
 			 <div class="leftColumn">For Department:</div>
         	 <div class="rightColumn">
             <span class="needed"></span>
             <s:select 
                   cssClass="select"
                   id="forDept11"
                   name="forDept" 
                   list="dataMap" 
                   multiple="true"
             >
    		 </s:select>
             </div>
             </div>
             <span id="mandatoryFields1" class="pIds1" style="display: none; ">toLevel#Communicate To#D#a#,</span>
             <div class="newColumn">
 			 <div class="leftColumn">Communicate To:</div>
         	 <div class="rightColumn">
             <span class="needed"></span>
             <s:select 
                   cssClass="select"
				   cssStyle="width:95%"
                   id="toLevel"
                   name="toLevel" 
                   list="#{'L2':'L2 Only','L2OW':'L2 & Owner','OW':'Owner Only'}" 
                   headerKey="-1"
                   headerValue="Select Level"
             >
    		 </s:select>
             </div>
             </div>
		  
		<div class="clear"></div>
        <div class="clear"></div>
        <br>
		<div class="fields">
		<center>
		 <ul>
			<li class="submit" style="background:none;">
			<div class="type-button">
	        <sj:submit 
         				targets			=	"complTarget" 
         				clearForm		=	"true"
         				value			=	" Save " 
         				button			=	"true"
         				cssClass		=	"submit"
                 		indicator		=	"indicator2"
                 		effect			=	"highlight"
                 		effectOptions	=	"{ color : '#222222'}"
                 		effectDuration	=	"1000"
                 		onCompleteTopics=	"openDialog,makeEffect"
                 		onBeforeTopics="validateCommunication"
     		  	  />
     		  	 <sj:a 
                        button="true" href="#"
                        onclick="resetTaskName('alertMailOwnerOnLeave');resetColor('.pIds1');"
                        >
                        Reset
                    </sj:a>
     		  	  <sj:a 
						button="true" href="#"
						onclick="viewOwnerShip();"
						>
						Back
					</sj:a>
	        </div>
	        </li>
		 </ul>
		 
		 </center>
		 <br>
		 <br>
		 	 <%-- <center><sj:div id="complTarget"  effect="fold"> </sj:div></center> --%>
		 </div>
		 <i>Note: All department comes from task name</i>
   </s:form>
</div>
</div>
</div>
</div>
</body>
</html>