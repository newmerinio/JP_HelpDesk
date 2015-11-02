<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<script type="text/javascript">

	function addKrAndCompletionTip(taskId)
	{
		$("#completionTipDialog").dialog('close');
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/Compliance/compl_task_page/beforeAddKRCompTipInTask.action?taskId="+taskId,
		    success : function(data) 
		    {
				$("#data_part").html(data);
		    },
		    error: function() 
		    {
	            alert("error");
	        }
			 });

	}

</script>

</head>
<body>
	<s:if test="%{dataFor=='KRName'}">
		<s:if test="%{dataList.size()>0}">
		
		<table width="100%" border="1" style="border-collapse: collapse;">
        
        <tr bgcolor="#D8D8D8" style="height: 25px">
                    <td  width="40%" align="left" ><strong style="margin-left: 14%;">Task Type:</strong></td>
                    <td align="left" width="60%"> <s:property value="%{taskType}"/></td>
         </tr>
         <tr style="height: 25px">           
                     <td align="left" width="40%" ><strong style="margin-left: 14%;">Task Name:</strong></td>
                    <td align="left" width="60%"> <s:property value="%{taskName}"/></td>
        </tr>
        </table>
         <br>
		
		<table border="1" width="100%" align="center">
		<tbody>
		<tr bgcolor="#D8D8D8">
			<td  align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>S.No</b></font></td>
			<td  align="center" width="25%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>KR Name</b></font></td>
			<td  align="center" width="25%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Doc Name</b></font></td>
		</tr>
		<s:iterator id="totalKRId"  status="status" value="%{dataList}" >
		 <s:if test="#status.odd">
			<tr >
				<td align="center" width="10%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1">
					<s:property value="%{#status.count}"/>
				</font></td>
				<td align="center" width="25%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1">
					<s:property value="#totalKRId.name"/>
				</font></td>
				<td align="center" width="25%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1">
					<a href="<%=request.getContextPath()%>/view/Over2Cloud/Compliance/compliance_pages/confirmDownload.action?moduleName=KR&fileName=<s:property value="#totalKRId.orginalDocPath"/>"><B><s:property value="#totalKRId.docPath"/></B></a>
				</font></td>
			</tr>
			</s:if>
			<s:else>
			<tr bgcolor="#D8D8D8">
				<td align="center" width="10%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1">
					<s:property value="%{#status.count}"/>
				</font></td>
				<td align="center" width="25%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1">
					<s:property value="#totalKRId.name"/>
				</font></td>
				<td align="center" width="25%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1">
					<a href="<%=request.getContextPath()%>/view/Over2Cloud/Compliance/compliance_pages/confirmDownload.action?fileName=<s:property value="#totalKRId.orginalDocPath"/>"><B><s:property value="#totalKRId.docPath"/></B></a>
				</font></td>
			</tr>
			
			
			</s:else>
		</s:iterator>
		</tbody>
		</table>
	</s:if>
	<s:else>
		<center>There are no KR mapped for <s:property value="%{taskName}" />.To add KR <a href="#" onclick="addKrAndCompletionTip(<s:property value='%{dataId}'/>)"><font color="blue">Click Here</font></a></center>
	</s:else>
	</s:if>
	
	
	<s:if test="%{dataFor=='CompletionTip'}">
	<s:if test="%{dataList.size()>0}">
	 <table width="100%" border="1" style="border-collapse: collapse;">
         <tr bgcolor="#D8D8D8" style="height: 25px">
                    <td  width="40%" align="left"><strong style="margin-left: 14%;">Task Type:</strong></td>
                    <td align="left" width="60%"> <s:property value="%{taskType}"/></td>
         </tr>
         <tr style="height: 25px">           
                     <td align="left" width="40%" ><strong style="margin-left: 14%;">Task Name:</strong></td>
                    <td align="left" width="60%"> <s:property value="%{taskName}"/></td>
        </tr>
     </table>
     
      <br>
	
		<table border="1" width="100%" align="center">
		<tbody>
		<tr bgcolor="#D8D8D8">
			<td  align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>S.No</b></font></td>
			<td  align="center" width="25%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Check List</b></font></td>
		</tr>
		<s:iterator id="totalTip"  status="status" value="%{dataList}" >
		 <s:if test="#status.odd">
			<tr>
				<td align="center" width="10%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1">
					<s:property value="%{#status.count}"/>
				</font></td>
				<td align="center" width="25%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1">
					<s:property value="#totalTip.name"/>
				</font></td>
			</tr>
			</s:if>
			<s:else>
			<tr bgcolor="#D8D8D8">
				<td align="center" width="10%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1">
					<s:property value="%{#status.count}"/>
				</font></td>
				<td align="center" width="25%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1">
					<s:property value="#totalTip.name"/>
				</font></td>
			</tr>
			
			</s:else>
		</s:iterator>
		</table>
	</s:if>
	<s:else>
		<center>There are no Completion Tip mapped for <s:property value="%{taskName}" />.To add Completion Tip <a href="#" onclick="addKrAndCompletionTip(<s:property value='%{dataId}'/>)"><font color="blue">Click Here</font></a></center>
	</s:else>
	</s:if>
	
	<s:if test="%{dataFor=='RefRec'}">
	<s:if test="%{dataList.size()>0}">
		<table border="1" width="100%" align="center">
		<tbody>
		<tr bgcolor="#D8D8D8">
			<td  align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>S.No</b></font></td>
			<td  align="center" width="25%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Reference Record</b></font></td>
		</tr>
		<s:iterator id="totalTip"  status="status" value="%{dataList}" >
		 <s:if test="#status.odd">
			<tr>
				<td align="center" width="10%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1">
					<s:property value="%{#status.count}"/>
				</font></td>
				<td align="center" width="25%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1">
					<s:property value="#totalTip.name"/>
				</font></td>
			</tr>
			</s:if>
			<s:else>
			<tr bgcolor="#D8D8D8">
				<td align="center" width="10%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1">
					<s:property value="%{#status.count}"/>
				</font></td>
				<td align="center" width="25%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1">
					<s:property value="#totalTip.name"/>
				</font></td>
			</tr>
			
			</s:else>
		</s:iterator>
		</table>
	</s:if>
	<s:else>
		<center>There are no Reference Record mapped for this <s:property value="%{taskName}" />.</center>
	</s:else>
	</s:if>
	
</body>
</html>