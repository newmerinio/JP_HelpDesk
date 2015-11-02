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
<s:if test="orgmap.size()>0">
 <table width="100%" border="1" style="border-collapse: collapse;" align="center">
        <tr bgcolor="#D8D8D8" style="height: 25px">
          <s:iterator value="%{orgmap}" status="status" >
            <s:if test="%{key=='Contact No' || key=='Website'}">
             <s:if test="%{key=='Website'}">
                    <td align="center" width="15%"><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="center" width="30%" >
                    <s:if test="%{ value == 'NA'}">NA</s:if>
                    <s:else><a href="http://<s:property value='%{value}'/>" target="new"><FONT color="blue"><s:property value="%{value}"/></FONT></a></s:else>
                    </td>
            </s:if>
            <s:else>
                    <td align="center" width="15%" ><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="center" width="30%"> 
                    <s:if test="%{ value == '-1'}">NA</s:if>
                    <s:else> <s:property value="%{value}"/></s:else></td>
                   </s:else> 
            </s:if>
        </s:iterator>
        </tr>
         <tr style="height: 25px">
         <s:iterator value="%{orgmap}" status="status" >
            <s:if test="%{key=='Landmark' || key=='Fax'}">
                    <td align="center" width="15%"><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="center" width="30%" >
                    <s:if test="%{ value == 'NA'}">NA</s:if>
                    <s:else><a href="http://<s:property value='%{value}'/>" target="new"><FONT color="blue"><s:property value="%{value}"/></FONT></a></s:else>
                    </td>
            </s:if>
        </s:iterator> 
        </tr>
         <tr bgcolor="#D8D8D8" style="height: 25px">
         <s:iterator value="%{orgmap}" status="status" >
            <s:if test="%{key=='Source' || key=='Account Manager'}">
                    <td align="center" width="15%"><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="center" width="30%" ><s:property value="%{value}"/></td>
            </s:if>
        </s:iterator>
       </tr>
           <tr style="height: 25px">
         <s:iterator value="%{orgmap}" status="status" >
            <s:if test="%{key=='Address' }">
                    <td align="center" width="15%"><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="center" width="30%"> 
                    <s:if test="%{ value == '-1'}">NA</s:if>
                    <s:else> <s:property value="%{value}"/></s:else></td>
            </s:if>
        </s:iterator>
         		 <td align="center" width="15%"><strong>Google Map:</strong></td>
         		 <td align="center" width="30%"><font color="blue">Map</font></td>
          </tr>
        <tr bgcolor="#D8D8D8" style="height: 25px">
         <s:iterator value="%{orgmap}" status="status" >
            <s:if test="%{key=='City' || key=='State'}">
                    <td align="center" width="15%" ><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="center" width="30%"> <s:property value="%{value}"/></td>
            </s:if>
        </s:iterator>
       </tr>
        <tr  style="height: 25px">
         <s:iterator value="%{orgmap}" status="status" >
            <s:if test="%{key=='Country' || key=='Created By'}">
                    <td align="center" width="15%"><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="center" width="30%" ><s:property value="%{value}"/></td>
            </s:if>
        </s:iterator> 
        </tr>
        <tr bgcolor="#D8D8D8" style="height: 25px">
         <s:iterator value="%{orgmap}" status="status" >
            <s:if test="%{key=='Created On'}">
                    <td align="center" width="15%" ><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="center" width="30%"> <s:property value="%{value}"/></td>
            </s:if>
        </s:iterator>
        <td align="center" width="15%" ><strong></strong></td>
        <td align="center" width="30%"></td>
       </tr>
     
</table> 
</s:if>
<s:elseif test="contactmap.size()>0">
 <table width="100%" border="1" style="border-collapse: collapse;" align="center">
        <tr bgcolor="#D8D8D8" style="height: 25px">
          <s:iterator value="%{contactmap}" status="status" >
            <s:if test="%{key=='Name'}">
                    <td align="center" width="15%" ><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="center" width="30%"> 
                    <s:if test="%{ value == '-1'}">NA</s:if>
                    <s:else> <s:property value="%{value}"/></s:else></td>
            </s:if>
        </s:iterator>
        </tr>
         <tr style="height: 25px">
         <s:iterator value="%{contactmap}" status="status" >
            <s:if test="%{key=='Communication Name'}">
                    <td align="center" width="15%"><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="center" width="30%" ><s:property value="%{value}"/></td>
            </s:if>
        </s:iterator> 
        </tr>
         <tr bgcolor="#D8D8D8" style="height: 25px">
         <s:iterator value="%{contactmap}" status="status" >
            <s:if test="%{key=='Mobile No'}">
                    <td align="center" width="15%"><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="center" width="30%" ><s:property value="%{value}"/></td>
            </s:if>
        </s:iterator>
       </tr>
           <tr style="height: 25px">
         <s:iterator value="%{contactmap}" status="status" >
            <s:if test="%{key=='Email Id'}">
                    <td align="center" width="15%"><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="center" width="30%"> 
                    <s:if test="%{ value == '-1'}">NA</s:if>
                    <s:else> <s:property value="%{value}"/></s:else></td>
            </s:if>
        </s:iterator>
       </tr>
        <tr bgcolor="#D8D8D8" style="height: 25px">
         <s:iterator value="%{contactmap}" status="status" >
            <s:if test="%{key=='DOB'}">
                    <td align="center" width="15%" ><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="center" width="30%"> <s:property value="%{value}"/></td>
            </s:if>
        </s:iterator>
       </tr>
        <tr  style="height: 25px">
         <s:iterator value="%{contactmap}" status="status" >
            <s:if test="%{key=='Anniversary Date'}">
                    <td align="center" width="15%"><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="center" width="30%" ><s:property value="%{value}"/></td>
            </s:if>
        </s:iterator> 
        </tr>
        <tr bgcolor="#D8D8D8" style="height: 25px">
         <s:iterator value="%{contactmap}" status="status" >
            <s:if test="%{key=='Designation'}">
                    <td align="center" width="15%" ><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="center" width="30%"> <s:property value="%{value}"/></td>
            </s:if>
        </s:iterator>
       </tr>
       <tr style="height: 25px">
          <s:iterator value="%{contactmap}" status="status" >
            <s:if test="%{key=='Department'}">
                    <td align="center" width="15%"><strong><s:property value="%{key}"/>:</strong></td>
                     <td align="center" width="30%"> 
                    <s:if test="%{ value == '-1'}">NA</s:if>
                    <s:else> <s:property value="%{value}"/></s:else></td>
            </s:if>
        </s:iterator>
        </tr>
        <tr bgcolor="#D8D8D8" style="height: 25px">
                <s:iterator value="%{contactmap}" status="status" >
            <s:if test="%{key=='Gender'}">
                    <td align="center" width="15%"><strong><s:property value="%{key}"/>:</strong></td>
                     <td align="center" width="30%"> 
                    <s:if test="%{ value == '-1'}">NA</s:if>
                    <s:else> <s:property value="%{value}"/></s:else></td>
            </s:if>
        </s:iterator>
        </tr>
</table> 
</s:elseif>
<s:elseif test="statusmap.size()>0">
 <table width="100%" border="1" style="border-collapse: collapse;" align="center">
        <tr bgcolor="#D8D8D8" style="height: 25px">
          <s:iterator value="%{statusmap}" status="status" >
            <s:if test="%{key=='Requested By'}">
                    <td align="center" width="15%" ><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="center" width="30%"> 
                    <s:if test="%{ value == '-1'}">NA</s:if>
                    <s:else> <s:property value="%{value}"/></s:else></td>
            </s:if>
        </s:iterator>
        </tr>
         <tr style="height: 25px">
         <s:iterator value="%{statusmap}" status="status" >
            <s:if test="%{key=='Requested On'}">
                    <td align="center" width="15%"><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="center" width="30%" ><s:property value="%{value}"/></td>
            </s:if>
        </s:iterator> 
        </tr>
         <tr bgcolor="#D8D8D8" style="height: 25px">
         <s:iterator value="%{statusmap}" status="status" >
            <s:if test="%{key=='Requested To'}">
                    <td align="center" width="15%"><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="center" width="30%" ><s:property value="%{value}"/></td>
            </s:if>
        </s:iterator>
       </tr>
           <tr style="height: 25px">
         <s:iterator value="%{statusmap}" status="status" >
            <s:if test="%{key=='Action On'}">
                    <td align="center" width="15%"><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="center" width="30%"> 
                    <s:if test="%{ value == '-1'}">NA</s:if>
                    <s:else> <s:property value="%{value}"/></s:else></td>
            </s:if>
        </s:iterator>
       </tr>
        <tr bgcolor="#D8D8D8" style="height: 25px">
         <s:iterator value="%{statusmap}" status="status" >
            <s:if test="%{key=='Current Status'}">
                    <td align="center" width="15%" ><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="center" width="30%"> <s:property value="%{value}"/></td>
            </s:if>
        </s:iterator>
       </tr>
        <tr  style="height: 25px">
         <s:iterator value="%{statusmap}" status="status" >
            <s:if test="%{key=='Comments'}">
                    <td align="center" width="15%"><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="center" width="30%" ><s:property value="%{value}"/></td>
            </s:if>
        </s:iterator> 
        </tr>
        <tr bgcolor="#D8D8D8" style="height: 25px">
              <td align="center" width="15%"><strong></strong></td>
                     <td align="center" width="30%">           </td>
        </tr>
</table> 
</s:elseif>
<s:elseif test="statusmap1.size()>0">
 <table width="100%" border="1" style="border-collapse: collapse;" align="center">
        <tr bgcolor="#D8D8D8" style="height: 25px">
          <s:iterator value="%{statusmap1}" status="status" >
            <s:if test="%{key=='Requested By'}">
                    <td align="center" width="15%" ><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="center" width="30%"> 
                    <s:if test="%{ value == '-1'}">NA</s:if>
                    <s:else> <s:property value="%{value}"/></s:else></td>
            </s:if>
        </s:iterator>
        </tr>
         <tr style="height: 25px">
         <s:iterator value="%{statusmap1}" status="status" >
            <s:if test="%{key=='Requested On'}">
                    <td align="center" width="15%"><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="center" width="30%" ><s:property value="%{value}"/></td>
            </s:if>
        </s:iterator> 
        </tr>
         <tr bgcolor="#D8D8D8" style="height: 25px">
         <s:iterator value="%{statusmap1}" status="status" >
            <s:if test="%{key=='Requested To'}">
                    <td align="center" width="15%"><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="center" width="30%" ><s:property value="%{value}"/></td>
            </s:if>
        </s:iterator>
       </tr>
           <tr style="height: 25px">
         <s:iterator value="%{statusmap1}" status="status" >
            <s:if test="%{key=='Action On'}">
                    <td align="center" width="15%"><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="center" width="30%"> 
                    <s:if test="%{ value == '-1'}">NA</s:if>
                    <s:else> <s:property value="%{value}"/></s:else></td>
            </s:if>
        </s:iterator>
       </tr>
        <tr bgcolor="#D8D8D8" style="height: 25px">
         <s:iterator value="%{statusmap1}" status="status" >
            <s:if test="%{key=='Current Status'}">
                    <td align="center" width="15%" ><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="center" width="30%"> <s:property value="%{value}"/></td>
            </s:if>
        </s:iterator>
       </tr>
        <tr  style="height: 25px">
         <s:iterator value="%{statusmap1}" status="status" >
            <s:if test="%{key=='Comments'}">
                    <td align="center" width="15%"><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="center" width="30%" ><s:property value="%{value}"/></td>
            </s:if>
        </s:iterator> 
        </tr>
        <tr bgcolor="#D8D8D8" style="height: 25px">
              <td align="center" width="15%"><strong></strong></td>
                     <td align="center" width="30%">           </td>
        </tr>
</table> 
</s:elseif>
<s:elseif test="indmap.size()>0">
 <table width="100%" border="1" style="border-collapse: collapse;" align="center">
        <tr bgcolor="#D8D8D8" style="height: 25px">
          <s:iterator value="%{indmap}" status="status" >
            <s:if test="%{key=='Name' || key=='Communication Name'}">
                    <td align="center" width="15%" ><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="center" width="30%"> 
                    <s:if test="%{ value == '-1'}">NA</s:if>
                    <s:else> <s:property value="%{value}"/></s:else></td>
            </s:if>
        </s:iterator>
        </tr>
         <tr style="height: 25px">
         <s:iterator value="%{indmap}" status="status" >
            <s:if test="%{key=='Mobile No' || key=='Email Id'}">
                    <td align="center" width="15%"><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="center" width="30%" ><s:property value="%{value}"/></td>
            </s:if>
        </s:iterator> 
        </tr>
         <tr bgcolor="#D8D8D8" style="height: 25px">
         <s:iterator value="%{indmap}" status="status" >
            <s:if test="%{key=='DOB' || key=='DOA'}">
                    <td align="center" width="15%"><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="center" width="30%" ><s:property value="%{value}"/></td>
            </s:if>
        </s:iterator>
       </tr>
           <tr style="height: 25px">
         <s:iterator value="%{indmap}" status="status" >
            <s:if test="%{key=='Address' || key=='City'}">
                    <td align="center" width="15%"><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="center" width="30%"> 
                    <s:if test="%{ value == '-1'}">NA</s:if>
                    <s:else> <s:property value="%{value}"/></s:else></td>
            </s:if>
        </s:iterator>
       </tr>
        <tr bgcolor="#D8D8D8" style="height: 25px">
         <s:iterator value="%{indmap}" status="status" >
            <s:if test="%{key=='State' || key=='Country'}">
                    <td align="center" width="15%" ><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="center" width="30%"> <s:property value="%{value}"/></td>
            </s:if>
        </s:iterator>
       </tr>
        <tr  style="height: 25px">
         <s:iterator value="%{indmap}" status="status" >
            <s:if test="%{key=='Created By' || key=='Created On'}">
                    <td align="center" width="15%"><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="center" width="30%" ><s:property value="%{value}"/></td>
            </s:if>
        </s:iterator> 
        </tr>
</table> 
</s:elseif>
<s:elseif test="corpmap.size()>0">
 <table width="100%" border="1" style="border-collapse: collapse;" align="center">
        <tr bgcolor="#D8D8D8" style="height: 25px">
          <s:iterator value="%{corpmap}" status="status" >
            <s:if test="%{key=='Corporate Type'|| key=='Address'}">
                    <td align="center" width="15%" ><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="center" width="30%"> 
                    <s:if test="%{ value == '-1'}">NA</s:if>
                    <s:else> <s:property value="%{value}"/></s:else></td>
            </s:if>
        </s:iterator>
        </tr>
         <tr style="height: 25px">
         <s:iterator value="%{corpmap}" status="status" >
            <s:if test="%{key=='Country' || key=='State'}">
                    <td align="center" width="15%"><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="center" width="30%" ><s:property value="%{value}"/></td>
            </s:if>
        </s:iterator> 
        </tr>
         <tr bgcolor="#D8D8D8" style="height: 25px">
         <s:iterator value="%{corpmap}" status="status" >
            <s:if test="%{key=='City' || key=='POC Name'}">
                    <td align="center" width="15%"><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="center" width="30%" ><s:property value="%{value}"/></td>
            </s:if>
        </s:iterator>
       </tr>
           <tr style="height: 25px">
         <s:iterator value="%{corpmap}" status="status" >
            <s:if test="%{key=='Mobile No' || key=='Email Id'}">
                    <td align="center" width="15%"><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="center" width="30%"> 
                    <s:if test="%{ value == '-1'}">NA</s:if>
                    <s:else> <s:property value="%{value}"/></s:else></td>
            </s:if>
        </s:iterator>
       </tr>
        <tr bgcolor="#D8D8D8" style="height: 25px">
         <s:iterator value="%{corpmap}" status="status" >
            <s:if test="%{key=='Created By' || key=='Created On'}">
                    <td align="center" width="15%" ><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="center" width="30%"> <s:property value="%{value}"/></td>
            </s:if>
        </s:iterator>
       </tr>
</table> 
</s:elseif>

<s:else>
<center><b><h1><font style="font-style: italic;">There is no data</font></h1></b></center>
</s:else>
</body>
</html>