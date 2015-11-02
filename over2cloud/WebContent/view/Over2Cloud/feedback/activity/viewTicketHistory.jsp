<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">

	td
	{
		padding: 2px;
		text-align: center;
	}
</style>

<script type="text/javascript">
	function getDataForPrint() 
	{
		$("#takeActionGrid").dialog('close');
		 var printContents = document.getElementById("dataForPrint").innerHTML;
		 var myWindow = window.open("","myWindow","width=900,height=600"); 
		 myWindow.document.write(printContents);
		 myWindow.moveTo(300,200); 
		 myWindow.print();
		 myWindow.close();
	}

</script>
</head>
<body>
	<sj:a  button="true" cssStyle="height:25px; width:32px;float:right;"  cssClass="button" buttonIcon="ui-icon-print" title="Print" onClick="getDataForPrint()" ></sj:a>
	<div id="dataForPrint">
		<table border="1" width="100%" style="border-collapse: collapse;">
	   
				 <tr bgcolor="#D8D8D8" style="height: 25px">
						<th width="10%"><b>UHID: </b></th><td width="10%"><s:property value="%{clientId}"/></td>
						<th width="10%"><b>Episode No: </b></th><td width="10%"><s:property value="%{patientId}"/></td>
				</tr>
				
				<tr  style="height: 25px">
						<th width="10%"><b>Name: </b></th><td width="10%"><s:property value="%{patientName}"/></td>
						<th width="10%"><b>Location: </b></th><td width="10%"><s:property value="%{centerCode}"/></td>
				</tr>
				<tr bgcolor="#D8D8D8" style="height: 25px">
						<th width="10%"><b>Status: </b></th><td width="10%"><s:property value="%{status}"/></td>
						<th width="10%"><b>Open Date: </b></th><td width="10%"><s:property value="%{date}"/></td>
				</tr>
				<tr style="height: 25px">
						<th width="10%"><b>Department: </b></th><td width="10%"><s:property value="%{dept}"/></td>
						<th width="10%"><b>Category: </b></th><td width="10%"><s:property value="%{cat}"/></td>
				</tr>
				
				<tr bgcolor="#D8D8D8"  style="height: 25px">
						<th width="10%"><b>Sub Category: </b></th><td width="10%"><s:property value="%{subCat}"/></td>
						<th width="10%"><b>Brief: </b></th><td width="10%"><s:property value="%{brief}"/></td>
				</tr>
		</table>
		
	<div class="contentdiv-small" style="overflow: auto;  height:355px; width:98%;  margin-left:0%; margin-right:1px;   border-color: black; background-color: #D8D8D8" >
    <div class="clear"></div>
           <div style="height:10px; margin-bottom:2px;" id='1stBlock'></div>
                       <table  align="center" border="0" width="98%" height="7%" style="border-style:dotted solid;">
                               <tbody>
                                    <tr bgcolor="black" bordercolor="black" >
	                                       <td align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Status</b></font></td>
	                                       <td align="center" width="15%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Action Date</b></font></td>
	                                       <td align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Action By</b></font></td>
	                                       <td align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Escalate Level</b></font></td>
	                                       <td align="center" width="15%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Escalate To</b></font></td>
	                                       <td align="center" width="25%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Action Reason</b></font></td>
	                                       <td align="center" width="25%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>CAPA</b></font></td>
	                                </tr>
	                                
	                                <s:iterator value="dataList" var="listVal1" status="counter1">
 									<tr>  <td align="center" width="10%"><s:property value="%{#listVal1[0]}" /></td>
                                           <td align="center" width="15%"><s:property value="%{#listVal1[1]}" /></td>
                                           <td align="center" width="10%"><s:property value="%{#listVal1[2]}" /></td>
                                           <td align="center" width="10%"><s:property value="%{#listVal1[5]}" /></td>
                                           <td align="center" width="15%"><s:property value="%{#listVal1[6]}" /></td>
                                            <td align="center" valign="middle" width="25%" style="width: 200px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;position: absolute;" title="<s:property value="%{#listVal1[3]}" />"><s:property value="%{#listVal1[3]}" /></td>
                                           <td align="center" valign="middle" width="25%" style="width: 150px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;position: absolute;margin-left: 221px;" title="<s:property value="%{#listVal1[4]}" />"><s:property value="%{#listVal1[4]}" /></td>
                                    </tr>
                                   
                                   </s:iterator>
                                    
                               </tbody>
                       </table>
                 </div>
       </div>          
</body>
</html>