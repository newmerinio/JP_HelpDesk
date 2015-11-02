<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript">
$.subscribe('completeData', function(event,data)
{
	  setTimeout(function(){ $("#confirmingSuccess").fadeIn(); }, 10);
      setTimeout(function(){ $("#confirmingSuccess").fadeOut(); }, 4000);
      $('select').find('option:first').attr('selected', 'selected');
});
function resetForm(formId) 
{
	 $('#'+formId).trigger("reset");
}
function getSubCategoryData(category,divId)
{
	var moduleName=$("#moduleName").val();
	
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/RCAOver2Cloud/RCAMaster/getSubCategory.action?moduleName="+moduleName+"&category="+category,
	    success : function(data) 
	    {
	    	$('#'+divId+' option').remove();
			$('#'+divId).append($("<option></option>").attr("value",-1).text('Select Name'));
			
			$(data).each(function(index)
			{
			   $('#'+divId).append($("<option></option>").attr("value",this.ID).text(this.NAME));
			});
	   },
	   error: function() {
          alert("error");
      }
	 });
}
function viewRCA() 
{
	var moduleName=$("#moduleName").val();
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/RCAOver2Cloud/RCAMaster/beforeRCAMasterView.action?moduleName="+moduleName,
	    success : function(confirmation) {
      $("#"+"data_part").html(confirmation);
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
	 <div class="head">RCA</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Add</div> 
</div>
 <div style=" float:left; padding:5px 0%; width:100%;">
	<div class="border" style="overflow-x:hidden;">
	
        <s:form id="formtask" name="formtask" action="addRCAMaster" theme="css_xhtml"  method="post" enctype="multipart/form-data" >
		  
		   <div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
             <div id="errZone" style="float:left; margin-left: 7px">
             </div> 
          </div>
		  <s:hidden name="moduleName" id="moduleName" value="%{moduleName}"/>
		      <s:iterator value="rcaDropDown">
			      <s:if test="%{mandatory}">
	                      <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	              </s:if> 
			    <s:if test="%{key == 'deptName'}">
               <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <s:select 
                              id="%{key}"
                              name="%{key}"
                              list="listDept"
                              headerKey="-1"
                              headerValue="Select Department" 
                              cssClass="select"
                              cssStyle="width:82%"
                              >
                               </s:select>
               </div> 
               </div>               
               </s:if>
              <s:if test="%{key == 'category'}">
              <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                 			    <s:select 
                              id="%{key}"
                              name="%{key}"
                              list="listCategory"
                              headerKey="-1"
                              headerValue="Select Name" 
                              cssClass="select"
                              cssStyle="width:82%"
                              onchange="getSubCategoryData(this.value,'subCategory');"
                              >
                  				</s:select>
               </div>
               </div>   				
               </s:if>
               
                <s:if test="%{key == 'subCategory'}">
              <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                 			    <s:select 
                              id="%{key}"
                              name="%{key}"
                              list="listCategory"
                              headerKey="-1"
                              headerValue="Select Name" 
                              cssClass="select"
                              cssStyle="width:82%"
                              >
                  				</s:select>
               </div>
               </div>   				
               </s:if>
                </s:iterator>
              <s:iterator value="rcaTextField" status="counter">
                <s:if test="#counter.odd">
				 	<div class="newColumn">
					  <div class="leftColumn1"><s:property value="%{value}"/>&nbsp;:&nbsp;</div>
				            <div class="rightColumn1">
				                     <s:if test="%{mandatory}"><span class="needed"></span></s:if>
					                 <s:textfield name="%{key}"  id="%{key}" onblur="getDetail('%{key}')" maxlength="10" cssClass="textField" placeholder="Enter Data" />
				             </div>
			      </div>
				 </s:if>
				 <s:else>
				      <div class="newColumn">
			                <div class="leftColumn1"><s:property value="%{value}"/>&nbsp;:&nbsp;</div>
							<div class="rightColumn1">
							<s:if test="%{mandatory}"><span class="needed"></span></s:if>
							    <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" />
							</div>
					 </div>
				 </s:else>
             </s:iterator>
		    
		     <div class="clear"></div> 
             <center><img id="indicator222" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
		     <center>
			 <div class="type-button" style="text-align: center;">
	         <sj:submit 
	                        targets	="confirmingSuccess" 
	                        clearForm	="true"
	                        value	=" Save " 
	                        button	="true"
	                        onBeforeTopics	="validate"
	                        cssClass	="submit"
	                        indicator	="indicator222"
	                        cssStyle	="margin-left: -32px;"
	                        effect			=	"highlight"
                 			effectOptions	=	"{ color : '#222222'}"
                 			effectDuration	=	"5000"
	                        onCompleteTopics="completeData"
	                        />
	   
			        
		              <sj:a cssStyle="margin-left: 181px;margin-top: -45px;" button="true" href="#" onclick="resetForm('formtask');">Reset</sj:a>
		             <sj:a cssStyle="margin-left: 0px;margin-top: -45px;" button="true" href="#" onclick="viewRCA();">Back</sj:a>  
	        </div>
	        </center>
		  <div align="center">
			 <sj:div id="confirmingSuccess"  effect="fold"> </sj:div>
         </div>
	 
	 </s:form>
	  </div>
	  </div>
</body>
</html>
