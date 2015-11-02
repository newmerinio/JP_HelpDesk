<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<html>
<head>
<script type="text/javascript">
function printDiv(divName) {
    var printContents = document.getElementById(divName).innerHTML;
    $("#patientFeedbackDataView1").dialog('close');
    var myWindow = window.open("","myWindow","width=600,height=400"); 
    myWindow.document.write(printContents);
    myWindow.moveTo(300,200); 
    myWindow.print();
    myWindow.close();  
//auto reset wnen print or close    
 	closeDiv();			
//End auto reset			  
}

</script>
</head>
<body>
<sj:a button="true" cssStyle="margin-left: 754px;width: 82px;height: 25px;" onclick="printDiv('patient')">Print</sj:a>&nbsp&nbsp
<div id="patient">
<s:if test="%{viewAnswerList.size > 0 && viewAnswerList != null }">
<s:iterator value="viewAnswerList" id="show" var="show">
<table width="97%" style="margin:0px;padding:0px; " id="feedbakData">
		<tr>
		<th colspan="3"><b><s:property value="#show.sections"/></b></th>
		</tr>
		<tr>
		<td colspan="3"><b> Submitted on :</b><s:property value="#show.date"/>&nbsp;<s:property value="#show.time"/></td>
		</tr>
		<tr style="background-color: grey; color: white; font-weight: bold;">
			<th style="padding:2px;margin:1px;text-align:center; width: 20%;">Sr No.</th>
			<th style="padding:2px;margin:1px;text-align:left; width: 50%;">Question</th>
			<th style="padding:2px;margin:1px;text-align:left; width: 30%;">Answer</th>
		</tr>
		<!-- Answers -->
		<div id="answerDiv">

<s:iterator value="#show.answerlist" status="status" id="test">
	<tr style="  background-color: lightgray;   text-align: center;">
		   
		    <s:if test="#test.questions!='Relation' && #test.questions!='Duration' && #test.questions!='Mention Value if any' ">
		   <td style="margin:1px;text-align:center;"> 
		   		<s:property value="#test.sno"/>.  
		    </td>
		    <td style="margin:1px;text-align:left;">
		   					<s:property value="#test.questions"/>
		   	</td>
		     <s:if test="#test.subanswers!=null && #test.answers!=null && #test.answers!='No' && #test.answers!='Do not Know'">
		   <td style="margin:1px;text-align:left;">
		 <s:property value="#test.answers"/>:&nbsp;
		 <s:property value="#test.subanswers"/>
		 </td>
		    </s:if>
		    <s:else>
		     <td style="margin:1px;text-align:left;">
		   	<s:property value="#test.answers"/>
		   </td>
		    </s:else>
		    
		     
		    </s:if>
		   	<%-- <s:else>
		    <s:if test="#test.questions=='Relation'">
		     <s:if test="#test.answers=='Yes'">
		    <!--<td style="margin:1px;text-align:left;">
		   	<s:iterator value="#test.optionsList" status="status" id="test3">
			<s:if test="#status.count != 1 ">
			<b>,</b>
			</s:if>
			<s:property />
			
		 </s:iterator>
		 </td>
		 
		 -->
		 <td style="margin:1px;text-align:left;">
		 <s:property value="#test.answers"/>:&nbsp;
		 <s:property value="#test.subanswers"/>
		 </td>
		 </s:if>
		 </s:if>
		 </s:else> 
 --%>		
		</tr> 
		     <!-- <s:iterator value="#test.subquestion" id="test1" status="stat">
		    <tr style="  background-color: lightgray; text-align: left;">
		    			 <td> <s:property value="#status.count"/>. <s:property value="#stat.count"/>
		    			 </td>
		    	 			 
		    	 		<td>	 <s:property value="#test1.subquestions"/>
		    	 		</td>
				  				<td>
				  				<s:iterator value="#test1.suboptionslist" status="status" id="test4">
				  				 <s:property />
				  				<s:if test="#status.count != 1 ">
				  				 <b>,</b>
				  				 </s:if>
				  				</s:iterator>
				  				</td>
			</tr>
			</s:iterator> -->
		
		</s:iterator>
</div>
 </table>
<!--<div id="reportDiv">
<s:url action="downloadReport.action" namespace="view/Over2Cloud/patientActivity/" var="downloadlink">
<s:param name="reportFileName" value="#show.report"></s:param>
</s:url>
<b>Report Uploaded : </b>
<s:if test="%{#show.report == null || #show.report == 'NA' || #show.report == '' }">
Not Available.
</s:if>
<s:else>
<a href='${downloadlink}' > <img src='images/docDownlaod.jpg' style='height: 31px;'></a>
</s:else>
 
</div>
--><br><br><br> 
 <div class="clear"></div>
</s:iterator>
</s:if>
<s:else>
<center>
<img src='images/noDocsFound.jpg' style="  margin-top: 110px;">
</center>
</s:else>
</div>
</body>
</html>