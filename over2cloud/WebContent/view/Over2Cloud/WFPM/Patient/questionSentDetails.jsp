<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
         <%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<s:if test="profilemap.size()>0">
 <table width="100%" border="1" style="border-collapse: collapse;" align="center">
        <tr bgcolor="#D8D8D8" style="height: 25px">
          <s:iterator value="%{profilemap}" status="status" >
            <s:if test="%{key=='Sent Date'}">
                    <td align="center" width="15%" ><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="center" width="30%"> <s:property value="%{value}"/></td>
            </s:if>
        </s:iterator>
        </tr>
         <tr style="height: 25px">
          <s:iterator value="%{profilemap}" status="status" >
            <s:if test="%{key=='Sent Time'}">
                    <td align="center" width="15%" ><strong><s:property value="%{key}"/>: </strong></td>
                    <td align="center" width="30%">  <s:property value="%{value}"/></td>
            </s:if>
        </s:iterator>
        </tr>
      
</table> 
</s:if>
 
<s:else>
<center><b><h1><font style="font-style: italic;">There is no data</font></h1></b></center>
</s:else>
</body>
</html>