<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<style type="text/css">

.user_form_input{
    margin-bottom:10px;
}

</style>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>

<script src="<s:url value="/js/lead/LeadCommon.js"/>"></script>

<SCRIPT type="text/javascript">
function resetForm(formone)
{
	$('#'+formone).trigger("reset");
}
function resetForm2(formtwo)
{
	$('#'+formtwo).trigger("reset");
}


function resetForm3(formThree)
{
	$('#'+formThree).trigger("reset");
}

function fillName(val,divId,param)
{
	/*
	param is either 1 or 0
	1: for fetching name on both key and value place
	0: for fetching id on key and name on value place
	*/
	//in case of param 1 fetch dropdown both parameter as name only otherwise set it as id and name  
	var id = val;
	if(id == null || id == '-1')
	return;


	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/wfpm/client/fetchReferredName.action",
		data: "id="+id+"&param="+param, 
		success : function(data) {   
			$('#'+divId+' option').remove();
			$('#'+divId).append($("<option></option>").attr("value",-1).text('Select'));
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
$.subscribe('level1', function(event,data)
        {
          setTimeout(function(){ $("#leadgntion").fadeIn(); }, 10);
          setTimeout(function(){ $("#leadgntion").fadeOut(); }, 4000);
          $('select').find('option:first').attr('selected', 'selected');
          
});
$.subscribe('level2', function(event,data)
        {
          setTimeout(function(){ $("#leadeditionid").fadeIn(); }, 10);
          setTimeout(function(){ $("#leadeditionid").fadeOut(); }, 4000);
          $('select').find('option:first').attr('selected', 'selected');
          
});

$.subscribe('dd', function(event,data)
        {
          setTimeout(function(){ $("#pAssociate").fadeIn(); }, 10);
          setTimeout(function(){ $("#pAssociate").fadeOut(); }, 4000);
          $('select').find('option:first').attr('selected', 'selected');
        });
$.subscribe('form2', function(event,data)
        {
          setTimeout(function(){ $("#orglevel3").fadeIn(); }, 10);
          setTimeout(function(){ $("#orglevel3").fadeOut(); }, 4000);
          $('select').find('option:first').attr('selected', 'selected');
         
        });

</script>

<script type="text/javascript">
	function viewClient()
	{
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$.ajax({
	    type : "post",
	    url  : "view/Over2Cloud/wfpm/client/beforeClientView.action",
	    data : "isExistingClient=${isExistingClient}",
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
	<div class="clear"></div>
	<div class="list-icon">
	<!-- <div class="head">Add Prospective Client</div> -->
		<div class="head">Lead</div>
		<div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Edit</div>
	</div>

<div class="container_block">
<div style=" float:left; padding:10px 1%; width:98%;">

<!-- Lead basic data ---------------------------------------------------------------------------------------------------->
<sj:accordion id="accordion" autoHeight="false">
<sj:accordionItem title="Basic Details" id="one">
<s:form id="formone" name="formone" namespace="/view/Over2Cloud/WFPM/Lead" action="editLead" theme="simple" method="post" 
	enctype="multipart/form-data">
	<s:hidden name="id" value="%{id}" />
	
	<div style="float: left; border-color: black; background-color: rgb(255, 99, 71); color: white; width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
		<div id="errZone" style="float: left; margin-left: 7px"></div>
	</div>
	<br/>
	
	<div class="menubox">		
	<s:iterator value="masterViewProp" status="counter">
		<s:if test='%{colType == "T"}'>
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{headingName}"/>:</div>
			<div class="rightColumn1"><s:if test="%{mandatroy == true}"><span class="needed"></span></s:if>
			     <s:textfield name="%{colomnName}"  id="%{colomnName}" value="%{value}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
			</div>
			</div>
		</s:if>
    </s:iterator>
	
	<s:iterator value="masterViewProp" status="counter">
	<s:if test='%{colType == "D"}'>
		<s:if test="%{colomnName == 'sourceName'}">
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{headingName}"/>:</div>
			<div class="rightColumn1"><s:if test="%{mandatroy == true}"><span class="needed"></span></s:if>
			     <s:select 
						id="%{colomnName}" 
						name="%{colomnName}"
						list="sourceList"
						headerKey="-1" 
						headerValue="Select Source"
						value="%{value}"
						cssClass="select"
                        cssStyle="width:82%"
						>
					</s:select>
			</div>
			</div>	
		</s:if>
		<s:elseif test="%{colomnName == 'starRating'}">
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{headingName}"/>:</div>
			<div class="rightColumn1"><s:if test="%{mandatroy == true}"><span class="needed"></span></s:if>
			     <s:select
							id="%{colomnName}" 
							name="%{colomnName}" 
							list="#{'-1':'Select Rating'}" 
							list="#{'1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            headerKey="-1"
                            headerValue="Select Rating" 
							value="%{value}" 
                            cssClass="select"
                            cssStyle="width:82%"
                            >
				</s:select>
			</div>
			</div>	
		</s:elseif>
		<s:elseif test="%{colomnName == 'name'}">
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{headingName}"/>:</div>
			<div class="rightColumn1"><s:if test="%{mandatroy == true}"><span class="needed"></span></s:if>
			     <s:select
							id="%{colomnName}" 
							name="%{colomnName}" 
							list="locationList"
							headerKey="-1"
							headerValue="Select Location"
							value="%{value}" 
							cssClass="select"
                            cssStyle="width:82%">
				</s:select>
			</div>
			</div>	
		</s:elseif>
 		<s:elseif test="%{colomnName == 'industry'}">
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{headingName}"/>:</div>
			<div class="rightColumn1"><s:if test="%{mandatroy == true}"><span class="needed"></span></s:if>
			     <s:select 
			            id="industry" 
						name="%{colomnName}"
						list="industryList"
			            headerValue="Select Industry" 
			            value="%{value}"
	                    cssClass="select"
                       	cssStyle="width:82%"
                       	onchange="fillName(this.value,'subIndustry',0)"
			            >
				</s:select>
			</div>
			</div>	
		</s:elseif>
		<s:elseif test="%{colomnName == 'subIndustry'}">
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{headingName}"/>:</div>
			<div class="rightColumn1"><s:if test="%{mandatroy == true}"><span class="needed"></span></s:if>
			     <s:select 
			            id="subIndustry" 
						name="%{colomnName}"
						list="subIndustryList"
	                    headerKey="-1"
	                    headerValue="Select Sub-Industry" 
			            value="%{value}"
	                    cssClass="select"
                       	cssStyle="width:82%"
			            >
				</s:select>
			</div>
			</div>	
		</s:elseif>
	</s:if>
	
	<s:if test="%{#status.even}">
		<div class="clear"></div>
	</s:if>	

	</s:iterator>	
	</div>	

	<br>		
	<div class="clear"></div>
	<div class="fields">
	<div style="width: 100%; text-align: center; padding-bottom: 10px;">
		<sj:submit 
		     targets="leadresult" 
		     clearForm="true"
		     value="Edit" 
		     effect="highlight"
		     effectOptions="{ color : '#222222'}"
		     effectDuration="5000"
		     button="true"
		     onCompleteTopics="level1"
		     cssClass="submit" 
		     indicator="indicator2"
		     onBeforeTopics="validate"
	     />
	     <sj:a 
	     	name="Reset"  
			href="#" 
			cssClass="submit" 
			button="true"
			onclick="resetForm('formone');"
		>
		  	Reset
		</sj:a> 
	     <sj:a 
	     	name="Cancel"  
			href="#" 
			targets="result" 
			cssClass="submit" 
			indicator="indicator"
			button="true" 
			onclick="viewLead()" 
			style="margin-left:4px;"
		>
		  	Back
		</sj:a>
	</div>
	</div>
	<div class="clear"></div>
	<center>
		<sj:div id="leadgntion"  effect="fold">
                  <div id="leadresult"></div>
              </sj:div>
             </center>
	<br>
</s:form>
</sj:accordionItem>

<!-- Lead contact data --------------------------------------------------------------------------------------------------------->
<sj:accordionItem title="Contact Details" id="two">
<s:form id="formTwo" name="formone" namespace="/view/Over2Cloud/WFPM/Lead" action="editLeadContact" theme="simple" method="post" 
	enctype="multipart/form-data">
<s:iterator value="leadContactEditList" var="varLeadContactEditList">	
	<div class="menubox">
	<!-- textfield -->
	<s:iterator value="%{#varLeadContactEditList}" status="counter">
		<s:if test='%{colType == "T" && colomnName == "id"}'>
			<s:hidden name="%{colomnName}" value="%{value}" />
		</s:if>
		<s:elseif test='%{colType == "T" && colomnName != "id"}'>
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{headingName}"/>:</div>
			<div class="rightColumn1"><s:if test="%{mandatroy == true}"><span class="needed"></span></s:if>
			     <s:textfield name="%{colomnName}"  id="%{colomnName}" value="%{value}"  cssClass="textField" 
			     			placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
			</div>
			</div>
		</s:elseif>
			
   </s:iterator>
		<!-- dropdown -->
	<s:iterator value="%{#varLeadContactEditList}" status="counter">
	<s:if test='%{colType == "D"}'>
		<s:if test="%{colomnName == 'department'}">
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{headingName}"/>:</div>
			<div class="rightColumn1"><s:if test="%{mandatroy == true}"><span class="needed"></span></s:if>
			     <s:select 
						id="%{colomnName}" 
						name="%{colomnName}"
						list="deptlist"
						headerKey="-1" 
						headerValue="Select Source"
						value="%{value}"
						cssClass="select"
                        cssStyle="width:82%"
						>
					</s:select>
			</div>
			</div>	
		</s:if>
		<s:elseif test="%{colomnName == 'degreeOfInfluence'}">
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{headingName}"/>:</div>
			<div class="rightColumn1"><s:if test="%{mandatroy == true}"><span class="needed"></span></s:if>
			     <s:select
							id="%{colomnName}" 
							name="%{colomnName}" 
							list="#{'-1':'Select Rating'}" 
							list="#{'1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            headerKey="-1"
                            headerValue="Influence" 
                            cssClass="select"
                            cssStyle="width:82%"
                            >
				</s:select>
			</div>
			</div>	
		</s:elseif>	
	</s:if>
	
	</s:iterator>
		<!-- datepicker -->	
	<s:iterator value="%{#varLeadContactEditList}" status="counter">
	<s:if test='%{colType == "Time"}'>
		<s:if test="%{colomnName == 'anniversary'}">
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{headingName}"/>:</div>
			<div class="rightColumn1"><s:if test="%{mandatroy == true}"><span class="needed"></span></s:if>
			     <sj:datepicker 
		     				name="%{colomnName}" 
		     				id="%{anniversaryId}" 
							value ="%{value}"
		     				changeMonth="true" 
		     				changeYear="true" 
		     				cssClass="textField"
                            cssStyle="width:82%"   
		     				yearRange="1890:2020" 
		     				showOn="focus" displayFormat="dd-mm-yy" placeholder="Select Date"/>
			</div>
			</div>	
		</s:if>
		<s:elseif test="%{colomnName == 'birthday'}">
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{headingName}"/>:</div>
			<div class="rightColumn1"><s:if test="%{mandatroy == true}"><span class="needed"></span></s:if>
			     <sj:datepicker 
		     				name="%{colomnName}" 
		     				id="%{birthdayId}" 
							value ="%{value}"
		     				changeMonth="true" 
		     				changeYear="true" 
		     				cssClass="textField"
                            cssStyle="width:82%"   
		     				yearRange="1890:2020" 
		     				showOn="focus" displayFormat="dd-mm-yy" placeholder="Select Date"/>
			</div>
			</div>	
		</s:elseif>	
	</s:if>
	
	</s:iterator>
		
	</div>
</s:iterator>
	<br>		
	<div class="clear"></div>
	<div class="fields">
	<div style="width: 100%; text-align: center; padding-bottom: 10px;">
		<sj:submit 
		     targets="leadresultid" 
		     clearForm="true"
		     value="Edit" 
		     effect="highlight"
		     effectOptions="{ color : '#222222'}"
		     effectDuration="5000"
		     button="true"
		     onCompleteTopics="level2"
		     cssClass="submit" 
		     indicator="indicator2"
		     onBeforeTopics="validate"
	     />
	     <sj:a 
	     	name="Reset"  
			href="#" 
			cssClass="submit" 
			button="true"
			onclick="resetForm('formone');"
		>
		  	Reset
		</sj:a> 
	     <sj:a 
	     	name="Cancel"  
			href="#" 
			targets="result" 
			cssClass="submit" 
			indicator="indicator"
			button="true" 
			onclick="viewLead()" 
			style="margin-left:4px;"
		>
		  	Back
		</sj:a>
	</div>
	</div>
	<div class="clear"></div>
	<center>
		<sj:div id="leadeditionid"  effect="fold">
                  <div id="leadresultid"></div>
              </sj:div>
             </center>
	<br>

</s:form>
</sj:accordionItem>
</sj:accordion>
</div>
</div>
<SCRIPT type="text/javascript">
/*fillName('Client','clientNa',0);
fillName('Client','clientN',0);*/
</SCRIPT>
</body>
</html>