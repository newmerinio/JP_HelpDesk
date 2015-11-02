<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script src="<s:url value="/js/organization.js"/>"></script>
<script src="<s:url value="/js/employeeOver2Cloud.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<SCRIPT type="text/javascript">
function getAllSwapAndSwappedWithData()
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/hr/getAllSwapAndSwappedWithDataFromAction.action",
	    success : function(companyData) {
		$("#putData").html(companyData);
	},
	   error: function() {
	        alert("error");
	    }
	 });
}
</SCRIPT>
<SCRIPT type="text/javascript">
$.subscribe('completeData', function(event,data)
        {
          setTimeout(function(){ $("#Result").fadeIn(); }, 10);
          setTimeout(function(){ $("#Result").fadeOut(); }, 4000);
         
        });

function mapTarget(empID) {

	if(empID != "-1" && empID != null)
	{
		$("#mappedKPIForTarget").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/wfpm/target/mapTarget.action?empID="+empID,
		    success : function(subdeptdata) {
		   
	       $("#"+"mappedKPIForTarget").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
}

function clearAllErroDiv()
{
	$("div[id^='error']").each(function() {
  	  // `this` now refers to the current element
	     this.innerHTML="";
  	});
}
</script>
</head>

<body>

	<div class="clear"></div>
	<div class="list-icon">
		<%-- <div class="head"><s:property value="%{header}"/> Target To Employee</div> --%>
		<div class="head">Target</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Allot To Employee</div>
	</div>
	<div style="min-height: 10px; height: auto; width: 100%;padding-top: 30px;"></div>
		<div class="border">
	<div style="min-height: 0px; height: auto; width: 100%;"></div>

	
	<div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
             <div id="errZone" style="float:left; margin-left: 7px">
             </div> 
    </div>

	<table width="90%" cellspacing="0" cellpadding="0" border="0" class="secContent">
	<tbody width="100%">
			<tr>
				<td width="25%" class="label">Emp Name:</td>
   				<td width="25%">
   					<span class="needed"></span>
   					<s:select 
                        cssClass="textField"
                        id="empName"
                        name="empName" 
                        list="empDataList" 
                        headerKey="-1"
                        headerValue="Select Employee Name" 
                        onchange="mapTarget(this.value);"
                        theme="simple"
                        > 
	                  </s:select>
   				</td>
		
				<td width="25%" class="label">&nbsp;&nbsp;</td>
   				<td width="25%">&nbsp;&nbsp;</td>
     		</tr>
	</tbody>
	</table>
	
		<div class="clear"></div>
		<div id="mappedKPIForTarget">	</div>
		
		
	</div>
</body>
</html>
