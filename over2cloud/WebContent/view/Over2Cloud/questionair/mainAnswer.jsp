<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<html>
<head>
</head>
<body>

		<table width="100%" style="margin:0px;padding:0px; " id="feedbakData">
		<tr></tr>
		<tr style="background-color: grey; color: white; font-weight: bold;">
			<th style="padding:2px;margin:1px;text-align:center; width: 20%;">Sr.</th>
			<th style="padding:2px;margin:1px;text-align:center; width: 50%;">Question</th>
			<th style="padding:2px;margin:1px;text-align:center; width: 30%;">Answer</th>
		</tr>
		<!-- Answers -->
		<div id="answerDiv">

<s:iterator value="answersList" status="status" id="test">
	<tr style="  background-color: lightgray;   text-align: center;">
		   <td> 
		   		<s:property value="#status.count"/>.  
		    </td>
		   	<td>
		   					<s:property value="#test.questions"/>
		   	</td>
		   	<td>
		   	<s:iterator value="#test.optionsList" status="status" id="test3">
			<s:if test="#status.count != 1 ">
			<b>,</b>
			</s:if>
			<s:property />
			
		 </s:iterator>
		 </td>
		</tr>  
		     <s:iterator value="#test.subQuestion" id="test1" status="stat">
		    <tr style="  background-color: lightgray; text-align: center;">
		    			 <td> <s:property value="#status.count"/>. <s:property value="#stat.count"/>
		    			 </td>
		    	 			 
		    	 		<td>	 <s:property value="#test1.subquestions"/>
		    	 		</td>
				  				<td>
				  				<s:iterator value="#test1.suboptionsList" status="status" id="test4">
				  				 <s:property />
				  				<s:if test="#status.count != 1 ">
				  				 <b>,</b>
				  				 </s:if>
				  				</s:iterator>
				  				</td>
			</tr>
			</s:iterator>
		
		</s:iterator>
</div>
 	</table>
<div id="reportDiv">
<s:url action="downloadReport.action" namespace="view/Over2Cloud/patientActivity/" var="hellolink">
<s:param name="reportFileName" value="%{report}"></s:param>
</s:url>
<br><br>
<b>Report Uploaded : </b>
<s:if test="%{report == null || report== 'NA' || report == '' }">
Not Available.
</s:if>
<s:else>
<a href='${hellolink}' > <img src='images/docDownlaod.jpg' style='height: 31px;'></a>
</s:else>
 
</div>
</body>
</html>