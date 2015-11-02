<%@taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>

<html>
<head>
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<meta   http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="<s:url value="/js/WFPMReport/validation.js"/>"></script>
<SCRIPT type="text/javascript">
$.subscribe('complete', function(event,data)
 {   
    $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$("#data_part").load(encodeURI("view/Over2Cloud/wfpm/client/viewClientFullHistory.action?id="+id+"&isExistingClient="+isExistingClient+"&mainHeaderName="+mainHeaderName));
 });


</SCRIPT>
	
<title>Offering details</title>
</head>
<body>
 <s:if test="offeringNameMap.size()>0">
<table width="100%" border="1" style="border-collapse: collapse;">
        <tr bgcolor="#D8D8D8" style="height: 25px">
          <s:iterator value="%{offeringNameMap}" status="status" >
            <s:if test="%{key=='Lost By' || key=='Lost Date'}">
                    <td align="left"  ><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="left" > <s:property value="%{value}"/></td>
            </s:if>
        </s:iterator>
        </tr>
        <tr style="height: 25px">
         <s:iterator value="%{offeringNameMap}" status="status" >
           <s:if test="%{key=='Lost Reason' || key=='RCA'}">
                     <td align="left"  ><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="left" > <s:property value="%{value}"/></td>
           </s:if>
       </s:iterator>
      </tr>
     <tr bgcolor="#D8D8D8" style="height: 25px">
          <s:iterator value="%{offeringNameMap}" status="status" >
            <s:if test="%{key=='CAPA'}">
                    <td align="left"  ><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="left" > <s:property value="%{value}"/></td>
            </s:if>
        </s:iterator>
        <td align="left"></td>
         <td align="left"></td>
        </tr>
</table>
</s:if>
<s:else>
<center><b><h1><font style="font-style: italic;">There is no data</font></h1></b></center>
</s:else>

 
</body>
</html>