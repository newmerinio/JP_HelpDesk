<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString(); %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="<s:url value="/js/task_js/task.js"/>"></script>
<script type="text/javascript">
function onChangeData()
{
	var allot=$("#allotment").val();
	var fdate=$("#fromDate").val();
	var todate=$("#toDate").val();
	var taskType=$("#taskType").val();
	var taskP=$("#taskPriority").val();
	var searching=$("#searching").val();
	var taskStatus=$("#taskStatus").val();
	var searchBy=$("#searchBy").val();
	if (allot=='Alloted') 
	{
		$('#div1').show();
		$('#div2').hide();
	} 
	else 
	{
		$('#div1').hide();
		$('#div2').show();
	}
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/DAROver2Cloud/activityData.action?allot="+allot+"&fromdate="+fdate+"&todate="+todate+"&taskType="+taskType+"&taskPriority="+taskP+"&search="+searching+"&taskStatus="+taskStatus+"&searchBy="+searchBy,
	    success : function(data) {
       $("#"+"viewActivity").html(data);
	},
	  error: function() {
          alert("error");
      }
	 });
}
onChangeData();
</script>
</head>
<body>
<s:form action="download" id="downloadFile" theme="css_xhtml" name="forms">
<s:hidden name="filePath" id="filePath"/>
<s:hidden name="accessType" id="accessType"/>
</s:form>

 <div class="clear"></div>
 <div class="list-icon">
	 <div class="head">Activity Board</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">View</div> 
   </div>  
<div class="clear"></div>
<div class="listviewBorder" style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr>
						  <td class="pL10">
				
					   <sj:a id="searchButton"  title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px"  cssClass="button" button="true"  onclick="searchRow()"></sj:a>
					   <sj:a id="refButton"  title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px"  cssClass="button" button="true"  onclick="reloadRow()"></sj:a>
					   <s:select
							id="allotment" 
							list="#{'Unalloted':'Unalloted','Alloted':'Alloted'}"
				            theme="simple"
				            cssStyle="height: 26px;width:108px;"
							cssClass="button"
							onchange="onChangeData();"
				            />
					   <sj:datepicker id="fromDate" name="fromDate" cssClass="button" onchange="onChangeData();" cssStyle="height: 14px;width: 59px;margin-left: 4px;" value="%{fromdate}"  readonly="true"  size="20" changeMonth="true" changeYear="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select From Date"/>
					   <sj:datepicker id="toDate" name="toDate" cssClass="button" onchange="onChangeData();" cssStyle="height: 14px;width: 59px;margin-left: 2px;" value="%{todate}" readonly="true"  size="20" changeMonth="true" onchange="getDateSearchData(this.value)" changeYear="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select To Date"/>
 
	
		<s:select
			id="taskType" 
			list="listtask"
			headerKey="-1"
			headerValue="Task Type"
            theme="simple"
            cssStyle="height: 26px;width:108px;"
			cssClass="button"
			onchange="onChangeData();"
            />
            <div id="div2">
				<s:select
					id="registerBy" 
					list="registerBY"
					headerKey="-1"
					headerValue="Register By"
		            cssStyle="height: 26px;"
		            theme="simple" 
					cssClass="button"
					onchange="onChangeData();"
		            />
            </div>
         <s:select 
			id="taskPriority" 
			list="{'Low','High','Medium'}"
			headerKey="-1"
			headerValue="Task Priority"
			cssStyle="height: 26px;"
            theme="simple" 
			cssClass="button"
			onchange="onChangeData();"
			/>
			 <s:textfield id="searching" name="searching" onclick="onChangeData();" cssClass="button" cssStyle="margin-top: -28px;height: 14px;width:80px;" Placeholder="Task Name" onchange="getSearchData(this.value)"/>
					    	
				</td>
				
					      </tr></tbody>
					  </table>
				  </td>
				  <td class="alignright printTitle">
				  <div id="div1" style="display: none;">     
				    		 <s:select
							id="taskStatus" 
							headerKey="-1"
							headerValue="All"
							list="#{'Running':'Running','Pending':'Pending','Done':'Completed','Snooze':'Snooze'}"
				            theme="simple"
				            cssStyle="height: 26px;width:108px;"
							cssClass="button"
							onchange="onChangeData();"
				            /> 
		            	 <s:select
							id="searchBy" 
							headerKey="-1"
							headerValue="All"
							list="allotmentList"
				            theme="simple"
				            cssStyle="height: 26px;width:108px;"
							cssClass="button"
							onchange="onChangeData();"
				            />
				        </div> 
				        <s:select
							id="searchByName" 
							headerKey="-1"
							headerValue="All"
							list="searchByname"
				            theme="simple"
				            cssStyle="height: 26px;width:108px;"
							cssClass="button"
							onchange="onChangeData();"
				            />
				        </div>
				        
				        
						<sj:a id="addButton"  button="true"  cssClass="button"  onclick="krUpload('shareStatus');">Resource</sj:a>
						<sj:a id="addButtonReg"  button="true"  cssClass="button" onclick="createTask();">Register</sj:a>
					<sj:a id="addAllotButton"  button="true"  cssClass="button" onclick="createTaskAllot();">Allot</sj:a>
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>	
<div style="overflow: scroll; height: 430px;">
<div id="viewActivity">		
</div>
</div>
</div>
</body>
</html>