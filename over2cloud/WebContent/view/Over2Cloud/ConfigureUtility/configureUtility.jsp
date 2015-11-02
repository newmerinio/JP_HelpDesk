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
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>

<script src="<s:url value="/js/common/commonvalidation.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/asset/AssetCommonValidation.js"/>"></script>
<SCRIPT type="text/javascript">
function changeThresholdMin(val,div1)
{
 var p=document.getElementById(div1);

if(val=="Yes")
{
	p.style.display='block';
}	
else if(val=="NO")
{
	p.style.display='none';
   
	}
	}

function changeThresholdMax(val,div1)
{
 var r=document.getElementById(div1);

if(val=="Yes")
{
	r.style.display='block';
}	
else if(val=="NO")
{
	r.style.display='none';
   
	}
	}

function getOutletName(AssocitaeTypeId,divId,div)
{
	
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/ConfigureUtility/getAssociateNameAction.action?associateType="+AssocitaeTypeId+"&divId="+divId,
	    success : function(data) {
	    	$('#'+div+' option').remove();
			$('#'+div).append($("<option></option>").attr("value",-1).text('Select Outlet Name'));
			$(data).each(function(index)
			{
			   $('#'+div).append($("<option></option>").attr("value",this.ID).text(this.NAME));
			});
	},
	   error: function() {
	        alert("error");
	    }
	 });
}

function viewConfigure()
 {
 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/ConfigureUtility/beforeViewConfigureGrid.action",
		    success : function(data) {
	       		$("#data_part").html(data);
	       		
			},
		    error: function() {
	            alert("error");
	        }
		 });
	
 }

function reset(formId) {
	  $("#"+formId).trigger("reset"); 
	}

</script>
</head>
<body>
<div class="list-icon">
	<div class="head">Configure</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Utility</div>

</div>
	<div class="clear"></div>
<div style=" float:left; padding:10px 0%; width:100%;">
<div class="clear"></div>
<div class="border">

<div class="container_block">
<div style=" float:left; padding:20px 1%; width:98%;">
<s:form id="formone" name="formone" action="configureAddAction" theme="simple"  method="post" enctype="multipart/form-data" >
		     	    <center>
		     		   <div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
                         <div id="errZone" style="float:left; margin-left: 7px"></div>        
                       </div>
                       </center>
		     		 <s:iterator value="configureUtilityColumnText">
		     		 <s:if test="%{mandatory}">
			     		<span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	               </s:if>
					  <s:if test="%{key == 'utility_Name'}">
	                 <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <s:textfield id="utility_Name" name="utility_Name"  cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" placeholder="Enter Data"  ></s:textfield>
                   </div>
                   </div>
                   </s:if>
                   <s:if test="%{key == 'brief'}">
                   <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <s:textfield id="brief" name="brief"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px" ></s:textfield>
                  </div>
                  </div>	
                  </s:if>
                  </s:iterator>
                  
                 
		  
				 <s:iterator value="UtilityColumnDropdown">
				          <s:if test="%{mandatory}">
                           <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                         </s:if>
					  <s:if test="%{key == 'outlet_type'}">
	                <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <span class="needed"></span>
                                <s:select 
	                              id="utility_id"
	                              name="associateType" 
	                              list='outletTypeList'
	                              headerKey="-1"
	                              headerValue="Select Outlet Type " 
	                              multiple="false"
                                  cssClass="select"
                         		  cssStyle="width:82%"
                                  onchange="getOutletName(this.value,'outletNameDiv11','outlet_Name')"
	                              >
                               </s:select>
                               </div>
                      </div>        
                      </s:if> 
                      <s:if test="%{key == 'outlet_Name'}">
	                <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <span class="needed"></span>
                                <div id="outletNameDiv11">
                                <s:select 
	                              id="outlet_Name"
	                              name="outlet_Name" 
	                              list="{'No Data'}"
	                              headerKey="-1"
	                              headerValue="Select" 
	                              multiple="true"
                                  cssStyle="width:82%;height:40%"
	                              >
                               </s:select>
                               </div>
                               </div>
                      </div>        
                      </s:if> 
                      
                 </s:iterator>  
                <div style="width:100%; line-height:40px;">
       <div style="text-align:right; padding:6px; line-height:25px;	float:left;	width:13%;">Threshold Level Min:</div>
       <div style="width: 100%">
       <s:if test="%{mandatory}"><span class="needed"></span></s:if>
       <s:radio list="#{'Yes':'Yes','NO':'NO'}" name="threshold_Level_Min_alert" id="threshold_Level_Min_alert" onclick="changeThresholdMin(this.value,'thresholdMin')"/>
      </div>
       </div>
        
       <div id="thresholdMin" style="display: none">
      
							<div class="newColumn">
							<div class="leftColumn1">Value</div>
							<div class="rightColumn">
								  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
       								<s:textfield id="threshold_Level_Min" name="threshold_Level_Min"  cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" placeholder="Enter Data"  ></s:textfield>
      						 </div>
	                  			<div id="errorlevel2org" class="errordiv"></div>
							</div>
							</div>
							
					 <div class="clear"></div> 		
					 <div style="width:100%; line-height:40px;">
       <div style="text-align:right; padding:6px; line-height:25px;	float:left;	width:13%;">Threshold Level Max:</div>
       <div style="width: 100%">
       <s:if test="%{mandatory}"><span class="needed"></span></s:if>
       <s:radio list="#{'Yes':'Yes','NO':'NO'}" name="threshold_Level_Max_alert" id="threshold_Level_Max_alert" onclick="changeThresholdMax(this.value,'thresholdMax')"/>
      </div>
       </div>
       <div id="thresholdMax" style="display: none">
        
							<div class="newColumn">
							<div class="leftColumn1">Value</div>
							<div class="rightColumn">
								  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
       								<s:textfield id="threshold_Level_Max" name="threshold_Level_Max"  cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" placeholder="Enter Data"  ></s:textfield>
      						 </div>
				                  
	                  			<div id="errorlevel2org" class="errordiv"></div>
							</div>
							
							</div>	
                  <div class="clear"></div> 	
                   <s:iterator value="UtilityColumnDropdown">
				          <s:if test="%{mandatory}">
                           <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                         </s:if>
					  <s:if test="%{key == 'alert_to'}">
	                <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <s:select 
	                              id="alert_to"
	                              name="alert_to" 
	                              list="empolyeeMap"
	                              headerKey="-1"
	                              headerValue="Select" 
	                              cssClass="select"
                                  cssStyle="width:82%;height:40%"
                                  multiple="true"
	                              >
                               </s:select>
                               </div>
                      </div>        
                      </s:if> 
                 </s:iterator>  
             	
				<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
				 <div class="clear"></div> 
				
<div class="type-button" style="margin-left: -200px;">
<center> 
       <sj:submit 
           targets="leadresult" 
                       clearForm="true"
                       value="Save" 
                       effect="highlight"
                       effectOptions="{ color : '#222222'}"
                       effectDuration="5000"
                       button="true"
                       onCompleteTopics="level1"
                       cssClass="submit"
                       cssStyle="margin-right: 1px;margin-bottom: -12px;"
                       indicator="indicator2"
                       onBeforeTopics="validate"
         />
         
         <sj:a 
				     	href="#" 
		               	button="true" 
		               	onclick="reset('formone');"
						cssStyle="margin-top: 12px;"						>
					  	Reset
		   </sj:a>
		   
		   <sj:a 
				     	href="#" 
		               	button="true" 
		               	onclick="viewConfigure();"
						cssStyle="margin-right: -137px;margin-top: 14px;"
						>
					  	Back
		   </sj:a>
		   
         
         
      </center>   
         
   </div>
				<sj:div id="orglevel1"  effect="fold">
                    <div id="orglevel1Div"></div>
               </sj:div>
	</s:form>	
</div>
</div>
</div></div>

</body>
</html>