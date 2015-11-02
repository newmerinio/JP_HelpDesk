<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ taglib prefix="s" uri="/struts-tags" %>
    <%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<SCRIPT type="text/javascript">
function resetForm(formThree)
{
	$('#'+formThree).trigger("reset");
}

	$.subscribe('complete', function(event,data)
	      {
	        setTimeout(function(){ $("#fadeDIV").fadeIn(); }, 10);
	        setTimeout(function(){ $("#fadeDIV").fadeOut(); }, 4000);
	        $('select').find('option:first').attr('selected', 'selected');
	       
	      });

    function returnOBD()
    {
    	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    
		    url : "view/Over2Cloud/wfpm/target/beforeOBDTarget.action?headerName= OBD Target >> View",
		    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
		            alert("error");
		        }
		 });
    }

    function selectEmployee(val)
    {
		$.ajax({
		    type : "post",
		    dataType: 'json',
		    url : "view/Over2Cloud/wfpm/target/targetByEmp.action?empID="+ val,
		    success : function(data) {
		       $('#totalCall').val(data.TotalCall);
		       $('#productiveCall').val(data.ProductiveCall);
		       $('#totalSale').val(data.TotalSale);
		       $('#newOutlet').val(data.NewOutlet);
		},
		   error: function() {
		            alert("error");
		        }
		 });
    }
</SCRIPT>
</head>
<body>
	<div class="clear"></div>
	<div class="list-icon">
		<%-- <div class="head"><s:property value="headerName"/> </div> --%>
		<div class="head">Configure</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">OBD Target</div>
	</div>
	
	<div style=" float:left; padding:20px 1%; width:98%;">
	<div class="border">
		<!-- <div class="secHead">Allot OBD Target</div> -->
		<s:form id="formThree" name="formThree" cssClass="cssn_form" namespace="/view/Over2Cloud/wfpm/target" action="addOBDConfiguration" theme="simple"  method="post" enctype="multipart/form-data" >
			<div class="menubox">
				<div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
           			<div id="assErrZone" style="float:left; margin-left: 7px"></div> 
				</div>
				<div class="newColumn">
					<div class="leftColumn1"><s:property value="empName"/></div>
					<div class="rightColumn1">
						<s:if test="isEmpNameExist"><span class="needed"></span></s:if>
						<s:select 
	                       cssClass="select"
                              cssStyle="width:82%"
	                       id="empName"
	                       name="empName" 
	                       list="empDataList" 
	                       headerKey="-1"
	                       headerValue="--Select Employee Name--" 
	                       onchange="selectEmployee(this.value);"
	                       theme="simple"
	                       > 
	                  	</s:select>
					</div>
				</div>
				<s:iterator value="obdBoxForConfig" status="counter">
					<s:if test="%{mandatory}">
           				<span id="obdMandatoryFields" class="aIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
      				</s:if>
      				
      				<s:if test="#counter.odd">
      					<div class="clear"></div>
      				</s:if>
      				<div class="newColumn">
       					<div class="leftColumn1"><s:property value="%{value}"/>:</div>
        				<div class="rightColumn1">
        					<s:if test="%{mandatory}">
        						<span class="needed"></span>
        					</s:if>
        					<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
        				</div>
       				</div>
				</s:iterator>
				
				<div class="clear"></div>
				<!-- Buttons -->
				<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
				<div class="buttonStyle" style="width: 100%; text-align: center; padding-bottom: 10px;">
					<sj:submit 
					  	targets="result"
			            clearForm="true"
			            value="Save" 
			            effect="highlight"
			            effectOptions="{ color : '#222222'}"
			            effectDuration="5000"
			            button="true"
			            onCompleteTopics="complete"
			            cssClass="submit"
			            indicator="indicator4"
			            onBeforeTopics="validate1"
					 />
						<sj:a 
							     	name="Reset"  
									href="#" 
									cssClass="button" 
									button="true" 
									onclick="resetForm('formThree');"
									cssStyle="margin-left: -1px;margin-top: -40px;margin-top: 0px;"
								>
								  	Reset
								</sj:a>		          
				    <sj:a 
						name="Cancel"  
						href="#" 
						cssClass="button" 
						indicator="indicator" 
						button="true" 
						onclick="returnOBD()"
						cssStyle="margin-top: 0px;"
						
					>
					  	Back
					</sj:a>
				</div>
				<sj:div id="fadeDIV"  effect="fold">
	                <div id="result"></div>
	            </sj:div>
								    
			</div>
		</s:form>
	</div>
</body>
</html>