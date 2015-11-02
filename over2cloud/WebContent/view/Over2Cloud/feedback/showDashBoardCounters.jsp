<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link href="<s:url value="/css/style.css"/>" rel="stylesheet" type="text/css"/>
<SCRIPT type="text/javascript">
function showCounterDetails(type,header)
{
	var conP = "<%=request.getContextPath()%>";
	if(header=='Counters For Positive & Negative')
	{
		header="Feedback";
		//alert("URL is as >>>"+conP+"/view/Over2Cloud/feedback/showDashboardCountDetails.action?headerName="+header.split(" ").join("%20")+"&counterType="+type.split(" ").join("%20"));
		$.ajax({
		    type : "post",
		    url : conP+"/view/Over2Cloud/feedback/showDashboardCountDetails.action?headerName="+header.split(" ").join("%20")+"&counterType="+type.split(" ").join("%20"),
		    success : function(subdeptdata) {
			$("#mybuttondialog123").html(subdeptdata);
		       $("#mybuttondialog123").dialog('open');
		},
		   error: function() {
	            alert("error");
	        }
		 });	
	}
}
</SCRIPT>
<style type="text/css">
	 .handSymbol{
    cursor: pointer;
	}	
</style>
</head>
<body>
<s:hidden id='tabDataType' name="tabDataType" value="data"></s:hidden>
<s:hidden id='deptDataType' name="deptDataType" value="data"></s:hidden>
<div class="headding" align="center" style="margin-left: 0px;"><CENTER><h3><s:property value="%{counterDataHeader}"  /></h3></CENTER></div>
<center>	
<div style="margin:0 auto; width:80%">
<table  align="center" width="100%" height="40%">
		<tr>
			<td width="60%;" align="center" class="tableHeadMaroon">
				<s:property value="%{labelName}"  />
			</td>
			<td width="30%" align="center" class="tableHeadMaroon">
				Counters
			</td>
		</tr>
		<s:iterator id="news" value="%{counterList}">
		<tr>
			<td align="center" class="tableBodyMaroon" >
				<b><s:property value="%{name}"/></b>
			</td>
			<td align="center" class="tableBodyMaroon" onclick="showCounterDetails('<s:property value="name"/>','<s:property value="%{counterDataHeader}"  />')">
				<div class="handSymbol">
					<s:property value="%{count}"/>
				</div>
			</td>
		</tr>
		</s:iterator>
	</table>
	</div>
	</center>
</body>
</html>