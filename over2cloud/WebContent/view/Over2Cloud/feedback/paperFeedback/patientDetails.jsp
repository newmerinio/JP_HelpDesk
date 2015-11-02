<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="s"  uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<SCRIPT type="text/javascript" src="js/feedback/feedbackForm.js"></SCRIPT>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body> <br/>
<s:if test="%{visitType=='Patient'}">
<table width="100%" align="center" border="0">
									<tr bgcolor="#E6E6E6">
											<td align="left" ><b>Patient&nbsp;Name:</b></td><td align="center" ><s:hidden id="clientName" name="client_name" value="%{patObj.getPatientName()}" /><s:property value="%{patObj.getPatientName()}" /></td>
											<td align="left" ><b>Patient&nbsp;Type:</b></td><td align="center" ><s:property value="%{patObj.getPatType()}" /><s:hidden id="compType" name="comp_type" value="%{patObj.getPatType()}" /></td>
											<td align="left" ><b>MRD&nbsp;No:</b></td><td align="center" ><s:property value="%{patObj.getPatientId()}" /></td>
											<td align="left" ><b>Record&nbsp;ID:</b></td><td align="center" ><s:textfield theme="simple" id="patientId" name="patient_id" value="%{patObj.getRecordId()}" cssClass="textField"/></td>
                                    </tr>
                                    <tr>
                                    	<td align="left" ><b>Room&nbsp;No:</b></td><td align="center" ><s:textfield theme="simple" id="centerCode" name="center_code" value="%{patObj.getRoomNo()}" cssClass="textField" /></td>
                                    	<td align="left" ><b>Mobile&nbsp;No:</b></td><td align="center" ><s:property value="%{patObj.getPatientMobileNo()}" /><s:hidden id="mobNo" name="mob_no" value="%{patObj.getPatientMobileNo()}" /></td>
											<td align="left" ><b>Email&nbsp;ID:</b></td><td align="center" ><s:property value="%{patObj.getPatientEmailId()}" /><s:hidden id="emailId" name="email_id" value="%{patObj.getPatientEmailId()}" /></td>
											<%-- <td align="left" ><b>Company&nbsp;Type:</b></td><td align="center" ><s:property value="%{patObj.getCompanytype()}" /><s:hidden id="companytype" name="companytype" value="%{patObj.getCompanytype()}" /></td> --%>
											<s:if test="%{patObj.getPatType()=='IPD'}">
												<td align="left" ><b>Company&nbsp;Type:</b></td><td align="center" ><s:property value="%{patObj.getCompanytype()}" /><s:hidden id="companytype" name="company_type" value="%{patObj.getCompanytype()}" /></td>
                                  			</s:if>
                                  			<s:else>
                                  				<td align="left" ><b>Company&nbsp;Type:</b></td><td align="center" ><s:textfield theme="simple"  id="companytype" name="company_type" value="%{patObj.getCompanytype()}" cssClass="textField"/></td>
                                  			</s:else>
                                    </tr>
                                 
                                     <tr bgcolor="#E6E6E6">
                                    <s:if test="%{patObj.getPatType()=='IPD'}">
                                    	<td align="left" ><b>Speciality:</b></td><td align="center" ><s:property value="%{patObj.getVisit_type()}" /> <s:hidden id="centreName" name="centre_name" value="%{patObj.getVisit_type()}" /></td>
											<td align="left" ><b>Treating&nbsp;Doctor:</b></td><td align="center" ><s:property value="%{patObj.getDoctorName()}" /><s:hidden id="serviceBy" name="service_by" value="%{patObj.getDoctorName()}" /></td>
											<td align="left" ><b>Discharged&nbsp;On:</b></td><td align="center" ><sj:datepicker id="discharge_datetime" name="discharge_datetime" readonly="true" placeholder="Enter Date"   showOn="focus" displayFormat="yy-mm-dd" value="%{patObj.getDischarge_datetime()}" timepicker="true" cssClass="textField"  cssStyle="margin:0px0px 10px 0px;"/></td>
											<td><s:hidden id="admission_time" name="admission_time" value="%{patObj.getAdmission_time()}" /></td>
											<td></td><td></td>
									</s:if>
									<s:else>
										<td align="left" ><b>Speciality:</b></td><td align="center" > <s:textfield theme="simple"  id="centreName" name="centre_name" value="%{patObj.getVisit_type()}" cssClass="textField"/></td>
											<td align="left" ><b>Treating&nbsp;Doctor:</b></td><td align="center" ><s:textfield theme="simple"  id="serviceBy" name="service_by" value="%{patObj.getDoctorName()}" cssClass="textField"/></td>
											<td align="left" ><b>Visit&nbsp;Date:</b></td><td align="center" ><sj:datepicker id="admission_time" name="admission_time" readonly="true" placeholder="Enter Date"   showOn="focus" displayFormat="yy-mm-dd" value="%{patObj.getAdmission_time()}" timepicker="true" cssClass="textField"  cssStyle="margin:0px0px 10px 0px;"/></td> 
											<td><s:hidden id="discharge_datetime" name="discharge_datetime" value="%{patObj.getDischarge_datetime()}" /></td>
											<td></td><td></td>
									</s:else>		
                                    </tr>
</table>
</s:if>
<s:else>
	<span class="pIds" style="display: none; ">clientName#Visitor Name#T#sc,</span>
	
	<table width="100%" align="center" border="0">
		<tr bgcolor="#E6E6E6">
			<td align="left" ><b>Visitor&nbsp;Name:</b><font size="+2" color="#FF1111">|</font></td><td align="center" ><s:textfield theme="simple"  id="clientName" name="client_name"  cssClass="textField"/></td>
			<td align="left" ><b>Mobile&nbsp;No:</b><td align="center" ><s:textfield theme="simple"  id="mobNo" name="mob_no"  cssClass="textField"/></td>
			<td align="left" ><b>Email&nbsp;ID:</b></td><td align="center" ><s:textfield theme="simple"  id="emailId" name="email_id"  cssClass="textField"/></td> 
			<td></td><td></td>
		</tr>
	</table>	
</s:else>
<br/>
<s:hidden id="formcheck" value="checkedform"/>
<s:if test="%{patObj.getPatType()=='IPD'}">
<s:include value="ipdForm.jsp" />
</s:if>
<s:elseif test="%{patObj.getPatType()=='OPD'}">
<s:include value="opdForm.jsp" />
</s:elseif>

</body>
</html>