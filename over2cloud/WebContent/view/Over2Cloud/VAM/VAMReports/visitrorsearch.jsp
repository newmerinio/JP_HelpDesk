	<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
	<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<%@ taglib prefix="s" uri="/struts-tags" %>
	<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
	<%@ taglib prefix="sjr" uri="/struts-jquery-richtext-tags"%>
	<style type="text/css">
	
	.user_form_input{
	    margin-bottom:10px;
	}
	</style>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<s:url  id="contextz" value="/template/theme" />
	<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
	<script type="text/javascript" src="<s:url value="/js/menucontrl.js"/>"></script>
	<script type="text/javascript" src="js/VAM/menucontrl_VAM.js"></script>
	<title>Visitor Search</title>
		<script type="text/javascript">
			$.subscribe('presult', function(event,data)
			       {
			         setTimeout(function(){ $("#visitorsearch").fadeIn(); }, 10);
			         setTimeout(function(){ $("#visitorsearch").fadeOut(); }, 4000);
			        
			       });
		
		</script>
		
	<script type="text/javascript">
	$("#visitorSearchCancel").click(function(){
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/VAM/reports/visitorreportmenuview.action?modifyFlag=0&deleteFlag=0",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});
	
	</script>
	<script type="text/javascript">
	function searchVisitorData(){
		 var visitorMIS = "MIS";
		 var deptname = $("#deptName option:selected").val();
		 var location = $("#location option:selected").val();
		 var srnumber = $("#sr_number").val();
		 var visitorname = $("#visitor_name").val();
		 var visitormobile = $("#visitor_mobile").val();
		 var visitedperson = $("#visited_person").val();
		 var visitedmobile = $("#visited_mobile").val();
		 var visitorcompany = $("#visitor_company").val();
		 var fromdate = $("#from_date").val();
		 var todate = $("#to_date").val();
		/*  alert("deptname>"+deptname+"location>"+location+"srnumber>"+srnumber+"visitorname>"+visitorname+"visitormobile>"+visitormobile
				 +"visitorcompany>"+visitorcompany+"fromdate>"+fromdate+"todate>"+todate); */
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/VAM/reports/visitorSearch.action?modifyFlag=0&deleteFlag=0&deptname="+deptname+"&location="+location+"&srnumber="+srnumber+"&visitorname="+visitorname+"&visitormobile="+visitormobile+"&visitorcompany="+visitorcompany+"&fromdate="+fromdate+"&todate="+todate+"&visitedperson="+visitedperson+"&visitedmobile="+visitedmobile+"&visitorMIS="+visitorMIS,
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
		}
	
	</script>
	<script type="text/javascript">
	function resetForm(formId)
	{
		$('#'+formId).trigger("reset");
	}
	</script>
</head>
<body>
	<div class="list-icon">
	<div class="head">Visitor</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Search</div>
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
	<!-- Drop down -->
	<s:iterator value="deptDropDown" status="counter">
					<s:if test="#counter.odd">
							<div class="clear"></div>
					</s:if>
					<s:if test="%{key == 'deptName'}">
					<div class="newColumn">
						<div class="leftColumn1"><s:property value="%{value}"/>:</div>
						<div class="rightColumn1">
						         <s:select 
						                     id="deptName"
						                     name="deptName" 
						                     list="departmentlist"
						                     headerKey="-1"
						                     headerValue="Select Department" 
						                     cssClass="select"
						                     cssStyle="width:82%"
						                     >
						         </s:select>
						</div>
						</div>
						</s:if>
						<s:elseif test="%{key == 'location'}">
						<div class="newColumn">
						<div class="leftColumn1"><s:property value="%{value}"/>:</div>
						<div class="rightColumn1">
						         <s:select 
						                     id="location"
						                     name="location" 
						                     list="locationlist"
						                     headerKey="-1"
						                     headerValue="Select Location" 
						                     cssClass="select"
						                     cssStyle="width:82%"
						                     >
						         </s:select>
						</div>
						</div>
						</s:elseif>
						
         			</s:iterator>
         			<div class="clear"></div>
					<!-- Text box -->
					<s:iterator value="visitorsearchfields" status="counter">
						<div class="newColumn">
							<div class="leftColumn1"><s:property value="%{value}"/>:</div>
							<div class="rightColumn1">
								<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
							</div>
						</div>
						
         			</s:iterator>
         		 <div class="newColumn">
				  <div class="leftColumn1">From Date:</div>
                  <div class="rightColumn1">
                  <sj:datepicker name="from_date" id="from_date" showOn="focus" displayFormat="dd-mm-yy" value="today" yearRange="2014:2020" readonly="true" cssStyle="margin:0px 0px 10px 0px" cssClass="textField" />
                  </div>
			      </div>
			       <div class="newColumn">
			       <div class="leftColumn1">To Date:</div>
                   <div class="rightColumn1">
				  <sj:datepicker name="to_date" id="to_date" showOn="focus" displayFormat="dd-mm-yy" value="today" yearRange="2014:2020" readonly="true" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"  />
				  </div>
			      </div>
         	<!-- Buttons -->
			<div class="clear"></div>
	 		<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
				<center>
				<div class="fields">
			<div class="buttonStyle" style="width: 212px;">
			<div class="type-button" style="width: 108%;">
	        
	         <s:if test="%{visitorMIS == 'MIS'}">
	         <sj:submit 
	           			   targets="visitorsearchresult" 
	                       clearForm="true"
	                       value="Search" 
	                       effect="highlight"
	                       effectOptions="{ color : '#222222'}"
	                       effectDuration="5000"
	                       button="true"
	                       onCompleteTopics="presult"
	                       cssClass="submit"
	                       indicator="indicator2"
	                       onclick="searchVisitorData();"
	                       onBeforeTopics="validate"
	                       cssStyle="float: left;"
	         />
	        		 <sj:a 
						name="Reset"  
						cssClass="button" 
						button="true" 
						onclick="resetForm('formone');"
						cssStyle="margin-left: -26%;"
						>
			  			Reset
						</sj:a>
	         </s:if>
	         <s:else>
	         <sj:submit 
	           			   targets="visitorsearchresult" 
	                       clearForm="true"
	                       value="Search" 
	                       effect="highlight"
	                       effectOptions="{ color : '#222222'}"
	                       effectDuration="5000"
	                       button="true"
	                       onCompleteTopics="presult"
	                       cssClass="submit"
	                       indicator="indicator2"
	                       onclick="searchVisitorData();"
	                       onBeforeTopics="validate"
	                       cssStyle="float: left; width: 33%;"
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
						cssClass="submit" 
						indicator="indicator" 
						button="true" 
						id="visitorSearchCancel"
						>
						Back
				</sj:a>
	         </s:else>
	         
			</div>
			</div></div>
					<sj:div id="visitorsearch"  effect="fold">
					 <div id="visitorsearchresult"></div>
					</sj:div>
				   </center>		
	</s:form>
	
	</div>
	</div>
	<center>Note:Search without selecting any field to view all records.</center>
	</div>
	</div>
</body>
</html>