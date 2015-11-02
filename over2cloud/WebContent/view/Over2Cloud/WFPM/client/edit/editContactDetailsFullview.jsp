<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="s" uri="/struts-tags" %>
	    <%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
	    <%@ taglib prefix="sjg"  uri="/struts-jquery-grid-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Insert title here</title>
	<script type="text/javascript">
	$.subscribe('level1', function(event,data)
        {
          setTimeout(function(){ $("#contacteditID").fadeIn(); }, 10);
          setTimeout(function(){ $("#contacteditID").fadeOut();cancelButton(); }, 6000);
          
	});
	function cancelButton()
	{
	$('#completionResult').dialog('close');
	$('#clientContactDialog').dialog('close');
	}
	
	$.subscribe('validate', function(event,data)
	{
	 $("#completionResult").dialog('open');
	});
	</script>
</head>
<body>
<sj:dialog
          id="completionResult"
          showEffect="slide" 
          hideEffect="explode" 
          openTopics="openEffectDialog"
          closeTopics="closeEffectDialog"
          autoOpen="false"
          title="Confirmation Message"
          modal="true"
          draggable="true"
          resizable="true"
          buttons="{ 
                    'Close':function() { cancelButton(); } 
                                                            }" 
          >
          <sj:div id="contacteditID"  effect="fold"><div id="contactresultId"></div></sj:div>
</sj:dialog>
<s:form id="formtwo" name="formtwo" namespace="/view/Over2Cloud/wfpm/client" action="editClientContact" theme="simple" method="post" 
	enctype="multipart/form-data">
		
<s:iterator value="clientContactEditList" var="varClientContactEditList">
	<div class="menubox">
	<!-- textfield -->		
	<s:iterator value="%{#varClientContactEditList}" status="counter">
		<s:if test='%{colType == "T" && colomnName == "id"}'>
			<s:hidden name="%{colomnName}" value="%{value}" />
		</s:if>
		<s:elseif test='%{colType == "T" && colomnName != "id" && colomnName != "input" && colomnName != "contactName" && colomnName != "location" && colomnName != "potential"}'>
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{headingName}"/>:</div>
			<div class="rightColumn1"><s:if test="%{mandatroy == true}"><span class="needed"></span></s:if>
			     <s:textfield name="%{colomnName}"  id="%{colomnName}" value="%{value}"  cssClass="textField" 
			     			placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
			</div>
			</div>
		</s:elseif>
			
   </s:iterator>
	
	<!-- dropdown -->
	<s:iterator value="%{#varClientContactEditList}" status="counter">
	<s:if test='%{colType == "D"}'>
		<s:if test="%{colomnName == 'department'}">
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{headingName}"/>:</div>
			<div class="rightColumn1"><s:if test="%{mandatroy == true}"><span class="needed"></span></s:if>
			     <s:select 
						id="%{colomnName}" 
						name="%{colomnName}"
						list="deptMap"
						headerKey="-1" 
						headerValue="Select Source"
						value="%{value}"
						cssClass="select"
                        cssStyle="width:82%"
						>
					</s:select>
			</div>
			</div>	
		</s:if>
		<s:elseif test="%{colomnName == 'degreeOfInfluence'}">
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{headingName}"/>:</div>
			<div class="rightColumn1"><s:if test="%{mandatroy == true}"><span class="needed"></span></s:if>
			     <s:select
							id="%{colomnName}" 
							name="%{colomnName}" 
							list="#{'-1':'Select Rating'}" 
							list="#{'1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            headerKey="-1"
                            headerValue="Influence" 
                            cssClass="select"
                            cssStyle="width:82%"
                            >
				</s:select>
			</div>
			</div>	
		</s:elseif>	
	</s:if>
	
	</s:iterator>
	
	<!-- datepicker -->	
	<s:iterator value="%{#varClientContactEditList}" status="counter">
	<s:if test='%{colType == "Time"}'>
		<s:if test="%{colomnName == 'anniversary'}">
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{headingName}"/>:</div>
			<div class="rightColumn1"><s:if test="%{mandatroy == true}"><span class="needed"></span></s:if>
			     <sj:datepicker 
		     				name="%{colomnName}" 
		     				id="%{anniversaryId}" 
							value ="%{value}"
		     				changeMonth="true" 
		     				changeYear="true" 
		     				cssClass="textField"
                            cssStyle="width:82%"   
		     				yearRange="1890:2020" 
		     				showOn="focus" displayFormat="dd-mm-yy" placeholder="Select Date"/>
			</div>
			</div>	
		</s:if>
		<s:elseif test="%{colomnName == 'birthday'}">
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{headingName}"/>:</div>
			<div class="rightColumn1"><s:if test="%{mandatroy == true}"><span class="needed"></span></s:if>
			     <sj:datepicker 
		     				name="%{colomnName}" 
		     				id="%{birthdayId}" 
							value ="%{value}"
		     				changeMonth="true" 
		     				changeYear="true" 
		     				cssClass="textField"
                            cssStyle="width:82%"   
		     				yearRange="1890:2020" 
		     				showOn="focus" displayFormat="dd-mm-yy" placeholder="Select Date"/>
			</div>
			</div>	
		</s:elseif>	
	</s:if>
	
	</s:iterator>
	</div>
</s:iterator>

	<div class="clear"></div>
	<div class="fields">
	<div style="width: 100%; text-align: center; padding-bottom: 10px;">
		<sj:submit 
		     targets="contactresultId" 
		     formIds="formtwo"
		     clearForm="true"
		     value="Save" 
		     effect="highlight"
		     effectOptions="{ color : '#222222'}"
		     effectDuration="5000"
		     button="true"
		     onCompleteTopics="level2"
		     cssClass="submit" 
		     indicator="indicator2"
		     onBeforeTopics="validate"
		 />
	     
	</div>
	</div>
	<div class="clear"></div>
	<center>
		
             </center>
	<br>
	
</s:form>
</body>
</html>