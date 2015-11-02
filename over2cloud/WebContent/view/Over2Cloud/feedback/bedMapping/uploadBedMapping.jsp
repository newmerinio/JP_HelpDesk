<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/feedback.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/feedback/bedMapping.js"/>"></script>
<script type="text/javascript">
function getEmployee(deptid,targetid,frontVal) {
	$.ajax({
		type :"post",
		url :"view/Over2Cloud/feedback/bedMapping/getMappedEmployee.action?deptId="+ deptid,
		success : function(empData){
		$('#'+targetid+' option').remove();
		$('#'+targetid).append($("<option></option>").attr("value",-1).text(frontVal));
    	$(empData).each(function(index)
		{
		   $('#'+targetid).append($("<option></option>").attr("value",this.id).text(this.name));
		});
	    },
	    error : function () {
			alert("Somthing is wrong to get Data");
		}
	});
  }

</script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	<div class="head">Bed Mapping</div><img alt="" src="images/forward.png" style="margin-top:10px; float: left;"><div class="head">Upload</div> 
</div>
<div class="clear"></div>
<div style="overflow:auto; height:270px; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">
<s:form id="formone" name="formone" action="uploadBedMappingExcel"  theme="css_xhtml"  method="post" enctype="multipart/form-data">
		<center>
			<div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
	             <div id="errZone" style="float:center; margin-left: 7px"></div>        
	        </div>
        </center>    
        
		 <span id="normalSubdept" class="pIds" style="display: none; ">deptName#Department#D#,empName#Employee#D#,bedMappingExcel#Excel#D#,</span>
                   <div class="newColumn">
        			   <div class="leftColumn" >Department&nbsp;:&nbsp;</div>
					        <div class="rightColumn">
					        <span class="needed"></span>
				                  <s:select  id="deptName"
				                              name="deptName"
				                              list="#{'1':'IT','109':'Patient Care'}"
				                              headerKey="-1"
				                              headerValue="Select Department" 
				                              onchange="getEmployee(this.value,'empName','Select Employee')"
				                              cssClass="select"
					                          cssStyle="width:82%"
				                              >
				                  </s:select>
                           </div>
                   </div>
                     <div class="newColumn">
					      <div class="leftColumn">Employee&nbsp;:&nbsp;</div>
							<div class="rightColumn">
							<span class="needed"></span>
				                  <s:select 
				                              id="empName"
                              				  name="empName"
				                              list="{'No Data'}"
				                              headerKey="-1"
				                              headerValue="Select Employee" 
				                              cssClass="select"
				                              cssStyle="width:82%"
				                              >
				                  </s:select>
				           </div>
	                  </div>
	 
	 <div class="newColumn">
			 <div class="leftColumn" >Excel:</div>
			 <div class="rightColumn">
			     <span class="needed"></span>
			     <s:file name="bedMappingExcel" id="bedMappingExcel" cssClass="textField"/>
			 </div>
		 </div>
	     <div class="newColumn">
			<div class="leftColumn">Excel format:</div>
			<div class="rightColumn">
			     
			 	 <a href="<%=request.getContextPath()%>/view/Over2Cloud/feedback/bedMapping/excel_format/Bed_Room_Mapping.xls" ><font color="#194d65">Download </font></a>
	        </div>
	     </div>
	     
	     <div class="clear"></div>
	 
	
	 <div class="clear"></div>
	<!--  <div class="fields" align="center"> -->
	<center>
				<ul>
				<li class="submit" style="background: none;">
					<div class="type-button">
	                <sj:submit 
	                        id="onlineSubmitId"
	                        targets="createExcel" 
	                        clearForm="true"
	                        value="Upload" 
	                        button="true"
	                        disabled="false"
	                        onBeforeTopics="validateBedmappingUploadForm"
	                        cssStyle="margin-left: -40px;"
				            />
				            <sj:a cssStyle="margin-left: 200px;margin-top: -45px;" button="true" href="#" onclick="resetForm('formone');">Reset</sj:a>
				            <sj:a cssStyle="margin-left: 7px;margin-top: -45px;" button="true" href="#" onclick="viewBedMapping();">View</sj:a>
	               </div>
	               </li>
				</ul>
				<!-- </div> -->
				</center>
				 <center>
		     <sj:dialog id="ExcelDialog" autoOpen="false" closeOnEscape="true"  modal="true" title="Bed Mapping >> List" width="1150" height="450" showEffect="slide" hideEffect="explode" position="['center','center']">
	                  <center><div id="createExcel"></div></center>
	         </sj:dialog>
         </center>
				
</s:form>
</div>
</div>
</body>
</html>
