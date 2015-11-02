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
<script type="text/javascript" src="<s:url value="js/KPI/KPIMapping.js"/>"></script>
<SCRIPT type="text/javascript">
function getAllSwapAndSwappedWithData()
{
	$.ajax({
	    type : "post",
	    url : "<%=request.getContextPath()%>/view/Over2Cloud/hr/getAllSwapAndSwappedWithDataFromAction.action",
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

function mapKRAKPIJS(empID) {

	var val = $("#empName").val();
	if(val == null || val == "" || val == '-1')
	{
		$("#empView").html();
	}
	else
	{
		$("#empName").css("background-color","#ffffff"); 
		$("#empView").load(encodeURI("view/Over2Cloud/hr/mappedKRAKPIWithSubDept.action?empID="+empID+"&moduleName=${moduleName}"));
	}
}

function selectAll(value)
{
       	$("[name='pid']").attr('checked', value.checked);
}


</script>
</head>
<body>

<div class="clear"></div>
<div class="list-icon">
	<!-- <div class="head">Map KPI With Employee</div> -->
<div class="head"><s:property value="moduleName"/></div>
<div class="head"><img alt="" src="images/forward.png" style="margin-top:60%; float: left;"></div>
<div class="head">Add</div>
</div>
<br><br><br>
<div class="border">
<div style=" float:left; padding:20px 1%; width:98%;">
<div class="form_inner" id="form_reg">
<div class="page_form">
<s:form id="formtwo" action="mapKRAKPI" theme="simple" name="formtwo" namespace="/view/Over2Cloud/hr">
<div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
             <div id="errZone" style="float:left; margin-left: 7px">
             </div> 
    </div>
	  			 <div class="newColumn">
	                  <div class="leftColumn1">Employee Name:</div>
	                  <div class="rightColumn1"><span class="needed"></span>
	                  <s:select 
	                              cssClass="select"
                                  cssStyle="width:82%"
	                              id="empName"
	                              name="empID" 
	                              list="empDataList" 
	                              headerKey="-1"
	                              headerValue="Select Employee Name" 
	                              onchange="mapKRAKPIJS(this.value);"
	                              > 
	                  </s:select>
	                  </div>
			     </div>
<br><br><br><br>
<div id="empView" style="width:  80%; margin: auto;"></div>

<br><br>
<div class="buttonStyle" style="text-align: center;">
        <sj:submit 
                  targets="Resultmap" 
                  clearForm="true"
                  value=" Map " 
                  effect="highlight"
                  effectOptions="{ color : '#222222'}"
                  button="true"
                  onCompleteTopics="complete1"
                  onBeforeTopics="checkEmplyeeSelection"
                  cssClass="submit"
                  />
                        
        <sj:a 
		     	name="Cancel"  
				href="#" 
				cssClass="button" 
				indicator="indicator" 
				button="true" 
				onclick="viewKraKpiMapping();"
			>
			  	Back
			</sj:a>
               </div>
</s:form>  
</div>
</div>

<sj:div id="foldeffect"  effect="fold">
      <div id="Resultmap"></div>
</sj:div>
</div>
</div>
</body>
</html>
