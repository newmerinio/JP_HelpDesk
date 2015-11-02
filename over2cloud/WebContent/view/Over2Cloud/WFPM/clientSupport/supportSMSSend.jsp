<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib prefix="s"  uri="/struts-tags"%>
    <%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
	<%@ taglib prefix="sjr" uri="/struts-jquery-richtext-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="clear"></div>
		<div class="middle-content">
		<div class="list-icon">
			<div class="head">SMS</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:60%; float: left;"></div><div class="head">Send</div>
		</div>
	<div class="clear"></div>
	
	<div class="border">
	
		<div style="width: 100%; text-align: center; padding-top: 10px;">
		<table border="1" width="80%" style="border-collapse: collapse;" align="center">
				<tr bgcolor="#D8D8D8" style="height: 25px">
							<td width="10%"><b>Client Name:</b> </td>
							<td width="10%"><s:property  value="%{clientname}"/></td>
							<td width="10%"><b>Offering Name:</b></td>
							<td width="10%"><s:property  value="%{subofferingname}"/></td>
				</tr>
				<tr style="height: 23px">
							<td width="10%"><b>Contact Name:</b></td>
							<td width="10%"><s:property  value="%{contact_person}"/></td>
							<td width="10%"><b>Communication Name:</b></td>
							<td width="10%"><s:property  value="%{communicationName}"/></td>
				</tr>
				
				
		</table>	
		<s:form id="formRichtext" namespace="/view/Over2Cloud/wfpm/clientsupport" action="sendSMSAction" theme="simple" cssClass="yform">
		<s:hidden name="communicationName" value="%{communicationName}" id="communicationName"></s:hidden>
		<s:hidden name="clientname" value="%{clientname}"></s:hidden>
		<div style="margin-right: 18%;">
			<div class="newColumn">
	         <div class="leftColumn">To: </div>
	            <div class="rightColumn">
	            <s:textfield  name="mobile_number" id="mobile_number" value="%{contactNumber}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;" /> 
		        </div>
		 	 </div> 
	        <div class="newColumn">
			<div class="leftColumn">CC:</div>
			<div class="rightColumn">
			<s:select 
			            id="name"
			            name="name" 
			            list="employee_basicMap"
			            headerKey="-1"
			            headerValue="Select" 
			            cssClass="select"
		                cssStyle="width:82%"
			            >
			</s:select>
			</div>
			</div>
			<div class="clear"></div>
	         	<div class="newColumn">
	        	 <div class="leftColumn">SMS:</div>
	        	 <div class="rightColumn">
	        	 	<s:textarea name="msg_txt" id="msg_txt" cols="32" rows="2" cssStyle="margin:0px 0px 10px 0px" /></div>
	        	 </div>
	        	 </div>
	       <div class="clear"></div>
	       <div class="type-button">
				<sj:submit
					id="richtextSubmitButton"
					formIds="formRichtext" 
					targets="result" 
					clearForm="true"
					listenTopics="saveRichtext"
					value="Send" 
					indicator="indicator" 
					button="true"
					onBeforeTopics="validateMe"
					 onCompleteTopics="level1"
				/>
				<img id="indicator" 
					src="images/indicator.gif" 
					alt="Loading..." 
					style="display:none"/>
					<sj:a 
	     	    name="Reset"  
				href="#" 
				cssClass="submit" 
				indicator="indicator" 
				button="true" 
				onclick="resetForm('formRichtext');"
				>
	  			Reset
			</sj:a>
                  <sj:submit 
                  value="Back" 
                  effect="highlight"
                  effectOptions="{ color : '#222222'}"
                  effectDuration="5000"
                  button="true"
                  cssClass="submit"
                 onclick="backSupportView();"
                  />
	        </div>
     	 <br><br>
	  		 <sj:div id="submitmailform"  effect="fold">
	           <div id="result"></div>
	        </sj:div>
	        
	        </s:form>
	         
	  </div>
	  </div>
	  </div>
</body>
</html>