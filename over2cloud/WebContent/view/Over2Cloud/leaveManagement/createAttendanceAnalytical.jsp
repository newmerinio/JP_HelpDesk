<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/leaveManagement/validation2.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/leaveManagement/leave.js"/>"></script>

<SCRIPT type="text/javascript">
$.subscribe('level1', function(event,data)
	       {
	         setTimeout(function(){ $("#orglevel1Div").fadeIn(); }, 10);
	         setTimeout(function(){ $("#orglevel1Div").fadeOut(); }, 4000);
	       });


function viewGroup() {
	var conP = "<%=request.getContextPath()%>";
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
   $.ajax({
	    type : "post",
	    //url : "/over2cloud/view/Over2Cloud/commonModules/makeHistory.action",
	    url : "view/Over2Cloud/hr/beforeCommonContactViewHeader.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function getOnChangeData()
{
	var empname=$("#empname").val();
	var fdate=$("#fdate").val();
	var tdate=$("#tdate").val();
	var modifyFlag=$("#modifyFlag").val();
	$.ajax({
	    type : "post",
	    //url : "/over2cloud/view/Over2Cloud/commonModules/makeHistory.action",
	    url : "view/Over2Cloud/leaveManagement/reportDetailAction.action?empname="+empname+"&fdate="+fdate+"&tdate="+tdate,
	    success : function(subdeptdata) {
       $("#contactDetails").html(subdeptdata);
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
	 <div class="head">Analytical</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Report</div> 
</div>
<div class="clear"></div>
    <div style="width: 100%; center; padding-top: 10px;">
       <div class="border" style="height: 50%">
   <s:form id="formone" name="formone"  theme="css_xhtml"  method="post" enctype="multipart/form-data" >
                           <center>
			                 <div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
                             <div id="errZone" style="float:center; margin-left: 7px"></div>        
                             </div>
                           </center>
                           
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr>
					    <td class="pL10"> 
				<s:param name="empname" value="%{empname}" />
             <s:param name="fdate" value="%{fdate}" />
             <s:param name="tdate" value="%{tdate}" />
		      
		     <div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
            <div id="errZone" style="float:left; margin-left: 7px"></div>        
            </div>

				
						<s:select 
						id="deptName"
						 list="deptDataList"
						  headerKey="-1"
							headerValue="Select"
							 cssClass="button"
						   cssStyle="margin-top: 0px;margin-left:3px "
							onchange="getContactdata(this.value,'empname');"
							 theme="simple"
							>
						</s:select>
					

				<s:iterator value="attendanceColumnDropDown">
					  <s:if test="%{key == 'empname'}">
					  	    <s:if test="%{mandatory}">
                           <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                           </s:if>
					 
	                  <div id="employeeType1">
	                  <s:select 
                              id="%{Key}"
                              name="%{Key}" 
                              list="employeelist"
                              headerKey="-1"
                              headerValue="Select Employee" 
                              cssClass="button"
							  cssStyle="margin-top: -30px;margin-left:129px"
							  theme="simple"
                              >
                      </s:select>
                      </div>
                      </s:if>
                      </s:iterator>
                       <sj:datepicker name="fdate" id="fdate" readonly="true" changeMonth="true" changeYear="true" yearRange="2013:2020" showOn="focus" displayFormat="dd-mm-yy" cssStyle="height: 16px; width: 70px; margin-left:305px; margin-top: -29px;" cssClass="button" placeholder="Select From Time"/>
                     <sj:datepicker name="tdate" id="tdate" readonly="true"  onchange="getOnChangeData()" changeMonth="true" changeYear="true" yearRange="2013:2020" showOn="focus" displayFormat="dd-mm-yy" cssStyle="height: 16px; width: 70px;margin-left:414px; margin-top: -29px;" cssClass="button" placeholder="Select To Time"/>
					     </td></tr></tbody>
					  </table>
				  </td>
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
	      		  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
			
				 <div id="contactDetails" >
				 	</div>
				 <div class="clear"></div>
			    
   </s:form>          
</div>
</div>
</body>
</html>