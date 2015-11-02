<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<s:url id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<div class="middle-content">
<div class="contentdiv-small" style="y-overflow: scroll; x-overflow:hidden  ;width:95%;height:271px;margin-top: 10px">
<div style="float:center; width:auto; padding:0px 4px 0 0;">
		<center><font color="#000000"><b>Operational&nbsp;Excellence</b></font></center>
</div>
	<table align="center" border="0" width="100%" height="30%">
          <tr bordercolor="black">
              <th valign="top" bgcolor="#668F1F" >
                  <table align="center" border="0" width="100%" >
                   	  <tr>
                   	 		 <th align="center" class="headings1" width="5%"><font color="white">From&nbsp;OG</font></th>
                   			 <th align="center" class="headings1" width="5%"><font color="white">From&nbsp;Plant</font></th>
                   			 <th align="center" class="headings1" width="5%"><font color="white">Agenda</font></th>
                   			 <th align="center" class="headings1" width="5%"><font color="white">Title</font></th>
                   			 <th align="center" class="headings1" width="5%"><font color="white">Brief</font></th>
                   			 <th align="center" class="headings1" width="5%"><font color="white">Implemented&nbsp;In</font></th>
                   			 <th align="center" class="headings1" width="5%"><font color="white">On</font></th>
                   			 <th align="center" class="headings1" width="5%"><font color="white">At</font></th>
                   			 <th align="center" class="headings1" width="5%"><font color="white">Venue</font></th>
                   			 <th align="center" class="headings1" width="5%"><font color="white">Frequency</font></th>
                   			 <th align="center" class="headings1" width="5%"><font color="white">Date</font></th>
                   			 <th align="center" class="headings1" width="5%"><font color="white">To&nbsp;OG</font></th>
                   			 <th align="center" class="headings1" width="5%"><font color="white">To&nbsp;Plant</font></th>
                   			 <th align="center" class="headings1" width="5%"><font color="white">Uploaded&nbsp;By</font></th>
                   			 <th align="center" class="headings1" width="5%"><font color="white">Uploaded&nbsp;On</font></th>
                   			 <th align="center" class="headings1" width="5%"><font color="white">Status</font></th>
                   			 
                   	  </tr>
                  </table>
               </th>
          </tr>
          <tr>
              <td valign="top" height="10%">
              <marquee onmouseover="this.stop()" onmouseout="this.start()" scrollamount="1" scrolldelay="2" direction="up" >
  				 <table align="center" border="0" width="100%" style="border-style:dotted solid;">                        		
                         <s:iterator id="fList"  status="status" value="%{operationalList}" >
						 <tr bordercolor="#ffffff">
						     <td align="center" width="5%"><b><s:property value="%{fromOG}"/></b></td>
                   			<td align="center" width="5%"><b><s:property value="%{fromPlant}"/></b></td>
                   			<td align="center" width="5%"><b><s:property value="%{agenda}"/></b></td>
                   			<td align="center" width="5%"><b><s:property value="%{title}"/></b></td>
                   			<td align="center" width="5%"><b><s:property value="%{brief}"/></b></td>
                   			<td align="center" width="5%"><b><s:property value="%{implementedIn}"/></b></td>
                   			<td align="center" width="5%"><b><s:property value="%{onDate}"/></b></td>
                   			<td align="center" width="5%"><b><s:property value="%{atTime}"/></b></td>
                   			<td align="center" width="5%"><b><s:property value="%{venue}"/></b></td>
                   			<td align="center" width="5%"><b><s:property value="%{frequency}"/></b></td>
                   			<td align="center" width="5%"><b><s:property value="%{dueDate}"/></b></td>
                   			<td align="center" width="5%"><b><s:property value="%{toOG}"/></b></td>
                   			<td align="center" width="5%"><b><s:property value="%{toPlant}"/></b></td>
                   			<td align="center" width="5%"><b><s:property value="%{uploadedBy}"/></b></td>
                   		    <td align="center" width="5%"><b><s:property value="%{uploadedOn}"/></b></td>
                   		    <td align="center" width="5%"><b><s:property value="%{status}"/></b></td>
                   		
                   		</tr>
                   	   </s:iterator>
                   </table>
              </marquee>
              </td>
          </tr>
   </table>
</div>
<div class="contentdiv-small" style="y-overflow: scroll; x-overflow:hidden  ;width:95%;height:271px;margin-top: 10px">
<div style="float:center; width:auto; padding:0px 4px 0 0;">
		<center><font color="#000000"><b>CMD&nbsp;Communication</b></font></center>
</div>
	<table align="center" border="0" width="100%" height="30%">
          <tr bordercolor="black">
              <th valign="top" bgcolor="#4b0082" >
                  <table align="center" border="0" width="100%" >
                   	  <tr>
                   	 		 <th align="center" class="headings1" width="5%"><font color="white">From&nbsp;OG</font></th>
                   			 <th align="center" class="headings1" width="5%"><font color="white">From&nbsp;Plant</font></th>
                   			 <th align="center" class="headings1" width="5%"><font color="white">Agenda</font></th>
                   			 <th align="center" class="headings1" width="5%"><font color="white">Title</font></th>
                   			 <th align="center" class="headings1" width="5%"><font color="white">Brief</font></th>
                   			 <th align="center" class="headings1" width="5%"><font color="white">Implemented&nbsp;In</font></th>
                   			 <th align="center" class="headings1" width="5%"><font color="white">On</font></th>
                   			 <th align="center" class="headings1" width="5%"><font color="white">At</font></th>
                   			 <th align="center" class="headings1" width="5%"><font color="white">Venue</font></th>
                   			 <th align="center" class="headings1" width="5%"><font color="white">Frequency</font></th>
                   			 <th align="center" class="headings1" width="5%"><font color="white">Date</font></th>
                   			 <th align="center" class="headings1" width="5%"><font color="white">To&nbsp;OG</font></th>
                   			 <th align="center" class="headings1" width="5%"><font color="white">To&nbsp;Plant</font></th>
                   			 <th align="center" class="headings1" width="5%"><font color="white">Uploaded&nbsp;By</font></th>
                   			 <th align="center" class="headings1" width="5%"><font color="white">Uploaded&nbsp;On</font></th>
                   			 <th align="center" class="headings1" width="5%"><font color="white">Status</font></th>
                   			 
                   	  </tr>
                  </table>
               </th>
          </tr>
          <tr>
              <td valign="top" height="10%">
              <marquee onmouseover="this.stop()" onmouseout="this.start()" scrollamount="1" scrolldelay="2" direction="up" >
  				 <table align="center" border="0" width="100%" style="border-style:dotted solid;">                        		
                         <s:iterator id="fList"  status="status" value="%{cmdCommList}" >
						 <tr bordercolor="#ffffff">
						 <td align="center" width="5%"><b><s:property value="%{fromOG}"/></b></td>
                   			<td align="center" width="5%"><b><s:property value="%{fromPlant}"/></b></td>
                   			<td align="center" width="5%"><b><s:property value="%{agenda}"/></b></td>
                   			<td align="center" width="5%"><b><s:property value="%{title}"/></b></td>
                   			<td align="center" width="5%"><b><s:property value="%{brief}"/></b></td>
                   			<td align="center" width="5%"><b><s:property value="%{implementedIn}"/></b></td>
                   			<td align="center" width="5%"><b><s:property value="%{onDate}"/></b></td>
                   			<td align="center" width="5%"><b><s:property value="%{atTime}"/></b></td>
                   			<td align="center" width="5%"><b><s:property value="%{venue}"/></b></td>
                   			<td align="center" width="5%"><b><s:property value="%{frequency}"/></b></td>
                   			<td align="center" width="5%"><b><s:property value="%{dueDate}"/></b></td>
                   			<td align="center" width="5%"><b><s:property value="%{toOG}"/></b></td>
                   			<td align="center" width="5%"><b><s:property value="%{toPlant}"/></b></td>
                   			<td align="center" width="5%"><b><s:property value="%{uploadedBy}"/></b></td>
                   		    <td align="center" width="5%"><b><s:property value="%{uploadedOn}"/></b></td>
                   		    <td align="center" width="5%"><b><s:property value="%{status}"/></b></td>
                   		
                   		</tr>
                   	   </s:iterator>
                   </table>
              </marquee>
              </td>
          </tr>
   </table>
</div>
<div class="contentdiv-small" style="y-overflow: scroll; x-overflow:hidden  ;width:95%;height:271px;margin-top: 10px">
<div style="float:center; width:auto; padding:0px 4px 0 0;">
		<center><font color="#000000"><b>BSC&nbsp;Communication</b></font></center>
</div>
	<table align="center" border="0" width="100%" height="30%">
          <tr bordercolor="black">
              <th valign="top" bgcolor="#00B3B3" >
                  <table align="center" border="0" width="100%" >
                   	  <tr>
                   	 		 <th align="center" class="headings1" width="5%"><font color="white">From&nbsp;OG</font></th>
                   			 <th align="center" class="headings1" width="5%"><font color="white">From&nbsp;Plant</font></th>
                   			 <th align="center" class="headings1" width="5%"><font color="white">Agenda</font></th>
                   			 <th align="center" class="headings1" width="5%"><font color="white">Title</font></th>
                   			 <th align="center" class="headings1" width="5%"><font color="white">Brief</font></th>
                   			 <th align="center" class="headings1" width="5%"><font color="white">Implemented&nbsp;In</font></th>
                   			 <th align="center" class="headings1" width="5%"><font color="white">On</font></th>
                   			 <th align="center" class="headings1" width="5%"><font color="white">At</font></th>
                   			 <th align="center" class="headings1" width="5%"><font color="white">Venue</font></th>
                   			 <th align="center" class="headings1" width="5%"><font color="white">Frequency</font></th>
                   			 <th align="center" class="headings1" width="5%"><font color="white">Date</font></th>
                   			 <th align="center" class="headings1" width="5%"><font color="white">To&nbsp;OG</font></th>
                   			 <th align="center" class="headings1" width="5%"><font color="white">To&nbsp;Plant</font></th>
                   			 <th align="center" class="headings1" width="5%"><font color="white">Uploaded&nbsp;By</font></th>
                   			 <th align="center" class="headings1" width="5%"><font color="white">Uploaded&nbsp;On</font></th>
                   			 <th align="center" class="headings1" width="5%"><font color="white">Status</font></th>
                   			 
                   	  </tr>
                  </table>
               </th>
          </tr>
          <tr>
              <td valign="top" height="10%">
              <marquee onmouseover="this.stop()" onmouseout="this.start()" scrollamount="1" scrolldelay="2" direction="up" >
  				 <table align="center" border="0" width="100%" style="border-style:dotted solid;">                        		
                         <s:iterator id="fList"  status="status" value="%{bscCommList}" >
						 <tr bordercolor="#ffffff">
						  <td align="center" width="5%"><b><s:property value="%{fromOG}"/></b></td>
                   			<td align="center" width="5%"><b><s:property value="%{fromPlant}"/></b></td>
                   			<td align="center" width="5%"><b><s:property value="%{agenda}"/></b></td>
                   			<td align="center" width="5%"><b><s:property value="%{title}"/></b></td>
                   			<td align="center" width="5%"><b><s:property value="%{brief}"/></b></td>
                   			<td align="center" width="5%"><b><s:property value="%{implementedIn}"/></b></td>
                   			<td align="center" width="5%"><b><s:property value="%{onDate}"/></b></td>
                   			<td align="center" width="5%"><b><s:property value="%{atTime}"/></b></td>
                   			<td align="center" width="5%"><b><s:property value="%{venue}"/></b></td>
                   			<td align="center" width="5%"><b><s:property value="%{frequency}"/></b></td>
                   			<td align="center" width="5%"><b><s:property value="%{dueDate}"/></b></td>
                   			<td align="center" width="5%"><b><s:property value="%{toOG}"/></b></td>
                   			<td align="center" width="5%"><b><s:property value="%{toPlant}"/></b></td>
                   			<td align="center" width="5%"><b><s:property value="%{uploadedBy}"/></b></td>
                   		    <td align="center" width="5%"><b><s:property value="%{uploadedOn}"/></b></td>
                   		    <td align="center" width="5%"><b><s:property value="%{status}"/></b></td>
                   		
                   		</tr>
                   	   </s:iterator>
                   </table>
              </marquee>
              </td>
          </tr>
   </table>
</div>
</div>
</body>
</html>