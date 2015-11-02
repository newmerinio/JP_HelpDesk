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
function viewTarget(target, empID) 
{
	if($("#empName").val() == null || $("#empName").val() == "" ||  $("#empName").val() == "-1")
	{
		 $("#errZone").html("<div class='user_form_inputError2'>Please Select One Employee First !</div>");	
         setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
         setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		 event.originalEvent.options.submit = false;
		 return;
	}

	$("#empView").load("view/Over2Cloud/wfpm/target/beforeTargetGridView.action?empID="+empID+"&targetOn="+target);
}

function addTarget()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/wfpm/target/beforeTargetAdd.action?header=Allot",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}
</script>

</head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<body>
<div class="clear"></div>
	<div class="list-icon">
		<%-- <div class="head"><s:property value="%{header}"/> Target >> View </div> --%>
		<div class="head">Target</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">View</div>
	</div>
	<div class="container_block"><div style=" float:left;  width:100%;">
	<div class="clear"></div>
	<div class="border" style="height: ">
	<div style="min-height: 10px; height: auto; width: 100%;"></div>
	<div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
             <div id="errZone" style="float:left; margin-left: 7px">
             </div> 
    </div>
	
	<s:if test="#session['userRights'].contains('targ_ADD')">
		<div class="rightHeaderButton">
			<sj:a
				button="true"
				buttonIcon="ui-icon-plus" 
				onclick="addTarget();"
			>
				Allot Target
			</sj:a>
			<span class="normalDropDown"> </span>
		</div>
	</s:if>
		
	<s:if test="#session['userRights'].contains('targ_VIEW') 
				|| #session['userRights'].contains('targ_MODIFY') 
				|| #session['userRights'].contains('targ_DELETE')">
		   	
		<div class="menubox">
			<div class="newColumn">
				<div class="leftColumn1">Employee Name:</div>
				<div class="rightColumn1">
					<s:select 
	                              cssClass="textField"
	                              id="empName"
	                              name="empName" 
	                              list="empDataList" 
	                              headerKey="-1"
	                              headerValue="Select Employee Name" 
	                              
	                              > 
	                  </s:select>
				</div>
			</div>
			
			<div class="clear"></div>
			<div id="mappedKPIForTarget"></div>
			<div class="clear"></div>
			<div class="newColumn">
				<div class="leftColumn1">Target on :</div>
				<div class="rightColumn1">
					<s:radio id="targetOn" cssStyle="margin-left:10px" 
					name="targetOn" list="#{'kpi':'KPI','offering':'Offering'}" 
					value="kpi" onchange="viewTarget(this.value, empName.value);" />
				</div>
			</div>
			<div class="clear"></div>
			
		</div>
		<div class="clear"></div>
		<div id="empView">
			</div>
	</s:if>		
			
	</div>
</div>
</div>
</body>
</html>
