<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/krLibrary/tagJs/krLibrary.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/krLibrary/tagJs/KRValidation.js"/>"></script>
<link href="js/multiselect/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>

<script type="text/javascript">
$.subscribe('completeData22',function(event,data)
{
	setTimeout(function(){ $("#foldeffect10").fadeIn(); }, 10);
  	setTimeout(function(){ $("#foldeffect10").fadeOut(); }, 4000);
  	 $('select').find('option:first').attr('selected', 'selected');
  	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/KRLibraryOver2Cloud/beforeKRLibraryAdd.action",
	    success : function(data) 
	    {
			$("#"+"data_part").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
});
</script>
<script type="text/javascript">
$(document).ready(function()
{
	$("#dept").multiselect({
		   show: ["bounce", 200],
		   hide: ["explode", 1000]
		});
	
	$("#doc_name12222").multiselect({
		   show: ["bounce", 200],
		   hide: ["explode", 1000]
		});
	
	$("#emp_name1").multiselect({
		   show: ["bounce", 200],
		   hide: ["explode", 1000]
		});
});
</script>
<SCRIPT type="text/javascript">
	$.subscribe('close', function(event,data)
	{
	  document.getElementById("indicator2").style.display="none";
	});
		$('#browse').bind('change', function()
	   {
	     var iSize = ($("#browse")[0].files[0].size / 1024);
	     
	     if (iSize / 1024 > 1)
	     {
	        if (((iSize / 1024) / 1024) > 1)
	        {
	            iSize = (Math.round(((iSize / 1024) / 1024) * 100) / 100);
	            $("#krbrowse").html("File Size Is "+ iSize + "Gb");
	        }
	        else
	        {
	            iSize = (Math.round((iSize / 1024) * 100) / 100)
	            $("#krbrowse").html("File Size Is "+ iSize + "Mb");
	        }
	     }
	     else
	     {
	        iSize = (Math.round(iSize * 100) / 100)
	        $("#krbrowse").html("File Size Is "+ iSize  + "kb");
	     }  
	     if(this.files[0].size<=21316300)
			{
			a=true;
			
			}
		else
			{
			a=false;
			}
	  });
	function viewDept(status)
	{
		var shareStatus=$("#"+status).val();
		$("#data_part").html("<br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/>Loading...</center>");
		var conP = "<%=request.getContextPath()%>";
		$.ajax({
		    type : "post",
		    url : conP+"/view/Over2Cloud/KRLibraryOver2Cloud/beforeKrViewHeaderPage.action?shareStatus="+shareStatus,
		    success : function(data) 
		    {
				$("#"+"data_part").html(data);
		    },
		    error: function() 
		    {
	            alert("error");
	        }
		 });
	}

	selectRadio();
</script>
</head>
<div class="list-icon">
	 <div class="head">KR</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Share</div> 
</div> 
<div class="clear"></div>
<div style=" float:left; padding:5px 0%; width:100%;">
	<div class="border" style="overflow-x:hidden;">
			
	<s:form id="formone" name="formone" namespace="/view/Over2Cloud/KRLibraryOver2Cloud" action="krShareAdd" theme="simple" method="post" enctype="multipart/form-data">
	  <center>
		<div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;"><div id="errZone" style="float:center; margin-left: 7px">
		</div>
		</div>
	  </center>
     	<s:hidden id="shareStatus" value="%{shareStatus}"/>
		<div class="newColumn">
						<div class="leftColumn1">From Department:</div>
						<div class="rightColumn1"><span id="mandatoryFields" class="pIds" style="display: none; ">depart#From Department#D#a#,</span>
							<span class="needed"></span>
						 <s:select 
	                  id="depart"
					  list="contactTypeList" 
					  headerKey="-1"
					  headerValue="Select Department" 
					  cssClass="textField"
					  cssStyle="width:82%"
				      onchange="fetchGroup(this.value,'group_name')"
						>
					</s:select></div>
		</div>
		<s:iterator value="shareDropDown" status="counter" begin="0" end="2">
			<span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
					<s:if test="%{key=='group_name'}">
						<div class="newColumn">
						<div class="leftColumn1"><s:property value="%{value}" />:</div>
						<div class="rightColumn1">
						 <s:if test="%{mandatory}"><span class="needed"></span></s:if>
						 <s:select 
		                  id="%{key}"
						  list="{'No Data'}" 
						  headerKey="-1"
						  headerValue="Select Group" 
						  cssClass="select"
						  cssStyle="width:82%"
					      onchange="getSubGrp(this.value,'sub_group_name');">
					  </s:select></div>
						</div>
					</s:if>
					<s:elseif test="%{key=='sub_group_name'}">
						<div class="newColumn">
						<div class="leftColumn1"><s:property value="%{value}" />:</div>
						<div class="rightColumn1"> <s:if test="%{mandatory}"><span class="needed"></span></s:if>
						  <s:select
						 		id="%{key}" 
						 		list="#{'-1':'Select Sub Group'}"
								cssClass="select"
					            cssStyle="width:82%"
					            onchange="getDocName(this.value,'docDiv','depart')"
					            
					            >
					      </s:select></div>
						</div>
					</s:elseif>
					
					<s:elseif test="%{key=='doc_name'}">
					<div id="docDiv">
						<div class="newColumn">
						<div class="leftColumn1"><s:property value="%{value}" />:</div>
						<div class="rightColumn1"> <s:if test="%{mandatory}"><span class="needed"></span></s:if>
						  <s:select
						id="%{key}12222"
						name="%{key}"
						list="{'No Data'}"
						cssClass="select"
					    cssStyle="width:28%"
					    multiple="true"
					    
					    ></s:select></div>
						</div>
					</div>	
					</s:elseif>
				</s:iterator>
	
	           <div class="clear"></div>
	<div class="secHead"></div>
				<s:iterator value="shareDropDown" status="counter" begin="3" end="4">	
					
					
					<s:if test="%{key=='sub_type_id'}">
					<span id="mandatoryFields" class="pIds" style="display: none; ">dept#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
						<div class="newColumn">
						<div class="leftColumn1"><s:property value="%{value}" />:</div>
						<div class="rightColumn1"> <s:if test="%{mandatory}"><span class="needed"></span></s:if>
						  <s:select
							id="dept" 
							name="%{key}"
							list="contactTypeList"
							cssClass="select"
                            multiple="true"	
                            cssStyle="width:28%"
                            onchange="getEmpName(this.value,'empDivData')"					    
						    >
					    </s:select>
					    </div>
						</div>
					</s:if>
					
					<s:elseif test="%{key=='emp_name'}">
					<span id="mandatoryFields" class="pIds" style="display: none; ">emp_name1#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
					<div id="empDivData">
						<div class="newColumn">
						<div class="leftColumn1"><s:property value="%{value}" />:</div>
						<div class="rightColumn1"> <s:if test="%{mandatory}"><span class="needed"></span></s:if>
						  <s:select
						id="%{key}1" 
						name="%{key}"
						list="{'No Data'}"
						cssClass="select"
						name="%{key}"
						multiple="true"
					    cssStyle="width:28%">
					    </s:select></div>
						</div>
					</div>
					</s:elseif>
					
				</s:iterator>
				 <div class="clear"></div>
		<div class="secHead"></div>		
				<s:iterator value="shareDropDown" status="counter" begin="5" end="5">
					<span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
					<s:if test="%{key=='access_type'}">
						<div class="newColumn">
						<div class="leftColumn1"><s:property value="%{value}" />:</div>
						<div class="rightColumn1"> <s:if test="%{mandatory}"><span class="needed"></span></s:if>
						  <s:select
						id="%{key}" name="%{key}"
						list="#{'Readable':'Readable','Editable':'Editable'}"
						headerKey="-1" 
						headerValue="Select Access Type"
						cssClass="textField"
					    cssStyle="width:82%">
					    </s:select>
					    </div>
						</div>
					</s:if>
				</s:iterator>
				
					<s:iterator value="shareDate" status="counter" >
				    <s:if test="%{key=='due_share_date'}">
				    <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
				   <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                           <sj:datepicker name="%{key}" id="due_share_date" readonly="true" minDate="0" changeMonth="true" changeYear="true" yearRange="2013:2020" showOn="focus" displayFormat="dd-mm-yy" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" placeholder="Select Due Date"/>
	                           </div>
	                 </div>
				   </s:if>
				   </s:iterator>
				   
			     <s:iterator value="shareRadioButton" status="counter">
			     	<span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
					<s:if test="%{key=='action_req'}">
						<div class="newColumn">
						<div class="leftColumn1"><s:property value="%{value}" />:</div>
						<div class="rightColumn1"><s:if test="%{mandatory}">
							<span class="needed"></span>
						</s:if> <s:radio name="%{key}" id="%{key}" 
							 list="#{'Yes':'Yes','No':'No'}" value="N" onchange="showHide(this.value,'actionRequiredDiv')"/></div>
						</div>
					</s:if>
				</s:iterator>
				<div class="clear"></div>
				
				
				<div id="actionRequiredDiv" style="display: none;">
				   <s:iterator value="shareDropDown" status="counter" begin="6" end="7">
				   
					<s:if test="%{key=='rating_required'}">
						<div class="newColumn">
						<div class="leftColumn1"><s:property value="%{value}" />:</div>
						<div class="rightColumn1"> <s:if test="%{mandatory}"><span class="needed"></span></s:if>
						  <s:select
						id="%{key}" name="%{key}"
						 list="#{'Yes':'Yes','No':'No'}"
						headerKey="-1" 
						headerValue="Select Rating"
						cssClass="textField"
					    cssStyle="width:82%">
					    </s:select>
					    </div>
						</div>
					</s:if>
					<s:if test="%{key=='comments_required'}">
						<div class="newColumn">
						<div class="leftColumn1"><s:property value="%{value}" />:</div>
						<div class="rightColumn1"> <s:if test="%{mandatory}"><span class="needed"></span></s:if>
						  <s:select
						id="%{key}" name="%{key}"
						 list="#{'Yes':'Yes','No':'No'}"
						headerKey="-1" 
						headerValue="Select Comment"
						cssClass="textField"
					    cssStyle="width:82%">
					    </s:select>
					    </div>
						</div>
					
					</s:if>
				</s:iterator>
					<s:iterator value="shareDate" status="counter" >
				    <s:if test="%{key=='due_date'}">
				    <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
				   <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                           <sj:datepicker name="%{key}" id="actiondate" readonly="true" minDate="0" changeMonth="true" changeYear="true" yearRange="2013:2020" showOn="focus" displayFormat="dd-mm-yy" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" placeholder="Select Action Date"/>
	                           </div>
	                 </div>
				   </s:if>
				   </s:iterator>
				    </div>
				    
		        <s:iterator value="shareDropDown" status="counter" begin="8" end="9">
					<s:if test="%{key=='frequency'}">
					<span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
						<div class="newColumn">
						<div class="leftColumn1"><s:property value="%{value}" />:</div>
						<div class="rightColumn1"> <s:if test="%{mandatory}"><span class="needed"></span></s:if>
						  <s:select
						id="%{key}" 
						name="%{key}"
						list="#{'OT':'One-Time','D':'Daily','W':'Weekly','M':'Monthly','BM':'Bi-Monthly','Q':'Quaterly','HY':'Half Yearly','Y':'Yearly'}"
						headerKey="-1" 
						headerValue="Select Frequency"
						cssClass="textField"
					    cssStyle="width:82%">
					    </s:select>
					    </div>
						</div>
					</s:if>
				</s:iterator>
		     	    <div class="newColumn">
				       <div class="leftColumn1">Acknowledgement:&nbsp;</div>
				       <div style="display: block;" id="self">
					       <div style="margin-left: -4px; margin-top: 11px;">Self:</div>
					       <div class="rightColumn1" style="margin-left: 194px; margin-top: -37px;">
						       Mail <s:checkbox name="selfmail" id="mail" />
						       SMS <s:checkbox name="selfsms" id="sms"/>
					       </div>
				       </div>
				       <div style="display: block;" id="other">
					       <div class="leftColumn1" style="margin-left: 33px; margin-top: -12px;">Other:</div>
					       <div class="rightColumn1" style="margin-left: 194px; margin-top: -37px;">
						       Mail <s:checkbox name="othermail" id="mail" />
						       SMS <s:checkbox name="othersms" id="sms"/>
					       </div>
				       </div>
	                 </div>
		     
			<div class="clear"></div>
				<div class="fields">
				<div style="width: 100%; text-align: center; padding-bottom: 10px;">
				<sj:submit 
								     targets="Result7" 
								     clearForm="true"
								     value="  Add  " 
								     effect="highlight"
								     effectOptions="{ color : '#222222'}"
								     effectDuration="5000"
								     button="true"
								     onCompleteTopics="completeData22"
								     cssClass="button"
								     indicator="indicator2"
								     onBeforeTopics="validate"
							     />
							      <sj:a 
						button="true" href="#"
						onclick="resetTaskName('formone');"
						>
						Reset
					</sj:a>
							     <sj:a 
						button="true" href="#"
						onclick="viewDept('shareStatus');"
						>
						
						Back
					</sj:a>
					 <sj:div id="foldeffect10"  effect="fold">
                    <div id="Result7"></div>
               </sj:div>

				</div>
				<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
				</div>
   </s:form>
</div>
</div>
</html>