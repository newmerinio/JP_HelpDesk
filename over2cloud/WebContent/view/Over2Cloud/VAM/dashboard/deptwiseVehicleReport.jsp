<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
                                                                   pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>
<%@page import="com.Over2Cloud.CommonClasses.DateUtil"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<s:url var="chartDataUrl" action="json-chart-data"/>
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
    <script type="text/javascript" src="js/jquery.min.js"></script>
    <script type="text/javascript" src="js/bootstrap.js"></script>
<title>Insert title here</title>
</head>
<body>
<div class="middle-content">

<!-- Departmentwise start here -->
<div style="height:auto; margin-bottom:10px;" ><B>Departmentwise Vehicle Status</B></div>
<table align="center" border="0" width="100%" style="border-style:dotted solid;">
                        			
               <tr >
                   <td valign="bottom">
                       <table  align="center" border="1" style="border-style:dotted solid;" width="100%">
                        	  <tr bgcolor="#780000">
                        			<td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Department</b></font></td>
	                                <td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Vehicle In</b></font></td>
	                                <td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Vehicle Out</b></font></td>
	                                <td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Pending</b></font></td>
                        	  </tr>
                       </table>
                    </td>
               </tr>
               
               <tr>
                   <td valign="top" style="padding-top: 5px" align="center">
                 
					   <table align="center" border="0" width="100%" style="border-style:dotted solid;" >                        		
                             <s:iterator id="listOlists"  value="listOlists" var = "childlist"> 
								<tr bordercolor="#ffffff">
                        		<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b><s:property value="%{#childlist[0]}"/> </b></a></font></td>
                                <td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b><s:property value="%{#childlist[1]}"/> </b></a></font></td>
                                <td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b><s:property value="%{#childlist[2]}"/> </b></a></font></td>
                                <td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b><s:property value="%{#childlist[3]}"/> </b></a></font></td>
                                </tr>
                        	   </s:iterator>
                        </table>
                   
                   </td>
               </tr>
</table>
              
</div>
<!-- Departmentwise end here -->
</body>
<script type="text/javascript">
</script>
</html>