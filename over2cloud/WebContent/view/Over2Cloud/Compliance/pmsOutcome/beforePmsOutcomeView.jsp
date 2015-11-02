<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString(); %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/leaveManagement/leave.js"/>"></script>

	<script type="text/javascript">
	
	function complWorkHours(cellvalue, options, row)
	{
		if(cellvalue!=0)
		{
			return '<a href="#" onClick="viewOnLinkData('+row.id+','+10+','+-1+');" ><b><font color="blue">'+cellvalue+'</font></b></a>';
		}
		else
		{
			return cellvalue;
		}	
	}
	
	function shortWorkHours(cellvalue, options, row)
	{
		if(cellvalue!=0)
		{
			return '<a href="#" onClick="viewOnLinkData('+row.id+','+10+','+-2+');" ><b><font color="blue">'+cellvalue+'</font></b></a>';
		}
		else
		{
			return cellvalue;
		}	
	}
	
	function lateGoingView(cellvalue, options, row)
	{
		if(cellvalue!=0)
		{
			return '<a href="#" onClick="viewOnLinkData('+row.id+','+-5+','+0+');" ><b><font color="blue">'+cellvalue+'</font></b></a>';
		}
		else
		{
			return cellvalue;
		}	
	}
	
	function earlyGtr30View(cellvalue, options, row)
	{
		if(cellvalue!=0)
		{
			return '<a href="#" onClick="viewOnLinkData('+row.id+','+-4+','+0+');" ><b><font color="blue">'+cellvalue+'</font></b></a>';
		}
		else
		{
			return cellvalue;
		}	
	}
	
	function early30View(cellvalue, options, row)
	{
		if(cellvalue!=0)
		{
			return '<a href="#" onClick="viewOnLinkData('+row.id+','+-3+','+0+');" ><b><font color="blue">'+cellvalue+'</font></b></a>';
		}
		else
		{
			return cellvalue;
		}	
	}
	
	function early15View(cellvalue, options, row)
	{
		if(cellvalue!=0)
		{
			return '<a href="#" onClick="viewOnLinkData('+row.id+','+-2+','+0+');" ><b><font color="blue">'+cellvalue+'</font></b></a>';
		}
		else
		{
			return cellvalue;
		}	
	}
	
	function lateGtrHourView(cellvalue, options, row)
	{
		if(cellvalue!=0)
		{
			return '<a href="#" onClick="viewOnLinkData('+row.id+','+5+','+1+');" ><b><font color="blue">'+cellvalue+'</font></b></a>';
		}
		else
		{
			return cellvalue;
		}	
	}
	
	function late60View(cellvalue, options, row)
	{
		if(cellvalue!=0)
		{
			return '<a href="#" onClick="viewOnLinkData('+row.id+','+4+','+1+');" ><b><font color="blue">'+cellvalue+'</font></b></a>';
		}
		else
		{
			return cellvalue;
		}	
	}
	
	function late30View(cellvalue, options, row)
	{
		if(cellvalue!=0)
		{
			return '<a href="#" onClick="viewOnLinkData('+row.id+','+3+','+1+');" ><b><font color="blue">'+cellvalue+'</font></b></a>';
		}
		else
		{
			return cellvalue;
		}	
	}
	
	function late15View(cellvalue, options, row)
	{
		if(cellvalue!=0)
		{
			return '<a href="#" onClick="viewOnLinkData('+row.id+','+2+','+1+');" ><b><font color="blue">'+cellvalue+'</font></b></a>';
		}
		else
		{
			return cellvalue;
		}	
	}
	
	function absentView(cellvalue, options, row)
	{
		if(cellvalue!=0)
		{
			return '<a href="#" onClick="viewOnLinkData('+row.id+','+0+','+1+');" ><b><font color="blue">'+cellvalue+'</font></b></a>';
		}
		else
		{
			return cellvalue;
		}	
	}
	//-1 for early coming
	function earlyView(cellvalue, options, row)
	{
		if(cellvalue!=0)
		{
			return '<a href="#" onClick="viewOnLinkData('+row.id+','+-1+','+1+');" ><b><font color="blue">'+cellvalue+'</font></b></a>';
		}
		else
		{
			return cellvalue;
		}	
	}
	
	function onTimeView(cellvalue, options, row)
	{
		if(cellvalue!=0)
		{
			return '<a href="#" onClick="viewOnLinkData('+row.id+','+1+','+1+');" ><b><font color="blue">'+cellvalue+'</font></b></a>';
		}
		else
		{
			return cellvalue;
		}
	}
	//status type for incoming or outgoing time
	function viewOnLinkData(id,statusNo,statusType)
	{
		var start=0;
		var end=0;
		var status="";
		var viewType="";
		if(statusNo==1)
		{
			viewType="On Time";
			status="ON TIME";
		}	
		else if(statusNo==0)
		{
			viewType="Absent";
			status="ABSENT";
		}
		else if(statusNo==-1)
		{
			viewType="Early";
			status="Early";
		}
		else if(statusNo==2)
		{
			viewType="Late By 0 to 15 mins";
			status="Late";
			start=1;
			end=15;
		}
		else if(statusNo==3)
		{
			viewType="Late By 16 to 30 mins";
			status="Late";
			start=16;
			end=30;
		}
		else if(statusNo==4)
		{
			viewType="Late By 31 to 60 mins";
			status="Late";
			start=31;
			end=60;
		}
		else if(statusNo==5)
		{
			viewType="Late By Greater Than 1 Hour";
			status="Late 1 Hour";
		}
		else if(statusNo==-2)
		{
			viewType="Early Going By 0 to 15 mins";
			status="Early 15";
			start=1;
			end=15;
		}
		else if(statusNo==-3)
		{
			viewType="Early Going By 16 to 30 mins";
			status="Early 30";
			start=16;
			end=30;
		}
		else if(statusNo==-4)
		{
			viewType="Early Going By Greater Than 30 mins";
			status="Early 30 mins";
			start=31;
			end=60;
		}
		else if(statusNo==-5)
		{
			viewType="Late Going By Greater Than 30 mins";
			status="EarlyLate Going 30 ";
			start=31;
			end=60;
		}
		else if(statusType==-1)
		{
			viewType="Full Working Hours";
		}
		else if(statusType==-2)
		{
			viewType="Short Working Hours";
		}
		var name  = jQuery("#gridedittable").jqGrid('getCell',id,'name');
		var empId  = jQuery("#gridedittable").jqGrid('getCell',id,'empId');
		var fromDate=$("#fromdate").val();
		var toDate=$("#todate").val();
		//alert(id);
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/leaveManagement/beforeAttendanceData.action?id="+id+"&empId="+empId+"&fromDate="+fromDate+"&toDate="+toDate+"&status="+status+"&statusType="+statusType+"&viewType="+viewType+"&start="+start+"&end="+end,
		    success : function(data) {
		    	$("#attendanceView").html(data);
		},
		   error: function() {
	            alert("error");
	        }
		 });
		 $("#attendanceDataDialog").dialog({title: viewType+' Details of '+name,width:1200,height: 400});
	     $("#attendanceDataDialog").dialog('open');
	}
	
	function reloadRow()
	{
		getChangeData();
	}

 function getChangeData()
{
	var fromDate=$("#fromdate").val();
	var toDate=$("#todate").val();
	var deptId=$("#deptId").val();
	var emp=$("#emp").val();
	var status=$("#status").val();
	var empType=$("#empType").val();
	//alert(empType);
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Compliance/compl_contacts_pages/pmsOutcomeViewHeader.action?fromDate="+fromDate+"&toDate="+toDate,
	    success : function(data) {
       $("#datapart1").html(data);
	},
	   error: function() {
            alert("error");
        }
	 });
}
 getChangeData(); 
 
 function viewLeavePage()
 {
	 $.ajax({
		    type : "post",
		    url : "view/Over2Cloud/leaveManagement/beforeFullReportView.action",
		    success : function(data) {
	     	  $("#dataDiv1").html(data);
		},
		   error: function() {
	            alert("error");
	        }
		 });
 }
 
</script>


</head>
<body>
<sj:dialog id="attendanceDataDialog" autoOpen="false" modal="true"
		width="400" height="200" resizable="false" onCloseTopics="closeDialog">
		<div id="attendanceView"></div>
</sj:dialog>

<input type="hidden" id="conString"  value="<%=request.getContextPath()%>"/>
<div id="dataDiv1">
<div class="list-icon">
	 <div class="head">
	Report</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">PMS OutCome</div> 
</div>

	

 <div style=" float:left; padding:10px 1%; width:98%;">
    <div class="listviewBorder">
    <div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
	<div class="pwie"><table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr></tr><tr><td></td></tr><tr><td> <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10">
	
		<sj:a id="refButton" title="Reload Grid" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="reloadView()"></sj:a>

	          
	          
				 <%-- <s:select  
					id=	"deptId"
					name="deptId"
					list="deptName"
					headerKey="-1"
					headerValue="Department"
					cssClass="button"
					cssStyle="margin-top: -29px;margin-left:3px"
					onchange=" getEmployeeNames(this.value,'emp');"
					theme="simple"
					>
				 </s:select>
				 
				  <s:select  
					id=	"emp"
					name="emp"
					list="#{'-1':'Employee'}"
					cssClass="button"
					cssStyle="margin-top: -29px;margin-left:3px;width:120px;"
					theme="simple"
					 onchange="getChangeData()"
			     	>
				 </s:select>
				 
				  <s:select  
					id=	"status"
					name="status"
					list="{'Inactive','Both'}"
					headerKey="1"
					headerValue="Active"
					cssClass="button"
					cssStyle="margin-top: -29px;margin-left:3px"
					onchange="getChangeData()"
					theme="simple"
					>
				 </s:select>	
				 
				 <s:select  
					id=	"empType"
					name="empType"
					list="employeeType"
					headerKey="-1"
					headerValue="Employment Type"
					cssClass="button"
					cssStyle="margin-top: -29px;margin-left:3px"
					onchange="getChangeData()"
					theme="simple"
					>
				 </s:select>
				 
				 <s:select  
					id=	"time"
					name="time"
					list="{'On Time','Late Coming','Early Going'}"
					headerKey="-1"
					headerValue="Time"
					cssClass="button"
					cssStyle="margin-top: -29px;margin-left:3px"
					onchange="getChangeData()"
					theme="simple"
					>
				 </s:select>
				 
				   <s:select  
					id=	"absentType"
					name="absentType"
					list="{'Intimated','Non Intimated'}"
					headerKey="-1"
					headerValue="Absent Type"
					cssClass="button"
					cssStyle="margin-top: -29px;margin-left:3px"
					onchange="getChangeData()"
					theme="simple"
					>
				 </s:select>  --%>
				 
				
	</td></tr></tbody></table>
	</td>
	<td class="alignright printTitle">
	
	</td> 
	</tr></tbody></table></div></div>
    </div>
    

<div id="datapart1"></div>

</div></div>
</body>
</html>