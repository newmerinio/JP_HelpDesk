<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString(); %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript">
function selectExcel(value,destAjaxDiv){
	$.ajax({
		type :"post",
		url :"/over2cloud/view/Over2Cloud/leaveManagement/documentPolicy.action?empStatus="+value,
		success : function(subDeptData){
		$("#"+destAjaxDiv).html(subDeptData);
	    },
	    error : function () {
			alert("Somthing is wrong");
		}
	});
    }
</script>
<script type="text/javascript" src="<s:url value="/js/leaveManagement/validation.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/leaveManagement/leave.js"/>"></script>
</head>
<body>
<s:url id="fileDownload" action="download" ></s:url>
 
 
 <div class="list-icon">
	 <div class="head">
	 Leave Policy </div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">Document Download</div> 
</div>
 
<%if(userRights.contains("lpoli_ADD")){ %>
 <div style=" float:left; width:100%;">
 <div class="border">
		<s:form id="formone" name="formone" namespace="/view/Over2Cloud/leaveManagement" action="policyAddAction" theme="simple"  method="post" enctype="multipart/form-data" >
             <div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
             <div id="errZone" style="float:left; margin-left: 7px"></div>        
             </div>
             <%if(userRights.contains("lpoli_ADD")){ %>
 <div class="rightHeaderButton">
<sj:a  button="true" onclick="uploadPolicy();" buttonIcon="ui-icon-plus">Upload Policy</sj:a>
</div>
<%} %>
             
				 <s:iterator value="policyColumnDropdown" status="counter">
				 <s:if test="%{mandatory}">
                      <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if>
			   <s:if test="#counter.count%2 != 0">
			   <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                  <s:select 
                              id="%{key}"
                              name="%{key}" 
                              list="employeeType"
                              headerKey="-1"
                              headerValue="Select Employee Type" 
                              onchange="selectExcel(this.value,'grp1')"
                              cssClass="select"
                              cssStyle="width:82%"
                              >
                     </s:select>
                     </div>
                </div>
                </s:if>
				</s:iterator>  
             <div id="grp1"> 
		    </div>
	</s:form>	
</div>
</div>
<%} %>
</body>
</html>