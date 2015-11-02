<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<table align="center" border="0" width="100%" height="338px" style="border-style:dotted solid;">
                        			
               <tr height="30px">
                   <td valign="bottom">
                       <table height="5px" align="center" border="1" style="border-style:dotted solid;" width="100%">
                        	  <tr>
                        			<th align="center" class="headings1" >Patient&nbsp;Name</th>
                        			<th align="center" class="headings1" >Mob&nbsp;No</th>
                        			<th align="center" class="headings1" >Doctor&nbsp;Name</th>
                        	  </tr>
                       </table>
                    </td>
               </tr>
               
               <tr>
                   <td valign="top" style="padding-top: 10px" align="center">
                   <marquee onmouseover="this.stop()" onmouseout="this.start()" scrollamount="1" scrolldelay="2" direction="up" height="290px">
					   <table align="center" border="0" width="100%" style="border-style:dotted solid;" height="100%" >                        		
                              <s:iterator id="fList"  status="status" value="%{feedList}" >
								<tr bordercolor="#ffffff">
                        			<td align="center" ><b><s:property value="%{name}"/></b></td>
                        			<td align="center"><b><s:property value="%{mobNo}"/></b></td>
                        			<td align="center"><b><s:property value="%{doctName}"/></b></td>
                        		</tr>
                        	   </s:iterator>
                        </table>
                   </marquee>
                   </td>
               </tr>
              </table>
</body>
</html>