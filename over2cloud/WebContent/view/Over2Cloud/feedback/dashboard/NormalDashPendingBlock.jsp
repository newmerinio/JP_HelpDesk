<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<table align="center" border="0" width="100%" height="30%">
          <tr bordercolor="black">
              <th valign="top" bgcolor="" >
                  <table align="center" border="0" width="100%" >
                   	  <tr style="font-weight: normal;border-radius:3px;background: #e6e6e6 url(template/theme/mytheme/images/ui-bg_glass_75_e6e6e6_1x400.png) 50% 50% repeat-x;">
                   	 		<th align="center" class="headings1" width="10%"><font color="black">CR&nbsp;No</font></th>
                   			<th align="center" class="headings1" width="10%"><font color="black">Ticket&nbsp;No</font></th>
                   			<th align="center" class="headings1" width="10%"><font color="black">Patient&nbsp;Name</font></th>
                   			<th align="center" class="headings1" width="10%"><font color="black">Mob&nbsp;No</font></th>
                   			<th align="center" class="headings1" width="10%"><font color="black">Room&nbsp;No</font></th>
                   			<th align="center" class="headings1" width="10%"><font color="black">Date</font></th>
                   			<th align="center" class="headings1" width="10%"><font color="black">Status</font></th>
                   			<th align="center" class="headings1" width="10%"><font color="black">Category</font></th>
                   			<th align="center" class="headings1" width="40%"><font color="black">Feedback Brief</font></th>
                   	  </tr>
                  </table>
               </th>
          </tr>
          <tr>
              <td valign="top" height="10%">
              <marquee onmouseover="this.stop()" onmouseout="this.start()" scrollamount="2" scrolldelay="0" direction="up" height="200px">
  				 <table align="center" border="0" width="100%" style="border-style:dotted solid;">                        		
                         <s:iterator id="fList"  status="status" value="%{feedTicketList}" >
						 <tr bordercolor="#ffffff">
						    <td align="center" width="10%"><b><s:property value="%{cr_No}"/></b></td>
                   			<td align="center" width="10%" style="cursor: pointer;text-decoration: none;" onclick="takeAction('<s:property value="%{id}"/>','<s:property value="%{ticketNo}"/>','<s:property value="%{status}"/>','<s:property value="%{feedBy}"/>','<s:property value="%{feedBrief}"/>','<s:property value="%{categoryName}"/>','<s:property value="%{feedDate}"/>','<s:property value="%{allotTo}"/>');"><b><s:property value="%{ticketNo}"/></b></td>
                   			<td align="center" width="10%"><b><s:property value="%{feedBy}"/></b></td>
                   			<td align="center" width="10%"><b><s:property value="%{feedByMob}"/></b></td>
                   			<td align="center" width="10%"><b><s:property value="%{location}"/></b></td>
                   			<td align="center" width="10%"><b><s:property value="%{feedDate}"/></b></td>
                   			<td align="center" width="10%"><b><s:property value="%{status}"/></b></td>
                   			<td align="center" width="10%"><b><s:property value="%{categoryName}"/></b></td>
                   			<td align="center" width="40%"><b><s:property value="%{feedBrief}"/></b></td>
                   		</tr>
                   	   </s:iterator>
                   </table>
              </marquee>
              </td>
          </tr>
   </table>
</body>
</html>