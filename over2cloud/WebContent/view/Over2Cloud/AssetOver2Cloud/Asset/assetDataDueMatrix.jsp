<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="s"  uri="/struts-tags"%>
    <%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<script type="text/javascript">
function showSuportDetails(deptId,dataFor,type)
{
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/showAllSupportDetails.action?deptId="+deptId+"&dataFor="+dataFor+"&type="+type+"",
	    success : function(data) 
	    {
	    	$("#dataDialog").dialog('open');
			$("#dataDialog").html(data);
	},
	   error: function() {
	        alert("error");
	    }
	 });
}


</script>
</head>
<body>
<div style="height:auto; margin-bottom:10px;" ><B>Asset Due Action Matrix</B></div>
<table border="1" width="100%" align="center" >
<tbody>
<tr bgcolor="FF6699">
<td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Type</b></font></td>
<td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>7 Days</b></font></td>
<td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>30 Days</b></font></td>
<td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>90 Days</b></font></td>
<td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>180 Days</b></font></td>
</tr>
 
 <s:iterator id="assetList2"  status="status" value="%{supportForDashboard.assetList}" > 
<tr bgcolor="#FFB6C1">
<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#0000FF" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b><s:property value="#assetList2.assetName" /></b>   </a></font></td>
<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="showSuportDetails('<s:property value="#assetList2.departId"/>','7Days','Support');" href="#"><s:property value="#assetList2.next7Days" /></a></font></td>
<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="showSuportDetails('<s:property value="#assetList2.departId"/>','30Days','Support');" href="#"><s:property value="#assetList2.next30Days" /></a></font></td>
<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="showSuportDetails('<s:property value="#assetList2.departId"/>','90Days','Support');" href="#"><s:property value="#assetList2.next90Days" /></a></font></td>
<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="showSuportDetails('<s:property value="#assetList2.departId"/>','180Days','Support');" href="#"><s:property value="#assetList2.next180Days" /></a></font></td>
</tr>
</s:iterator>
 <s:iterator id="assetList2"  status="status" value="%{expireForDashboard.assetList}" > 
<tr bgcolor="#FFB6C1">
<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#0000FF" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b><s:property value="#assetList2.assetName" /></b></a></font></td>
<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="showSuportDetails('<s:property value="#assetList2.departId"/>','7Days','Preventive');" href="#"><s:property value="#assetList2.next7Days" /></a></font></td>
<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="showSuportDetails('<s:property value="#assetList2.departId"/>','30Days','Preventive');" href="#"><s:property value="#assetList2.next30Days" /></a></font></td>
<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="showSuportDetails('<s:property value="#assetList2.departId"/>','90Days','Preventive');" href="#"><s:property value="#assetList2.next90Days" /></a></font></td>
<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="showSuportDetails('<s:property value="#assetList2.departId"/>','180Days','Preventive');" href="#"><s:property value="#assetList2.next180Days" /></a></font></td>
</tr>
</s:iterator>
</tbody>
</table>
</body>
</html>