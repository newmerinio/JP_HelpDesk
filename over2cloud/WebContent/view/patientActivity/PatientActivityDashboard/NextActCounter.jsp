<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 
</head>
<body>
	<table width="100%" cellpadding="0" cellspacing="1" >
		<tbody>
			<tr>
			<td class="fbtxt f13 mB10" align="center">UHID</td>
			<td class="fbtxt f13 mB10" align="center">Patient Name</td>
			<td class="fbtxt f13 mB10" align="center">Sub Offering</td>
			<td class="fbtxt f13 mB10" align="center">Status</td>
			<td class="fbtxt f13 mB10" align="center">Account Manager</td>
			<td class="fbtxt f13 mB10" align="center">Due Date</td>
			</tr>
			<s:iterator value="nextActList" status="count" id="nextActId">
				<tr>
					<td align="center" class="bullet1"> 
						<div class=""> 	
							<s:property value="%{uhid}"/>
						</div>
					</td>
					<td align="center" class="bullet1"> 
						<div class=""> 	
							<s:property value="%{patName}"/>
						</div>
					</td>
					<td align="center" class="bullet1"> 
						<div class=""> 	
							<s:property value="%{suboffer}"/>
						</div>
					</td>
					<td align="center" class="bullet1"> 
						<div class=""> 	
							<s:property value="%{status}"/>
						</div>
					</td>
					<td align="center" class="bullet1"> 
						<div class=""> 	
							<s:property value="%{accMgr}"/>
						</div>
					</td>
					<td align="center" class="bullet1"> 
						<div class=""> 	
							<s:property value="%{maxDate}"/>
						</div>
					</td>
				</tr>
			</s:iterator>
												
		</tbody>
	</table>
</body>
</html>