    <%@ taglib prefix="s"  uri="/struts-tags"%>
    <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<table width="100%" align="center" border="0">
	<tr >
											<td align="left" >Patient&nbsp;Name</td><td align="center" ><s:property value="%{patObj.getPatientName()}" /></td>
											<td align="left" >Patient&nbsp;Type</td><td align="center" ><s:property value="%{patObj.getPatType()}" /></td>
											<td align="left" >MRD&nbsp;No</td><td align="center" ><s:property value="%{patObj.getPatientId()}" /></td>
											<td align="left" >Record&nbsp;Id</td><td align="center" ><s:property value="%{patObj.getRecordId()}" /></td>
                                    </tr>
                                    <tr>
                                    	<td align="left" >Room&nbsp;No</td><td align="center" ><s:property value="%{patObj.getRoomNo()}" /></td>
                                    	<td align="left" >Mob&nbsp;No</td><td align="center" ><s:property value="%{patObj.getPatientMobileNo()}" /></td>
											<td align="left" >Email&nbsp;Id</td><td align="center" ><s:property value="%{patObj.getPatientEmailId()}" /></td>
											<td align="left" >Company&nbsp;Type</td><td align="center" ><s:property value="%{patObj.getCompanytype()}" /></td>
                                    </tr>
                                    <tr>
                                    	<td align="left" >Speciality</td><td align="center" ><s:property value="%{patObj.getVisit_type()}" /></td>
											<td align="left" >Treating&nbsp;Doctor</td><td align="center" ><s:property value="%{patObj.getDoctorName()}" /></td>
											<td align="left" >Discharged On</td><td align="center" ><s:property value="%{patObj.getDischarge_datetime()}" /></td>
											<td></td>
                                    </tr>
</table>
</body>
</html>