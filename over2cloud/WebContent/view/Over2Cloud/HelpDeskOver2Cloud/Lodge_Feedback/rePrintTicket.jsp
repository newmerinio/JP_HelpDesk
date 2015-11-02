<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib  prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="<s:url value="/js/helpdesk/feedback.js"/>"></script>
<script>
function printDiv(divName) {
    var printContents = document.getElementById(divName).innerHTML;
    $("#printSelect").dialog('close');
    var myWindow = window.open("","myWindow","width=900,height=600"); 
    myWindow.document.write(printContents);
    myWindow.moveTo(300,200); 
    myWindow.print();
    myWindow.close();
}

function  CancelPrint()
{
   $("#printSelect").dialog('close');
}

</script>

</head>
<body>
<c:set var="open_date" value="${fstatus.open_date}" />
<c:set var="open_time" value="${fstatus.open_time}" />
<c:set var="ticketNo" value="${fstatus.ticket_no}" /> 
<c:set var="feedBackTo" value="${fstatus.empName}" /> 
<c:set var="feedBackToDept" value="${fstatus.feedback_to_dept}"/>
<c:set var="feedBackToSubDept" value="${fstatus.feedback_to_subdept}"/>
<c:set var="addDate" value="${fstatus.addr_date_time}"/>
<c:set var="subCat" value="${fstatus.feedback_subcatg}"/>
<c:set var="feedCat" value="${fstatus.feedback_catg}"/>
<c:set var="feedbrief" value="${fstatus.feed_brief}"/>
<c:set var="feedMobNo" value="${fstatus.mobOne}"/>
<c:set var="feedbackBy" value="${fstatus.feed_by}"/>
<c:set var="location" value="${fstatus.location}" />
<c:set var="resolvedBy" value="${fstatus.empName}" />
<c:set var="resDat" value="${fstatus.resolve_date}" />
<c:set var="resTime" value="${fstatus.resolve_time}" />
<c:set var="resCmnt" value="${fstatus.resolve_remark}" />
<c:set var="extno" value="${extno}" />
<c:set var="spareused" value="${fstatus.spare_used}" />
<c:set var="orgName" value="${orgName}" />
<c:set var="address" value="${address}" />
<c:set var="city" value="${city}" />
<c:set var="pincode" value="${pincode}" />
<div id="printDiv">
<table cellpadding="0" border="0" align="center" cellspacing="0" style="border-collapse: collapse" width="100%">
<tr>
   <td width="30%" align="left"><img src="<s:property value="%{orgImgPath}" />" border="0" width="150" height="50" /></td>
   <td width="70%" align="left">
      <table cellpadding="0" border="0" align="center" cellspacing="0" style="border-collapse: collapse" width="100%">
      <tr>
          <td width="60%" align="center"><font face="Arial, Helvetica, sans-serif" color="#000000" size="3"><b><u><c:out value="${orgName}" /></u></b></font></td>
      </tr>
      <tr>
          <td width="50%" align="center"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b><u><c:out value="${address}" />,&nbsp;<c:out value="${city}" /></u></b></font></td>
          <td width="50%" align="left"><font face="Arial, Helvetica, sans-serif"  color="#000000" size="1">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Lodge&nbsp;Time:</b></font>&nbsp;<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b><c:out value="${open_time}" /></b></font></td>
                  
     </tr>
      <tr>
           <td width="50%" align="center"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><u><b>Job Card&nbsp;-&nbsp;<c:out value="${ticketNo}" /></b></u></font></td>
          <td width="50%" align="left"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Lodge&nbsp;Date:</b></font>&nbsp;<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b><c:out value="${open_date}" /></b></font></td>
     </tr>
    
</table>
   </td>
</tr>
</table>
<hr />
<table border="1" width="100%" align="center">

    <tr>
		<td width="20%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;Opened By:&nbsp;</b></font>
		</td>
		<td width="30%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;<c:out value="${feedbackBy}" /></b></font>
		</td>
		<td width="20%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;Mobile No:&nbsp;</b></font>
		</td>
		<td width="30%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;<c:out value="${feedMobNo}" /></b></font>
		</td>
	</tr>

	<tr>
		<td width="20%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;To Dept:&nbsp;</b></font>
		</td>
		<td width="30%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;<c:out value="${feedBackToDept}" /></b></font>
		</td>
		<td width="20%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;Sub-Dept:&nbsp;</b></font>
		</td>
		<td width="30%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;<c:out value="${feedBackToSubDept}" /></b></font>
		</td>
	</tr>
	
	<tr>
		<td width="20%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;Alloted To:&nbsp;</b></font>
		</td>
		<td width="30%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;<c:out value="${resolvedBy}" /></b></font>
		</td>
		<td width="20%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;Location:&nbsp;</b></font>
		</td>
		<td width="30%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;<c:out value="${location}" /></b></font>
		</td>
	</tr>
	
	<tr>
		<td width="20%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;Category:&nbsp;</b></font>
		</td>
		<td width="30%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;<c:out value="${feedCat}" /></b></font>
		</td>
		<td width="20%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;Sub-Category:&nbsp;</b></font>
		</td>
		<td width="30%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;<c:out value="${subCat}" /></b></font>
		</td>
	</tr>
	<tr>
		<td width="20%" valign="top">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;Brief:&nbsp;</b></font>
		</td>
		<td width="30%" valign="top">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;<c:out value="${feedbrief}" /></b></font>
		</td>
		<td width="20%" valign="top">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;Addressing Time:&nbsp;</b></font>
		</td>
		<td width="30%" valign="top">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;<c:out value="${addDate}" /></b></font>
		</td>
	</tr>
	
    <tr>
          <td width="20%">
                      <font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;Resolved On:&nbsp;</b></font>
          </td>
          <td width="30%">
                     <font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b> <c:out value="${resDat}"/></b></font>
          </td>
          <td width="20%">
                     <font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;Resolved At:&nbsp;</b></font>
          </td>
          <td width="30%">
                    <font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b> <c:out value="${resTime}"/></b></font>
          </td>
    </tr>
  
    
	<tr>
		<td width="20%" colspan="1">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;Action Taken:&nbsp;</b></font>
		</td>
		<td width="80%" colspan="3">
                    <font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b> <c:out value="${resCmnt}"/></b></font>
        </td>
	</tr>
	
	<tr>
		<td width="20%" colspan="1">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;Resources Used:&nbsp;</b></font>
		</td>
		<td width="80%" colspan="3">
                    <font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b><c:out value="${spareused}"/></b></font>
        </td>
	</tr>
	
	<tr>
		<td width="20%" colspan="1">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;Feedback:&nbsp;</b></font>
		</td>
		<td width="80%" colspan="3">
            <font face="Arial, Helvetica, sans-serif" color="#000000" size="1"></font>
        </td>
	</tr>
</table>
<br />
<table width="100%" align="center">
	<tr>
		<td width="30%" align="center">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1">
<b>Complainant Sign</b></font>
		</td>
		<td width="30%" align="center">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1">
<b>Attendent Sign</b></font>
		</td>
		<td width="30%" align="center">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1">
<b>Manager</b></font>
		</td>
	</tr>
</table>     
      <table align="center" width="100%">
         <tr>
          <td width="100%" align="center"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1">
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------</font></td> 
        </tr>
	</table>
	</div>
	<br>
	<div class="type-button">
       		<center>
              <sj:submit 
                        clearForm="true"
                        value="  Print  " 
                        button="true"
                        onclick="printDiv('printDiv')"
                        />
              <sj:submit 
                        clearForm="true"
                        value="  Cancel  " 
                        button="true"
                        onclick="CancelPrint()"
                        />          
              </center>
              </div> 
	
</body>
</html>

