<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
		<%@taglib prefix="s" uri="/struts-tags" %>
	    <%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
	    <%@ taglib prefix="sjg"  uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>

<title>Insert title here</title>

	<script type="text/javascript">

		$.subscribe('level1', function(event,data)
		        {
		          setTimeout(function(){ $("#patientaddition").fadeIn(); }, 10);
		          setTimeout(function(){ $("#patientaddition").fadeOut(); }, 4000);
		          
		});
		 function resetForm(formId) 
		 {
			 $('#'+formId).trigger("reset");
		 }
		 function backToManagerView()
			{
				$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/></center>");
				$.ajax({
				    type : "post",
				    url : "view/Over2Cloud/WFPM/Patient/beforeviewManager.action",
				    
				    success : function(subdeptdata) {
				       $("#"+"data_part").html(subdeptdata);
				},
				   error: function() {
				            alert("error");
				        }
				 });
			}

		 function fetchEmployeeData(val,selectId)
		 {
		 	$.ajax({
		 	    type : "post",
		 	    url : "view/Over2Cloud/WFPM/Patient/fetchEmployeeData.action?depName="+val.value,
		 	    success : function(data) {
		 		    
		 		$('#'+selectId+' option').remove();
		 		$('#'+selectId).append($("<option></option>").attr("value",-1).text('Select'));
		     	$(data).each(function(index)
		 		{
		     		// alert(this.ID +" "+ this.NAME);
		 		   $('#'+selectId).append($("<option></option>").attr("value",this.ID).text(this.NAME));
		 		});
		 		
		 	},
		 	   error: function() {
		             alert("error");
		         }
		 	 });
		 }

			

			
			
			
		 function fetchLevelData(val,selectId,Offeringlevel)
		    {
		    	//  alert(val.value);
		    	//alert(Offeringlevel);
		    	
		    	$.ajax({
		    	    type : "post",
		    	    url : "view/Over2Cloud/wfpm/client/fetchOfferingLevelData.action?offeringId="+val.value+"&Offeringlevel="+Offeringlevel,
		    	    success : function(data) {
		    		    
		    		$('#'+selectId+' option').remove();
		    		$('#'+selectId).append($("<option></option>").attr("value",-1).text('Select'));
		        	$(data).each(function(index)
		    		{
		        		//  alert(this.ID +" "+ this.NAME);
		    		   $('#'+selectId).append($("<option></option>").attr("value",this.ID).text(this.NAME));
		    		});
		    		
		    	},
		    	   error: function() {
		                alert("error");
		            }
		    	 });
		    }
				
	</script>
	
</head>
<body>
	<div class="clear"></div>
	<div class="list-icon">
	<!-- <div class="head">Add Prospective Client</div> -->
	<div class="head">Doctor Offering Map</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:60%; float: left;"></div><div class="head">Add</div>
	</div>
	<div class="clear"></div>
<div class="border">
	<div class="container_block">
	<div style=" float:left; padding:10px 1%; width:98%;">
	<div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
             <div id="errZone" style="float:left; margin-left: 7px">
             </div> 
       	 </div>	
        	<br>
	<s:form id="formone" name="formone" namespace="/view/Over2Cloud/WFPM/Patient" action="addDocOfferingSave" theme="css_xhtml"  method="post"enctype="multipart/form-data" >
    <div class="menubox">
   	<s:iterator value="clientDropDown" status="counter">
	    <s:if test="%{mandatory}">
	       <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	    </s:if> 	
	</s:iterator> 
       
       <s:if test="OLevel1">
			<span id="form2MandatoryFields" class="qIds" style="display: none; ">verticalname#<s:property value="%{OLevel1LevelName}"/>#D#,</span>
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{OLevel1LevelName}"/>:</div>
			<div class="rightColumn1">
			<span class="needed"></span>
			<s:select 
		                              id="verticalname"
		                              name="verticalname" 
		                              list="verticalMap"
		                              headerKey="-1"
		                              headerValue="Select Name" 
		                              cssClass="select"
		                              cssStyle="width:82%" 
		                              onchange="fetchLevelData(this,'offeringname','1')"
		                              >
		                   
		                  </s:select>
			</div>
			</div>
			</s:if>
			<s:if test="OLevel2">
			<span id="form2MandatoryFields" class="qIds" style="display: none; ">offeringname#<s:property value="%{OLevel2LevelName}"/>#D#,</span>
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{OLevel2LevelName}"/>:</div>
			<div class="rightColumn1">
			<span class="needed"></span>
			<s:select 
		                              id="offeringname"
		                              name="offlevel3" 
		                              list="#{'-1':'Select Speciality'}"
		                               cssClass="select"
		                              cssStyle="width:82%"
		                              onchange="fetchLevelData(this,'subofferingname','2')"
		                              >
		                  	</s:select>
			</div>
			</div>
			</s:if>
			<%-- <s:if test="OLevel3">
			<span id="form2MandatoryFields" class="qIds" style="display: none; ">subofferingname#<s:property value="%{OLevel3LevelName}"/>#D#,</span>
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{OLevel3LevelName}"/>:</div>
			<div class="rightColumn1">
			<span class="needed"></span>
			<s:select 
		                              id="subofferingname"
		                              name="offlevel3" 
		                              list="#{'-1':'Select Sub Offering'}"
		                               cssClass="select"
		                              cssStyle="width:82%"
		                              onchange="fetchLevelData(this,'variantname','3');checkSuboffering(this.value,clientN.value);"
		                              >
		                  	</s:select>
			</div>
			</div>
			</s:if> --%>
       
         <!-- Drop down offering -->
			<span id="form2MandatoryFields" class="qIds" style="display: none; ">department#<s:property value="%{OLevel1LevelName}"/>#D#,</span>
			<div class="newColumn">
			<div class="leftColumn1">Department:</div>
			<div class="rightColumn1">
			<span class="needed"></span>
			<s:select 
		                              id="department"
		                              name="deptName" 
		                              list="deptMap"
		                              headerKey="-1"
		                              headerValue="Select Department" 
		                              cssClass="select"
		                              cssStyle="width:82%" 
		                              onchange="fetchEmployeeData(this,'relmgrname')"
		                              >
		                   
		                  </s:select>
			</div>
			</div>
			<span id="form2MandatoryFields" class="qIds" style="display: none; ">doctor_name#<s:property value="%{OLevel2LevelName}"/>#D#,</span>
			<div class="newColumn">
			<div class="leftColumn1">Doctor:</div>
			<div class="rightColumn1">
			<span class="needed"></span>
			<s:select 
		                              id="relmgrname"
		                              name="doctor_name" 
		                              list="#{'-1':'Select Doctor'}"
		                              cssClass="select"
		                              cssStyle="width:82%"
		                              >
		                  	</s:select>
			</div>
			</div>
			
    
<!-- Text box -->
	<s:iterator value="patientTextBox" status="counter">
	<s:if test="%{mandatory}">
	<span id="mandatoryField" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	</s:if>

	<s:if test="#counter.odd">
	
	<div class="newColumn" >
	<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	<div class="rightColumn1">
	<s:if test="%{mandatory}">
	<span class="needed"></span>
	</s:if>
	<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
	</div>
	</div>
	</s:if>
	
	<s:else>
	<div class="clear"></div>
	<div class="newColumn" >
	<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	<div class="rightColumn1">
	<s:if test="%{mandatory}">
	<span class="needed"></span>
	</s:if>
	<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
	</div>
	</div>
	</s:else>
	
	</s:iterator> 
	
         
	</div>
	<div class="clear"></div>
	
	

	
	
	
	
	
	<!-- Buttons -->
	<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
	<div class="buttonStyle" style="text-align: center;margin-left: -100px;">
         <sj:submit 
            targets="patientresult" 
            clearForm="true"
            value="Save" 
            effect="highlight"
            effectOptions="{ color : '#222222'}"
            effectDuration="5000"
            button="true"
            onCompleteTopics="level1"
            cssClass="submit"
            indicator="indicator2"
            onBeforeTopics="validate1"
            
          />
           <sj:a 
	     	    name="Reset"  
					href="#" 
					cssClass="submit" 
					indicator="indicator" 
					button="true" 
					onclick="resetForm('formone');"
					cssStyle="margin-left: 193px;margin-top: -43px;"
					>
					  	Reset
					</sj:a>
				          <sj:a 
					     	name="Cancel"  
					href="#" 
					cssClass="submit" 
					indicator="indicator" 
					button="true" 
					cssStyle="margin-left: 145px; margin-top: -25px;"
					onclick="backToDocOfferingMapView()"
					cssStyle="margin-top: -43px;"
					
					>
					  	Back
					</sj:a>
					    </div>
	    
	
	<sj:div id="patientaddition"  effect="fold">
           <div id="patientresult"></div>
        </sj:div>
	</s:form>
	
	
	
	</div>
	</div>
	</div>
</body>
</html>