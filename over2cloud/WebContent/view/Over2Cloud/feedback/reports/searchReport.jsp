<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<SCRIPT type="text/javascript" src="js/commanValidation/validation.js"></SCRIPT>
<SCRIPT type="text/javascript" src="js/feedback/feedbackForm.js"></SCRIPT>
<SCRIPT type="text/javascript">
function feedbackReport(val,data)
{
	$("#"+data).val(val);
}


function viewSearchdData()
{
	
	var startDate=$("#startDate").val();
	var endDate=$("#endDate").val();
	var patType=$("#patType").val();
	var smsStat=$("#smsStat").val();

	var conP = "<%=request.getContextPath()%>";
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $.ajax({
        type : "post",
        url : conP+"/view/Over2Cloud/feedback/report/beforeSearchedDataView.action?startDate="+startDate+"&endDate="+endDate+"&patType="+patType+"&smsStat="+smsStat.split(" ").join("%20"),
        success : function(subdeptdata) {
      $("#"+"data_part").html(subdeptdata);
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
	 <div class="head">Feedback Reports</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Search</div> 
</div>
<div class="clear"></div>
     <div style="width: 100%; center; padding-top: 10px;">
       <div class="border" style="height: 50%">
   		<s:form id="formone" name="formone" theme="css_xhtml"  method="post"enctype="multipart/form-data" >
			<center>
				<div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
					<div id="errZone" style="float:center; margin-left: 7px"></div>        
				</div>
            </center>
            <div class="newColumn">
				<div class="leftColumn">Start Date:</div>
				<div class="rightColumn">
					<sj:datepicker  name="startDate" id="startDate"  value="today" placeholder="Select Start Date" readonly="true" showOn="focus"  changeMonth="true" changeYear="true" yearRange="2013:2020" maxDate="+12m" showAnim="slideDown" duration="fast" displayFormat="dd-mm-yy" cssClass="textField"/>
				</div>
			</div>
			<div class="newColumn">
				<div class="leftColumn">End Date:</div>
				<div class="rightColumn">
					<sj:datepicker  name="endDate" id="endDate"  value="today" placeholder="Select End Date" readonly="true" showOn="focus"  changeMonth="true" changeYear="true" yearRange="2013:2020" maxDate="+12m" showAnim="slideDown" duration="fast" displayFormat="dd-mm-yy" cssClass="textField"/>
				</div>
			</div>
			<div class="newColumn">
				<div class="leftColumn">Patient Type:</div>
				<div class="rightColumn">
					<s:select 
                      id="patType" 
			          name="patType" 
			          list="{'IPD','OPD'}"
			          headerKey="-1"
			          headerValue="Select Patient Type"
			          cssClass="select"
			          cssStyle="width:82%"
         			 />
				</div>
			</div>
			<div class="newColumn">
				<div class="leftColumn">SMS Status:</div>
				<div class="rightColumn">
					<s:select 
                      id="smsStat" 
			          name="smsStat" 
			          list="{'Not Send','Send'}"
			          headerKey="-1"
			          headerValue="Select SMS Status"
			          cssClass="select"
			          cssStyle="width:82%"
         			 />
				</div>
			</div>
			    <center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
				<div class="clear" >
				<div style="padding-bottom: 10px;margin-left:100px" align="center">
						<sj:a
		                     value="Reset" 
		                     onclick="resetForm('formone');" button="true"
			            >Reset
			            </sj:a>
			            &nbsp;
			            <sj:a
						button="true" href="#" value="View" onclick="viewSearchdData();"  
						>View
					</sj:a>
					</div>
               </div>
               <sj:div id="orglevel1Div"  effect="fold">
                   <sj:div id="level123">
                   </sj:div>
               </sj:div>
   </s:form>          
</div>
</div>
</body>
</html>