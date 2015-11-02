<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<script src="<s:url value="/js/organization.js"/>"></script>
<script src="<s:url value="/js/employeeOver2Cloud.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<SCRIPT type="text/javascript">
function viewTarget(target, modify, del) 
{
	var tableName;
	if(target == "kpi")
		tableName = "incentivedata";
	else if(target == "offering")
		tableName = "offeringincentive";
		
	$("#grid").html("<br><br><br><div class='clear'></div><img style='margin-left: 44%;' src=images/ajax-loaderNew.gif>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/incentive/beforeIncentiveView.action?tableName="+tableName+"&targetOn="+target+"&modifyFlag=0&deleteFlag=0",
	    success : function(data) {
       $("#grid").html(data);
	},
	   error: function() {
            alert("error");
        }
	 });
	 
	//$("#incentive").load("view/Over2Cloud/WFPM/incentive/beforeIncentiveView.action??modifyFlag=0&deleteFlag=0&targetOn="+target);
}
</script>


</head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<body>
	<div class="clear"></div>
	<div class="list-icon">
		<!-- <div class="head">Incentive View</div> -->
	<div class="head">Incentive</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">View</div>
	</div>
	<div style=" float:left;  width:100%;">
		<div class="border">
			<div class="menubox">
				<div class="newColumn">
					<div class="leftColumn1">Target on :</div>
					<div class="rightColumn1">
						<s:radio id="targetOn" cssStyle="margin-left:10px" name="targetOn" list="#{'kpi':'KPI','offering':'Offering'}" value="%{'kpi'}" 
						         onchange="viewTarget(this.value, %{modifyFlag}, %{deleteFlag});" />
					</div>
				</div>
			</div>
			<div id="grid"></div>
		</div>
	</div>


</body>
<SCRIPT type="text/javascript">
viewTarget('kpi', ${modifyFlag}, ${deleteFlag});
</SCRIPT>
</html>
