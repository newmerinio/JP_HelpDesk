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

function onChangeDate()
{
	var fdate=$("#fdate").val();
	var tdate=$("#tdate").val();
	var modifyFlag=$("#modifyFlag").val();
	alert("fdate" +fdate+ "tdate" +tdate+ "modifyFlag"+modifyFlag);
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/leaveManagement/viewreportAddForMyself.action?fdate="+fdate+"&tdate="+tdate+"&modifyFlag="+modifyFlag,
	    success : function(feeddraft) {
       $("#datapart").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function getOnChangeData()
{
	
	var empname=$("#empname").val();
	
	$.ajax({
	    type : "post",
	    //url : "/over2cloud/view/Over2Cloud/commonModules/makeHistory.action",
	    url : "view/Over2Cloud/leaveManagement/summaryDetailAction.action?empname="+empname,
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
	    url : "view/Over2Cloud/leaveManagement/summaryDetailAction.action",
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
	Summary</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
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
									cssStyle="margin-top: -2px;margin-left:3px"
									onchange="getContactdata(this.value,'empname');"
									theme="simple"
									>
						</s:select>
				

				<s:iterator value="attendanceColumnDropDown">
					<s:if test="%{key == 'empname'}">
						<s:if test="%{mandatory}">
							<span id="mandatoryFields" class="pIds" style="display: none;"><s:property
									value="%{key}" />#<s:property value="%{value}" />#<s:property
									value="%{colType}" />#<s:property value="%{validationType}" />,</span>
						</s:if>
								<div id="employeeType1">
									<s:select
										  id="%{Key}"
										  name="%{Key}"
										  list="employeelist"
										  headerKey="-1"
										  headerValue="Select Employee"
										  cssClass="button"
									      cssStyle="margin-top: -31px;margin-left:132px"
									      theme="simple"
									      onchange="getOnChangeData()"
									      >
									</s:select>
								</div>
							
						
					</s:if>
				</s:iterator>
	
	</td></tr></tbody></table>
	</td>
	<td class="alignright printTitle">
	<!--<sj:a id="downloadExceProc"  button="true" buttonIcon="ui-icon-arrowthickstop-1-s" cssClass="button" onclick="getCurrentColumn('downloadExcel','dailyReport','downloadColumnDailyReport','downloadDailyReportColumnDiv','%{empname}','%{fdate}','%{tdate}')">Download</sj:a>
-->
<sj:a  button="true" 
				           cssClass="button" 
				           cssStyle="height:25px; width:32px"
				           title="Download"
				           buttonIcon="ui-icon-arrowstop-1-s" 
				           onclick="getCurrentColumn('downloadExcel','summaryReport','downloadColumnSummaryReport','downloadSummaryReportColumnDiv','%{empname}');" />
				 	   

	</td> 
	</tr></tbody></table></div></div>
    </div>
    
<s:url id="summaryViewGrid" action="summaryViewGrid" escapeAmp="false">
<s:param name="moduleFlag" value="%{moduleFlag}" />
<s:param name="id" value="%{id}" />
<s:param name="empname" value="%{empname}"></s:param>
</s:url>
<div id="datapart"></div>

</div>
</body>



</html>