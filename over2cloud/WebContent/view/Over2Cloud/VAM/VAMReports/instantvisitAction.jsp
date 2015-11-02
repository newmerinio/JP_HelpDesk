<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="s" uri="/struts-tags" %>
	<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<s:url  id="contextz" value="/template/theme" />
	<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
	<script type="text/javascript" src="<s:url value="/js/menucontrl.js"/>"></script>
	<script type="text/javascript" src="js/VAM/menucontrl_VAM.js"></script>
	<title>Instant Visit Action</title>
	<script type="text/javascript">
	$.subscribe('presult', function(event,data)
		       {
		         setTimeout(function(){ $("#instantactionresultid").fadeIn(); }, 10);
		         setTimeout(function(){ $("#instantactionresultid").fadeOut(); }, 4000);
		       });
	</script>
	<script type="text/javascript">
	function resetForm(formId)
	{
		$('#'+formId).trigger("reset");
	}
	</script>
	<script type="text/javascript"><!--
	function takeActionAcceptReject()
	{
		var rowid = $("#rowid").val();
		var comments = $("#comments").val();
		//var deptname = $("#deptName option:selected").val();
		var visitorstatusAction = $("#visitorstatusActionid option:selected").text();
		alert("visitorstatusAction>"+visitorstatusAction+"comments>"+comments);
		 $.ajax({
			    type : "post",
			    url : "view/Over2Cloud/VAM/reports/instantvisitactionsubmit.action?rowid="+rowid+"&visitorstatusAction ="+visitorstatusAction+"&comments="+comments,
			    success : function(subdeptdata) {
		      
			},
			   error: function() {
		            alert("error");
		        }
			 });
	}
	--></script>
</head>
<body>
	<div class="list-icon">
	<div class="head">Instant Visitor</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Action</div>
	</div>
	<div class="clear"></div>
	<div style=" float:left; padding:10px 0%; width:100%;">
	<div class="clear"></div>
	<div class="border">
	<div class="container_block">
	<div style=" float:left; padding:20px 1%; width:98%;">
	<s:form id="formone" name="formone" namespace="/view/Over2Cloud/VAM/reports" action="instantvisitactionsubmit" theme="css_xhtml"  method="post" >
		<div style="float:right; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 8px;">
	  	<div id="errZone" style="float:left; margin-left: 7px"></div>        
	    </div>
				<div class="clear"></div>
				<s:iterator value="oneList" status="counter">
						<s:if test="%{mandatory}">
							<span id="mandatoryFields" class="dIds" style="display: none; "><s:property value="%{key}" />#<s:property value="%{value}" />#<s:property value="%{colType}" />#<s:property value="%{validationType}" />,</span>
						</s:if>
							<div class="newColumn">
								<div class="leftColumn1"><s:property value="%{key}"/>:</div>
								<div class="rightColumn1">
									<s:textfield name="aaa%{#counter.count}"  id="aaa%{#counter.count}" value="%{value}" readonly="true" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
								</div>
							</div>
					</s:iterator>
						<div class="newColumn">
						<div class="leftColumn1">Request Action:</div>
						<div class="rightColumn1">
						         <s:select 
						                     id="visitorstatusActionid"
						                     name="visitorstatusAction" 
						                     list="#{'Approved':'Approved', 'Rejected':'Rejected'}"
						                     headerKey="-1"
						                     headerValue="Select Action" 
						                     cssClass="select"
						                     cssStyle="width:82%"
						                     >
						         </s:select>
						</div>
						</div>
						<div class="newColumn">
						<div class="leftColumn1">Comments:</div>
						<div class="rightColumn1">
							<s:textarea name="comments"  id="comments" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
						</div>
						</div>
						<s:hidden name="rowid"  id="rowid" value="%{rowid}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>			
			<!-- Buttons -->
			<div class="clear"></div>
	 		<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
			<center>	
			<div class="fields">
			<div class="buttonStyle" style="width: 212px;">
			<div class="type-button">
	        <sj:submit 
	                       targets="instantvisitoract" 
	                       clearForm="true"
	                       value="Save" 
	                       effect="highlight"
	                       effectOptions="{ color : '#222222'}"
	                       effectDuration="5000"
	                       button="true"
	                       onCompleteTopics="presult"
	                       cssClass="submit"
	                       indicator="indicator2"
	                       onBeforeTopics="validate"
	                       cssStyle="float: left;"
	                       onclick="takeActionAcceptReject();"
	         />
	         			<sj:a 
							name="Reset"  
							cssClass="button" 
							button="true" 
							onclick="resetForm('formone');"
						>
			  			Reset
						</sj:a>
	          <sj:a 
						name="Back"  
						href="#" 
						targets="result" 
						cssClass="button" 
						indicator="indicator" 
						button="true" 
						id="instantActionCancel"
						>
						Back
			</sj:a>
			</div>
			</div></div>
			<sj:div id="instantactionresultid"  effect="fold">
					 <div id="instantvisitoract"></div>
					</sj:div>
	   		</center>	
	</s:form>
	</div>
	</div>
	</div>
	</div>
	 <!--<div id="instantactionresultid"></div>
--></body>
</html>