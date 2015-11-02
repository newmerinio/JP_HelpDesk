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


<script>


function getOnChangeData()
{
	
	var empname=$("#empname").val();
	var fdate=$("#fdate").val();
	var tdate=$("#tdate").val();
	var modifyFlag=$("#modifyFlag").val();
	
	$.ajax({
	    type : "post",
	    //url : "/over2cloud/view/Over2Cloud/commonModules/makeHistory.action",
	    url : "view/Over2Cloud/leaveManagement/reportAddAction.action?empname="+empname+"&fdate="+fdate+"&tdate="+tdate+"&modifyFlag="+modifyFlag,
	    success : function(subdeptdata) {
       $("#datapart").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
	
}

function loadGrid()
{
	
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/leaveManagement/reportAddAction.action",
	    success : function(feeddraft) {
       $("#datapart").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}

loadGrid();

</script>


</head>
<body>




<div class="list-icon">
	 <div class="head">
	Daily</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">Report</div> 
</div>

 <div style=" float:left; padding:10px 1%; width:98%;">
    <div class="listviewBorder">
    <div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
	<div class="pwie"><table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr></tr><tr><td></td></tr><tr><td> <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10">
	
	
						
						<s:select 
						id="deptName" 
						list="deptDataList" 
						headerKey="-1"
						headerValue="Select" 
						cssClass="button"
						cssStyle="margin-top: -29px;margin-left:3px"
						onchange="getContactdata(this.value,'empname');"
						 theme="simple"
						>
						</s:select>
					

				<s:iterator value="attendanceColumnDropDown">
					<s:if test="%{key == 'empname'}">
								<s:select
										  id="%{Key}"
										   name="%{Key}" 
										   list="employeelist"
											headerKey="-1" 
											headerValue="Select Employee" 
											cssClass="button"
									        cssStyle="margin-top: -29px;margin-left:3px"
							                 theme="simple"
											>
								</s:select>
					
					</s:if>
				</s:iterator>
				 <sj:datepicker name="fdate" id="fdate" changeMonth="true" readonly="true" changeYear="true" yearRange="2013:2020" showOn="focus" displayFormat="dd-mm-yy" cssStyle="height: 16px; width: 70px;" cssClass="button" placeholder="Select From Time"/>
				 <sj:datepicker name="tdate" id="tdate" onchange="getOnChangeData()" changeMonth="true" readonly="true" changeYear="true" yearRange="2013:2020" showOn="focus" displayFormat="dd-mm-yy" cssStyle="height: 16px; width: 70px;" cssClass="button" placeholder="Select To Time"/>
		     		 
	
	</td></tr></tbody></table>
	</td>
	<td class="alignright printTitle">

<sj:a  button="true" 
				           cssClass="button" 
				           cssStyle="height:25px; width:32px"
				           title="Download"
				           buttonIcon="ui-icon-arrowstop-1-s" 
				           onclick="getCurrentColumn('downloadExcel','dailyReport','downloadColumnDailyReport','downloadDailyReportColumnDiv','empname','fdate','tdate')"/>
		 	   

	</td> 
	</tr></tbody></table></div></div>
    </div>
	 <s:hidden name="modifyFlag" id="modifyFlag" value="%{modifyFlag}"></s:hidden> 
	<s:hidden name="empname" id="empname" value="%{empname}"></s:hidden> 
	<s:hidden name="fdate" id="fdate" value="%{fdate}"></s:hidden> 
	<s:hidden name="tdate" id="tdate" value="%{tdate}"></s:hidden> 
	
<s:url id="attendanceGridDataView" action="attendanceGridDataView" escapeAmp="false">
<s:param name="empname" value="%{empname}" />
<s:param name="fdate" value="%{fdate}" />
<s:param name="tdate" value="%{tdate}" />
<s:param name="modifyFlag" value="%{modifyFlag}" />
</s:url>
<div id="datapart"></div>

</div>
</body>
<sj:dialog 
		id="downloadColumnDailyReport" 
		modal="true" 
		effect="slide" 
		autoOpen="false" 
		width="300" 
		height="530" 
		title="Holiday Detail Column Name" 
		loadingText="Please Wait" 
		overlayColor="#fff" 
		overlayOpacity="1.0" 
		position="['center','center']" >
	<div id="downloadDailyReportColumnDiv"></div>
</sj:dialog>


</html>