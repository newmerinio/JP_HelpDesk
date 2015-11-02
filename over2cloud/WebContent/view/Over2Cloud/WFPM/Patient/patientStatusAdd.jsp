<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
		<%@taglib prefix="s" uri="/struts-tags" %>
	    <%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
	    <%@ taglib prefix="sjg"  uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!--<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" /> 
--><s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<link href="js/multiselect/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>
<title>Insert title here</title>
	<style type="text/css">
	
		.textField
		{
			height:18px;
		}
		span.needed
		{
			height:22px;	
		}
		.select
		{
			height:22px;	
		}
	
	</style>
	
	<script type="text/javascript">
var c=0;
	 $(document).ready(function()
			 {
			 	/*
				 $("#empL1").multiselect({
			 	   show: ["", 200],
			 	   hide: ["explode", 1000]
			 	});
			 	$("#empL2").multiselect({
			 	   show: ["", 200],
			 	   hide: ["explode", 1000]
			 	});
			 	$("#empL3").multiselect({
			 	   show: ["", 200],
			 	   hide: ["explode", 1000]
			 	});
			 	$("#empL4").multiselect({
			 	   show: ["", 200],
			 	   hide: ["explode", 100]
			 	});
			*/
			 	$("#relationship_type").multiselect({
				 	   show: ["", 200],
				 	   hide: ["explode", 100]
				 	});
				 $("#rh_sub_type").multiselect({
					 	   show: ["", 200],
					 	   hide: ["explode", 1000]
				});	 

				 //for(var i=100;i<110;i++){
					//for(var j=1;j<5;j++){
					//	console.log("############empL"+j+''+i);
					//	 $("#empL"+j+''+i).multiselect({
						//	 
						 ///	   show: ["", 200],
						 	//   hide: ["explode", 100]
							//});	 
					//	}
				//}
				 
			 });
	 
		$.subscribe('level1', function(event,data)
		        {
		          setTimeout(function(){ $("#patientaddition").fadeIn(); }, 10);
		          setTimeout(function(){ $("#patientaddition").fadeOut(); }, 4000);
		          
		});
		 function resetForm(formId) 
		 {
			 $('#'+formId).trigger("reset");
		 }
		 function viewClient2222()
			{
			 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
			 $.ajax({
					type:"post",
					url : "view/Over2Cloud/WFPM/Patient/beforeStatusAdd.action",
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

			function needEsc(){
				var data=$("#escalation").val();
				if(data=="Yes"){
				$("#l2").fadeIn();
				
				}
				if(data=="No"){
					$("#l2").fadeOut();
				}
				
			}

			

			function needEsc2(){

				var data=$("#empL1").val();
				if(data!="-1"){
				$("#l3").fadeIn();
				
				}
				if(data=="-1"){
					$("#l3").fadeOut();
				}

				
			}

		

			function needEsc3(){

				var data=$("#empL2").val();
				if(data!="-1"){
				$("#l4").fadeIn();
				
				}
				if(data=="-1"){
					$("#l4").fadeOut();
				}

				
			}

			function needEsc4(){

				var data=$("#empL3").val();
				if(data!="-1"){
				$("#l5").fadeIn();
				
				}
				if(data=="-1"){
					$("#l5").fadeOut();
				}

				
			}

			function changeinNextEsc(data, div, sId, sName, nId, nName){
					alert(div +"sadasd ");
					var l1 = $("#empL1").val();
					var l2 = $("#empL2").val();
					var l3 = $("#empL3").val();
					var l4= $("#empL4").val();
									
					$.ajax({
						type :"post",
						url :"view/Over2Cloud/WFPM/Patient/nxxtLevelEscnext.action?l1="+l1+"&l2="+l2+"&l3="+l3+"&l4="+l4+"&div="+div+"&sId="+sId+"&sName="+sName+"&nId="+nId+"&nName="+nName,
						success : function(data)
						{
							$('#l3 #'+div).html(data);
					    },
					    error : function () {
							alert("Somthing is wrong to get get Next excalation Level");
						}
					});

			/*	if(c==0){
					var matches = [];
					var searchEles = document.getElementById("wwctrl_empL1100").children;
					console.log(searchEles);
					
					    $(searchEles[1]).remove();
					          
					
				
					alert(matches.length);
					c++;
					} */
					
					
			}

			
			function fetchRhSubType(div)
			{
				var subType= $("#relationship_type").val();
				$.ajax({
					type :"post",
					url :"view/Over2Cloud/WFPM/Patient/fetchRhSubType.action?id="+subType,
					success : function(data)
					{
						$('#'+div).html(data);
				    },
				    error : function () {
						alert("Somthing is wrong to get relationship sub type.");
					}
				});
			}

			 function changeDivView(value,divId)
			 {
					if(value=='2')
					{
						$('#'+divId).hide();
					}	
					else
					{
						$('#'+divId).show();
					}	
			}
			 
			 function getAllActivity(stage)
			 {
					var subType= $("#rh_sub_type").val();
					$.ajax({
						type :"post",
						url :"view/Over2Cloud/WFPM/Patient/fetchAllActivity.action?id="+subType+"&rowid="+stage,
						success : function(data)
						{
						for(var i = 99;i<110;i++){
							$('#status'+i+' option').remove();
							$('#status'+i).append($("<option></option>").attr("value",-1).text("--Select Activity--"));
					    }
							$(data).each(function(index)
							{
					    		 for(var i = 99;i<110;i++){
					    			 $('#status'+i).append($("<option></option>").attr("value",this.ID).text(this.name));
					 				}
							});
					    },
					    error : function () {
							alert("Somthing is wrong to get relationship sub type.");
						}
					});
			 }
	</script>
<SCRIPT type="text/javascript">

function showSelectedDivs(v1){
	$("#"+v1).show(200);
}
function hideSelectedDivs(v1){
	$("#"+v1).hide(200);
}

function showHideMyDiv(obj,div){
	var val= obj.value;
	if(val == '2' ){
			showSelectedDivs(div);
		}else{
			hideSelectedDivs(div);
			for(var i = 99;i<110;i++){
				hideSelectedDivs("timeGapDiv"+i);
				}
			}
}

function showHideMyDiv2(obj,div){
	
	var val= obj.value;
	if(val == '1' ){
			for(var i = 99;i<110;i++){
				showSelectedDivs(div+i);
			}
		}else{
			for(var i = 99;i<110;i++){
				hideSelectedDivs(div+i);
			}
		}
}

function needMyMoreEsc(div){
	showSelectedDivs(div);
}

function needMyEsc(obj,div){
	var val= obj.value;
	alert(div);
	if(val == 'Yes' ){
				showSelectedDivs(div);
	}else{
				hideSelectedDivs(div);
		}
}
function changeinMyNextEsc(data, div, sId, sName, nId, nName,val)
{
//	alert(div+":"+sId+":"+nId);
	var l1 = $("#empL1"+val).val();
	
	var l2 = $("#empL2"+val).val();
	var l3 = $("#empL3"+val).val();
	var l4= $("#empL4"+val).val();
	console.log(div+":"+sId+":"+nId);
	$.ajax({
		type :"post",
		url :"view/Over2Cloud/WFPM/Patient/nxxMytLevelEscnext.action",
		data:"l1="+l1+"&l2="+l2+"&l3="+l3+"&l4="+l4+"&div="+div+"&sId="+sId+"&sName="+sName+"&nId="+nId+"&nName="+nName+"&status=other",
		success : function(data)
		{
				alert('mylaert ' + JSON.stringify(data));
			$('#'+sId +' option').remove();
		//	$('#'+sId).append($("<option></option>").attr("value",-1).text("--Select Employee--"));
	    	$(data).each(function(index)
			{
	    		 $('#'+sId).append($("<option></option>").attr("value",this.ID).text(this.name));
			});
	    },
	    error : function () {
			alert("Somthing is wrong to get relationship sub type.");
		}
	});

	/*
	console.log(l1);
	console.log(l2);
	console.log(l3);
	console.log(l4);
	
	
	$.ajax({
		type :"post",
		url :"view/Over2Cloud/WFPM/Patient/nxxtLevelEscnext.action",
		data:"l1="+l1+"&l2="+l2+"&l3="+l3+"&l4="+l4+"&div="+div+"&sId="+sId+"&sName="+sName+"&nId="+nId+"&nName="+nName+"&status=other",
		success : function(data)
		{
			$('#'+div).html(data);
	    },
	    error : function () {
			alert("Somthing is wrong to get get Next excalation Level");
		}
	}); */
}

</SCRIPT>

	
</head>
<body>
	<div class="clear"></div>
	<div class="list-icon">
	<!-- <div class="head">Add Prospective Client</div> -->
	<div class="head">Configure Activity Escalation</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:60%; float: left;"></div><div class="head">Add</div>
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
	<s:form id="formone" name="formone" namespace="/view/Over2Cloud/WFPM/Patient" action="addPatientStatus" theme="css_xhtml"  method="post"enctype="multipart/form-data">
    <div class="menubox">
   	<s:iterator value="clientDropDown" status="counter">
	    <s:if test="%{mandatory}">
	       <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	    </s:if> 	
	</s:iterator> 
			<div class="newColumn">
			<span id="form2MandatoryFields" class="qIds" style="display: none; ">relationship_type#Relationship Type#D#,</span>
			<div class="leftColumn1">Relationship Type:</div>
			<div class="rightColumn1"><span class="needed"></span>
			<s:select 
		                              id="relationship_type"
		                              name="relationship_type" 
		                              list="rhTypeMap"
		                              cssClass="select"
		                              multiple="true"
		                              cssStyle="width:24.5%" 
		                              onchange="fetchRhSubType('rh_sub_type_div')"
		                              >
		                   
		                  </s:select>
			</div>
			</div>
			<div class="newColumn">
			<span id="form2MandatoryFields" class="qIds" style="display: none; ">rh_sub_type#Relationship Sub Type#D#,</span>
			<div class="leftColumn1">Relationship Sub Type:</div>
			<div class="rightColumn1"><span class="needed"></span>
				<div id="rh_sub_type_div">	
					<s:select 
		                              id="rh_sub_type"
		                              name="rh_sub_type" 
		                              list="{}"
		                              cssClass="select"
		                              cssStyle="width:24.5%"
		                              >
		                  	</s:select>
		         </div>         	
			</div>
			</div>
			<div class="newColumn">
			<span id="form2MandatoryFields" class="qIds" style="display: none; ">stage#Stage#D#,</span>
			<div class="leftColumn1">Stage:</div>
			<div class="rightColumn1"><span class="needed"></span>
			<s:select 
		                              id="stage"
		                              name="stage" 
		                              list="stageMap"
		                              headerKey="-1"
		                              headerValue="Select Stage"
		                              cssClass="select"
		                              cssStyle="width:82%"
		                              onchange="getAllActivity(this.value)"
		                              >
		                  	</s:select>
			</div>
			</div>
		<div class="newColumn">
			<div class="leftColumn1">Offeringwise:</div>
			<div class="rightColumn1" style="padding: 3px;">
					<s:radio label="ofrWise" name="ofrWise" cssClass="radio" list="#{'1':'Yes','2':'No'}" value="1" theme="simple"  onchange="changeDivView(this.value,'ofrYesDiv')"/>
			</div>
		</div>
		<div class="clear"></div>
		<div id="ofrYesDiv">
         <!-- Drop down offering -->
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
		                              name="offeringname" 
		                              list="#{'-1':'Select Offering'}"
		                               cssClass="select"
		                              cssStyle="width:82%"
		                              onchange="fetchLevelData(this,'subofferingname','2')"
		                              >
		                  	</s:select>
			</div>
			</div>
			</s:if>
			<s:if test="OLevel3">
			<span id="form2MandatoryFields" class="qIds" style="display: none; ">subofferingname#<s:property value="%{OLevel3LevelName}"/>#D#,</span>
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{OLevel3LevelName}"/>:</div>
			<div class="rightColumn1">
			<span class="needed"></span>
			<s:select 
		                              id="subofferingname"
		                              name="offering_name" 
		                              list="#{'-1':'Select Sub Offering'}"
		                              cssClass="select"
		                              cssStyle="width:82%"
		                              onchange="fetchLevelData(this,'variantname','3');checkSuboffering(this.value,clientN.value);"
		                              >
		                  	</s:select>
			</div>
			</div>
			</s:if>
    </div>
    	<div class="newColumn">
			<div class="leftColumn1">Activity&nbsp;Type:</div>
			<div class="rightColumn1">
					<s:radio label="activityType" name="activityType" cssClass="radio" list="#{'1':'Random','2':'Sequence'}" value="1" theme="simple"  onchange="changeDivView(this.value,'docDiv1','buddyDiv1'); showHideMyDiv(this,'seqDiv');"/>
			</div>
		</div>
		
		<div id="seqDiv" style="display: none;">
			<div class="newColumn">
			<div class="leftColumn1">Sequence Option:</div>
			<div class="rightColumn1">
			<span class="needed"></span>
			<s:select 
		                              id="seqOption"
		                              name="seqOption" 
		                              list="#{'1':'Mandatory','2':'Skipping'}"
		                              cssClass="select"
		                              cssStyle="width:82%"
		                              value="2"
		                              onchange="showHideMyDiv2(this,'timeGapDiv');"
		                              >
		                  	</s:select>
			</div>
			</div>
		
		</div>
		
		
		<div class="clear"></div>
    	<div class="newColumn">
    		<span id="form2MandatoryFields" class="qIds" style="display: none; ">status#Status#D#,</span>
			<div class="leftColumn1">Activity:</div>
			<div class="rightColumn1"><span class="needed"></span>
							<s:select 
		                              id="status99"
		                              name="status" 
		                              list="{}"
		                              headerKey="-1"
		                              headerValue="Select Activity"
		                              cssClass="select"
		                              cssStyle="width:82%"
		                              >
		                  	</s:select>
				</div>
		</div>
		
		<div class="newColumn">
			<div class="leftColumn1">Activity Code:</div>
			<div class="rightColumn1">
				<s:textfield name="activity_code"  id="activityCode"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
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
	
			<div class="newColumn">
			<div class="leftColumn1">Questionnaire:</div>
			<div class="rightColumn1">
			<s:select 
		                              id="questionnaire"
		                              name="questionnaire" 
		                              list="#{'-1':'Select Questionnaire','A':'A', 'B':'B', 'C':'C', 'D':'D', 'E':'E'}"
		                               cssClass="select"
		                              cssStyle="width:82%"
		                              >
		                  	</s:select>
			</div>
			</div>
			
			<span id="form2MandatoryFields" class="qIds" style="display: none; ">Escalation#<s:property value="%{OLevel2LevelName}"/>#D#,</span>
			<div class="newColumn">
			<div class="leftColumn1">Escalation:</div>
			<div class="rightColumn1">
			<span class="needed"></span>
			<s:select 
		                              id="escalation"
		                              name="escalation" 
		                              list="#{ 'No':'No','Yes':'Yes'}"
		                               cssClass="select"
		                              cssStyle="width:82%"
		                              onchange="needEsc()"
		                              >
		                  	</s:select>
			</div>
			</div>
			
			
			<div classs="clear" ></div>
			<div id = "l2" style="display: none;">
			
			<span id="form2MandatoryFields" class="qIds" style="display: none; ">Employee#empL1#D#,</span>
			<div class="newColumn">
			<div class="leftColumn1">Employee:</div>
			<div class="rightColumn1">
			<span class="needed"></span>
			<s:select 
								id="empL199"
		                        name="l2_99" 
                              	list="escL2Emp"
                              	cssClass	="select"
								cssStyle	="width:82%;height:40%"
                              	multiple	="true"
                              	onchange="changeinMyNextEsc(this.value, 'l3esc' ,'empL299', 'l3_99', 'empL3', 'l4_99','99')"
		                              >
		                  	</s:select>
			</div>
			</div>
			
			<div class="newColumn">
          <div class="leftColumn1">L&nbsp;2&nbsp;TAT&nbsp;After:&nbsp;</div>
          <div class="rightColumn1">
	  <sj:datepicker id="l2EscDuration" name="tat2" readonly="true" onchange="needEsc2();" placeholder="Enter Time" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssClass="textField"/>
          </div>
          </div>
			
			</div>

			<div class="clear" ></div>
			<div id = "l3" style="display: none;">
			
			<div class="newColumn">
			<div class="leftColumn1">Employee:</div>
			<div class="rightColumn1">
			<div id="l3esc">
							<s:select 
		                              id="empL299"
		                              name="l3_99" 
		                              list="escL3Emp"
		                              cssClass="select"
		                              cssStyle	="width:82%;height:40%"
                              	multiple	="true"
                              	onchange="changeinMyNextEsc(this.value, 'l4esc' ,'empL399', 'l4_99', 'empL4', 'l5_99','99')"
		                              >
		                  	</s:select>
		                  	
		                  	</div>
			</div>
			</div>
			
			
			<div class="newColumn">
          <div class="leftColumn1">L&nbsp;3&nbsp;TAT&nbsp;After:&nbsp;</div>
          <div class="rightColumn1">
	  <sj:datepicker id="l3EscDuration" name="tat3" readonly="true" onchange="needEsc3();" placeholder="Enter Time" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssClass="textField"/>
          </div>
          </div>
			
			
			</div>

<div class="clear" ></div>
			<div id = "l4" style="display: none;">
			
			<div class="newColumn">
			<div class="leftColumn1">Employee:</div>
			<div class="rightColumn1">
			<div id="l4esc">
			<s:select 
		                              id="empL399"
		                              name="l4_99" 
		                              list="escL4Emp"
		                              cssClass="select"
		                              cssStyle	="width:82%;height:40%"
                              		multiple	="true"
                              		onchange="changeinMyNextEsc(this.value, 'l5esc' ,'empL499', 'l5_99', 'empL4', 'l5','99')"
		                              >
		                  	</s:select>
		                  	</div>
			</div>
			</div>
			
			
			<div class="newColumn">
          <div class="leftColumn1">L&nbsp;4&nbsp;TAT&nbsp;After:&nbsp;</div>
          <div class="rightColumn1">
	  <sj:datepicker id="l4EscDuration" name="tat4" readonly="true" onchange="needEsc4();" placeholder="Enter Time" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssClass="textField"/>
          </div>
          </div>
			
			
			</div>
			
			<div class="clear" ></div>
	<div id = "l5" style="display: none;">
			<div class="newColumn">
			<div class="leftColumn1">Employee:</div>
			<div class="rightColumn1">
			<div id="l5esc">
			<s:select 
		                              id="empL499"
		                              name="l5_99" 
		                              list="escL5Emp"
		                              cssClass="select"
		                              cssStyle	="width:82%;height:40%"
                              	   	multiple	="true"
		                              >
		                  	</s:select>
		                  	</div>
			</div>
			</div>
			
		<div class="newColumn">
          <div class="leftColumn1">L&nbsp;5&nbsp;TAT&nbsp;After:&nbsp;</div>
          <div class="rightColumn1">
	  			<sj:datepicker id="l5EscDuration" name="tat5" readonly="true"  placeholder="Enter Time" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssClass="textField"/>
          </div>
         </div>
	</div>
	
	
			<div id="timeGapDiv99" style="display: none;">
			<div class="newColumn">
			<div class="leftColumn1">Time Gap from above activity:</div>
			<div class="rightColumn1">
			
			<s:textfield 
		                              id="time_gap"
		                              name="time_gap" 
		                              list="escL5Emp"
		                              cssClass="textField"
		                            cssStyle="margin:0px 0px 10px 0px;"
		                            placeholder="Enter Days"
				                              >
		                  	</s:textfield>
		                  	
			</div>
			</div>
		</div>
		<sj:a value="+" onclick="adddiv('100')" button="true" cssClass="submit" cssStyle="margin-right: 43px; margin-top: -30px; float: right;">+</sj:a>
	<div class="clear"></div>
	
	<!-- + +  + +  + +  + +  + +  + +  + +  + +  + +  + +  + +  + +  + +  + +  + +  + +  + +  + +  + +  + +  + +  + +  + + -->
	<div id="extraDiv">
                 <s:iterator begin="100" end="104" var="typeIndx">
                        <div class="clear"></div>
                         <div id="<s:property value="%{#typeIndx}"/>" style="display: none ; width: 95%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px;
    -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10);
    padding: 1%; overflow: auto; "  class="container_block" >
                             
     <div class="newColumn">
    		<span id="form2MandatoryFields" class="qIds" style="display: none; ">status#Status#D#,</span>
			<div class="leftColumn1">Activity:</div>
			<div class="rightColumn1"><span class="needed"></span>
							<s:select 
		                              id="status%{#typeIndx}"
		                              name="status" 
		                              list="{}"
		                              headerKey="-1"
		                              headerValue="Select Activity"
		                              cssClass="select"
		                              cssStyle="width:82%"
		                              >
		                  	</s:select>
				</div>
		</div>
		
		<div class="newColumn">
			<div class="leftColumn1">Activity Code:</div>
			<div class="rightColumn1">
				<s:textfield name="activity_code"  id="activityCode%{#typeIndx}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
			</div>
		</div>                        
                             
                             
         <div class="newColumn">
			<div class="leftColumn1">Questionnaire:</div>
			<div class="rightColumn1">
			<s:select 
		                              id="questionnaire%{#typeIndx}"
		                              name="questionnaire" 
		                              list="#{'-1':'Select Questionnaire','A':'A', 'B':'B', 'C':'C', 'D':'D', 'E':'E'}"
		                               cssClass="select"
		                              cssStyle="width:82%"
		                              >
		                  	</s:select>
			</div>
			</div>
			
			<span id="form2MandatoryFields<s:property value='%{#typeIndx}'/>" class="qIds" style="display: none; ">Escalation#<s:property value="%{OLevel2LevelName}"/>#D#,</span>
			<div class="newColumn">
			<div class="leftColumn1">Escalation:</div>
			<div class="rightColumn1">
			<span class="needed"></span>
			<s:select 
		                              id="escalation%{#typeIndx}"
		                              name="escalation" 
		                              list="#{ 'No':'No','Yes':'Yes'}"
		                               cssClass="select"
		                              cssStyle="width:82%"
		                              onchange="needMyEsc(this,'l2%{#typeIndx}')"
		                              >
		                  	</s:select>
			</div>
			</div>
			
			
	<div class="clear"></div>			
			<div id ="l2<s:property value='%{#typeIndx}'/>" style="display: none;">
			<span id="form2MandatoryFields<s:property value='%{#typeIndx}'/>" class="qIds" style="display: none; ">Employee#empL1#D#,</span>
			<div class="newColumn">
			<div class="leftColumn1">Employee:</div>
			<div class="rightColumn1">
			<span class="needed"></span>
			<!--   function changeinMyNextEsc(data, div, sId, sName, nId, nName,val) -->
			
			<s:select 
								id="empL1%{#typeIndx}"
		                        name="l2_%{#typeIndx}" 
                              	list="escL2Emp"
                              	cssClass	="select"
								cssStyle	="width:82%;height:40%"
                              	multiple	="true"
                              	onchange="changeinMyNextEsc(this.value, 'l3esc%{#typeIndx}' ,'empL2%{#typeIndx}', 'l3', 'empL3%{#typeIndx}', 'l4%{#typeIndx}','%{#typeIndx}')"
		                              >
		                  	</s:select>
			</div>
			</div>
			
			
			<div class="newColumn">
          <div class="leftColumn1">L&nbsp;2&nbsp;TAT&nbsp;After:&nbsp;</div>
          <div class="rightColumn1">
	  <sj:datepicker id="l2EscDuration%{#typeIndx}" name="tat2" readonly="true" onchange="needMyMoreEsc('l3%{#typeIndx}');" placeholder="Enter Time" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssClass="textField"/>
          </div>
          </div>
			
			
			</div>
			
	<div class="clear"></div>			
			<div id = "l3<s:property value='%{#typeIndx}'/>" style="display: none;">
			<div class="newColumn">
			<div class="leftColumn1">Employee:</div>
			<div class="rightColumn1">
			<div id="l3esc<s:property value='%{#typeIndx}'/>">
			<s:select 
		                              id="empL2%{#typeIndx}"
		                              name="l3_%{#typeIndx}" 
		                              list="escL3Emp"
		                              cssClass="select"
		                              cssStyle	="width:82%;height:40%"
                              	multiple	="true"
                              	onchange="changeinMyNextEsc(this.value, 'l4esc%{#typeIndx}' ,'empL3%{#typeIndx}', 'l4', 'empL4%{#typeIndx}', 'l5%{#typeIndx}','%{#typeIndx}')"
		                              >
		                  	</s:select>
		                  	
		                  	</div>
			</div>
			</div>
			
			
			<div class="newColumn">
          <div class="leftColumn1">L&nbsp;3&nbsp;TAT&nbsp;After:&nbsp;</div>
          <div class="rightColumn1">
	  <sj:datepicker id="l3EscDuration%{#typeIndx}" name="tat3" readonly="true" onchange="needMyMoreEsc('l4%{#typeIndx}');" placeholder="Enter Time" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssClass="textField"/>
          </div>
          </div>
			</div>
			

<div class="clear"></div>			
			<div id = "l4<s:property value='%{#typeIndx}'/>" style="display: none;">
			<div class="newColumn">
			<div class="leftColumn1">Employee:</div>
			<div class="rightColumn1">
			<div id="l4esc<s:property value='%{#typeIndx}'/>">
			<s:select 
		                              id="empL3%{#typeIndx}"
		                              name="l4_%{#typeIndx}" 
		                              list="escL4Emp"
		                              cssClass="select"
		                              cssStyle	="width:82%;height:40%"
                              		multiple	="true"
                              		onchange="changeinMyNextEsc(this.value, 'l5esc%{#typeIndx}' ,'empL4%{#typeIndx}', 'l5', 'empL4%{#typeIndx}', 'l5%{#typeIndx}','%{#typeIndx}')"
		                              >
		                  	</s:select>
		                  	</div>
			</div>
			</div>
			
			
			<div class="newColumn">
          <div class="leftColumn1">L&nbsp;4&nbsp;TAT&nbsp;After:&nbsp;</div>
          <div class="rightColumn1">
	  <sj:datepicker id="l4EscDuration%{#typeIndx}" name="tat4" readonly="true" onchange="needMyMoreEsc('l5%{#typeIndx}');" placeholder="Enter Time" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssClass="textField"/>
          </div>
          </div>
			
			
			</div>
			
<div class="clear"></div>			
	<div id = "l5<s:property value='%{#typeIndx}'/>" style="display: none;">
			<div class="newColumn">
			<div class="leftColumn1">Employee:</div>
			<div class="rightColumn1">
			<div id="l5esc<s:property value='%{#typeIndx}'/>">
			<s:select 
		                              id="empL4%{#typeIndx}"
		                              name="l5_%{#typeIndx}" 
		                              list="escL5Emp"
		                              cssClass="select"
		                              cssStyle	="width:82%;height:40%"
                              	   	multiple	="true"
		                              >
		                  	</s:select>
		                  	</div>
			</div>
			</div>
			
		<div class="newColumn">
          <div class="leftColumn1">L&nbsp;5&nbsp;TAT&nbsp;After:&nbsp;</div>
          <div class="rightColumn1">
	  			<sj:datepicker id="l5EscDuration%{#typeIndx}" name="tat5" readonly="true"  placeholder="Enter Time" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssClass="textField"/>
          </div>
         </div>
	</div>
	
	<div class="clear"></div>
			<div id="timeGapDiv<s:property value='%{#typeIndx}'/>" style="display: none;">
			<div class="newColumn">
			<div class="leftColumn1">Time Gap from above activity:</div>
			<div class="rightColumn1">
			
			<s:textfield 
		                              id="time_gap"
		                              name="time_gap" 
		                              list="escL5Emp"
		                              cssClass="textField"
		                            cssStyle="margin:0px 0px 10px 0px;"
		                            placeholder="Enter Days"
				                              >
		                  	</s:textfield>
		                  	
			</div>
			</div>
		</div>
		
		<div style="">
		 				<s:if test="#typeIndx==110">
                                <div class="user_form_button2"><sj:a value="-" onclick="deletediv('%{#typeIndx}')" button="true"> - </sj:a></div>
                        </s:if>
                         <s:else>
                                   <div class="user_form_button2"><sj:a value="+"  onclick="adddiv('%{#typeIndx+1}')" button="true"> + </sj:a></div>
                                  <div class="user_form_button3" style="padding-left: 10px;"><sj:a value="-" onclick="deletediv('%{#typeIndx}')" button="true"> - </sj:a></div>
                        </s:else>
        </div>
		      
	<div class="clear"></div>
                           
                        </div>
                    </s:iterator>
                    </div>		
	
	
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
							onclick="viewClient2222()"
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
