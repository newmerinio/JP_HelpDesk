<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<s:if test="%{fullViewMap.size()>0}">
 <table width="100%" border="1">
    		  <tr  bgcolor="lightgrey" style="height: 25px">
	    			<s:iterator value="fullViewMap" status="counter">
	   					 <s:if test="%{key=='KR ID' || key=='Document' || key=='KR Brief'}">
			    			<td align="left" ><strong><s:property value="%{key}"/>:</strong></td>
							<td align="left" ><s:property value="%{value}"/></td>
	   					 </s:if>
					</s:iterator>
		    </tr>
		    <tr  bgcolor="white" style="height: 25px">
			    <s:iterator value="fullViewMap" status="counter">
			  
			    <s:if test="%{key=='Tags' || key=='Group' || key=='Sub Group'}">
			    			<td align="left" ><strong><s:property value="%{key}"/>:</strong></td>
							<td align="left" ><s:property value="%{value}"/></td>
			    </s:if>
				</s:iterator>
		    </tr>
		    <tr  bgcolor="lightgrey" style="height: 25px">
			    <s:iterator value="fullViewMap" status="counter">
			  
			    <s:if test="%{key=='Department' || key=='Access Type' }">
			    			<td align="left" ><strong><s:property value="%{key}"/>:</strong></td>
							<td align="left" ><s:property value="%{value}"/></td>
							
			    </s:if>
				</s:iterator>
		    </tr>
		    
		   
    </table>

</s:if>

<s:if test="contactDetails.size()>0">
<table width="100%" border="0" style="border-collapse: collapse;">
 <s:iterator value="%{contactDetails}" id="totalCompl" status="status">
        <tr>
            <td>
                <table width="100%"  align="center" border="1" style="border-collapse: collapse;">
                   <s:if test="#status.odd">
                   		 <tr bgcolor="#D8D8D8" style="height: 25px;">
	                        <td align="left" width="12%"><strong>Name:</strong></td>
	                        <td  align="center" width="12%"><s:property value="#totalCompl.remindTo" /></td>
	                        <td align="left" width="12%"><strong>Mobile No:</strong></td>
	                        <td  align="center" width="12%"><s:property value="#totalCompl.mobNo" /></td>
	                        <td align="left" width="12%"><strong>Email Id:</strong></td>
	                        <td  align="center" width="12%"><s:property value="#totalCompl.emailId" /></td>
	                        <td align="left" width="12%"><strong>Department:</strong></td>
	                        <td  align="center" width="12%"><s:property value="#totalCompl.departName" /></td>
                        </tr>
                   </s:if>
                   <s:else>
                   		<tr style="height: 25px;">
	                        <td align="left" width="12%"><strong>Name:</strong></td>
	                        <td  align="center" width="12%"><s:property value="#totalCompl.remindTo" /></td>
	                        <td align="left" width="12%"><strong>Mobile No:</strong></td>
	                        <td  align="center" width="12%"><s:property value="#totalCompl.mobNo" /></td>
	                        <td align="left" width="12%"><strong>Email Id:</strong></td>
	                        <td  align="center" width="12%"><s:property value="#totalCompl.emailId" /></td>
	                        <td align="left" width="12%"><strong>Department:</strong></td>
	                        <td  align="center" width="12%"><s:property value="#totalCompl.departName" /></td>
                        </tr>
                   
                   
                   </s:else>
                </table>
            </td>
        </tr>
</s:iterator>
</table>
</s:if>
  </body>
</html>