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
<title>Vehicle Exit On Search</title>
			<script type="text/javascript">
				$.subscribe('presult', function(event,data)
				       {
				         setTimeout(function(){ $("#vehicleExitOnSearch").fadeIn(); }, 10);
				         setTimeout(function(){ $("#vehicleExitOnSearch").fadeOut(); }, 4000);
				        
				       });
			</script>
			<script type="text/javascript">
			function resetForm(formId)
			{
				$('#'+formId).trigger("reset");
			}
		</script>
		<script type="text/javascript">
		function exitVehicle(){
			var vehicleId = $("#sr_number").val();
			var vehiclenumber = $("#vehicle_number").val();
			var drivername = $("#driver_name").val();
			alert("Are You Sure To Exit Vehicle?");
			$.ajax({
			type:"post",
			url: "view/Over2Cloud/VAM/master/exitVehicle.action?vehicleId="+vehicleId+"&vehiclenumber="+vehiclenumber+"&drivername="+drivername,
			success : function(vehicleexitdata) {
			       $("#"+"vehicleExit").html(vehicleexitdata);
				},
				   error: function() {
			            alert("error");
			        }
			});
		}
		</script>
</head>
<body>
<div class="list-icon">
		<div class="head">Vehicle</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Exit</div>
	</div>
	<div class="clear"></div>
	<div style=" float:left; padding:10px 0%; width:100%;">
	<div class="clear"></div>
	<div class="border">
	<div class="container_block">
	<div style=" float:left; padding:20px 1%; width:98%;">
	<s:form id="formone" name="formone" theme="css_xhtml"  method="post" enctype="multipart/form-data">
	<div style="float:right; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 8px;">
	  <div id="errZone" style="float:left; margin-left: 7px"></div>        
	        </div>
	<div class="clear"></div>
  				<s:iterator value="vehicleentryfields" status="counter">
					<!--<s:if test="#counter.odd">
							<div class="clear"></div>
					</s:if> -->
					<s:if test="%{key=='sr_number'}">
						<div class="newColumn">
							<div class="leftColumn1"><s:property value="%{value}"/>:</div>
							<div class="rightColumn1">
								<s:if test="mandatory">
									<span class="needed"></span>
								</s:if>
								<s:hidden name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
							</div>
						</div>
					</s:if>
					<s:if test="%{key=='driver_name'}">
					<div class="newColumn">
							<div class="leftColumn1"><s:property value="%{value}"/>:</div>
							<div class="rightColumn1">
								<s:if test="mandatory">
									<span class="needed"></span>
								</s:if>
								<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
							</div>
						</div>
					</s:if>	
					<s:if test="%{key=='vehicle_number'}">
					<div class="newColumn">
							<div class="leftColumn1"><s:property value="%{value}"/>:</div>
							<div class="rightColumn1">
								<s:if test="mandatory">
									<span class="needed"></span>
								</s:if>
								<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" onchange="searchForVehicleNo(this.value)" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
							</div>
						</div>
					</s:if>	
			      </s:iterator>
			       <!-- Buttons -->
			<div class="clear"></div>
	 		<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
			<center>
			<div class="fields">
			<div class="buttonStyle" style="width: 212px;">
			<div class="type-button">
			  <sj:submit 
	           			   targets="vehicleexitresult" 
	                       clearForm="true"
	                       value="Search" 
	                       effect="highlight"
	                       effectOptions="{ color : '#222222'}"
	                       effectDuration="5000"
	                       button="true"
	                       onCompleteTopics="presult"
	                       cssClass="submit"
	                       indicator="indicator2"
	                       onclick="exitVehicle();"
	                       onBeforeTopics="validate"
	                       cssStyle="float: left;"
	         />
	        		 <sj:a 
						name="Reset"  
						cssClass="submit" 
						button="true" 
						onclick="resetForm('formone');"
						cssStyle="margin-left: -39%;"
						>
			  			Reset
						</sj:a>
						</div>
			</div></div>
					<sj:div id="vehicleExitOnSearch"  effect="fold">
					 <div id="vehicleexitresult"></div>
					</sj:div>
				   </center>	
			      
</s:form>
</div>
</div>
</div>
</div>
</body>
</html>