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

<title>Insert title here</title>
<SCRIPT type="text/javascript" src="js/helpdesk/common.js"></SCRIPT>
	<style type="text/css">
	
		.textField
		{
			height:20px;	
		}
		.select
		{
			height:25px;
			width:150px;	
		}
		/* .newColumn
		{
			width:50%;	
		} */
		.leftColumn1
		{
			width:43%;
		} 
		 .rightColumn1
		{
			width:50%;	
		} 
		span.needed
		{
			height:22px;	
		}
		td.tdAlign {
			padding: 5px;
		}
 		
 		.hoverDiv:hover{
  			 white-space:initial;
    		cursor:pointer;
   			 width:100px;
   			 word-wrap:break-word;
		}

 		
		tr.color {
			background-color: #E6E6E6;
		}
		
		.wrap {
		width: 100%;
	}

	.floatleft {
    	float:left;
    	width: 20%;
	}

	.floatright {
    	width: 100%;
    	float:left;
	}
	
	</style>
	<script type="text/javascript">
	$.subscribe('level2', function(event,data)
	        {
	          setTimeout(function(){ $("#patientaddition").fadeIn(); }, 10);
	          setTimeout(function(){ $("#patientaddition").fadeOut(); }, 4000);
	          viewOfferingDetails();
	          resetForm('formone');
	});
	
	
	function fetchAllOfferingDetails(value)
	{
		$.ajax({
	 	    type : "post",
	 	    url : "view/Over2Cloud/WFPM/Patient/fetchAllOfferingDetails.action?rowid="+$("#rowid").val()+"&offeringLevel="+value,
	 	    success : function(data)
	 	    {
	 			$("#createdBy").html(data[0].createdBy);
	 			$("#relName").html(data[0].relName);
	 			$("#lastActivity").html(data[0].laststatus);
	 			$("#lastDate").html(data[0].lastDate);
	 			$("#lastComment").html(data[0].lastComment);
	 			$("#currActivity").html(data[0].status_view);
	 			$("#curr_schedule_date_div").html(data[0].date);
	 			$("#comment1_div").html(data[0].nextComment);
	 			$("#current_activity").val(data[0].currentActivity);
	 			$("#curr_schedule_date").val(data[0].date);
	 			$("#comment1").val(data[0].nextComment);
	 		},
	 	   error: function() {
	             alert("error");
	         }
	 	 });
	}
	
	 function fetchLevelData(val,selectId,Offeringlevel)
	 {
	 	$.ajax({
	 	    type : "post",
	 	    url : "view/Over2Cloud/WFPM/Patient/fetchOfferingLevelData.action?offeringId="+val.value+"&Offeringlevel="+Offeringlevel,
	 	    success : function(data) {
	 		    
	 		$('#'+selectId+' option').remove();
	 		$('#'+selectId).append($("<option></option>").attr("value",-1).text('Select'));
	     	$(data).each(function(index)
	 		{
	 		   $('#'+selectId).append($("<option></option>").attr("value",this.ID).text(this.NAME));
	 		});
	 	},
	 	   error: function() {
	             alert("error");
	         }
	 	 });
	 }
	 
	 function changeDivView(value,div1,div2)
		{
			if(value=='2')
			{
				$("#dr").val("-1");
				$('#'+div2).show();
				$('#'+div1).hide();
			}	
			else
			{
				$("#relationship_mgr").val("-1");
				$('#'+div1).show();
				$('#'+div2).hide();
			}	
		}
	 
	 function fetchSubOfferingData(val,id1)
	 {
	 	$.ajax({
	 	    type : "post",
	 	    url : "view/Over2Cloud/WFPM/Patient/fetchSubOfferingData.action?id="+val+"&status="+$("#patient_type").val()+"&offering="+$("#stage").val(),
	 	    async:false,		
	 	    success : function(data) {
	 		$('#'+id1+' option').remove();
	 		//$('#'+targetid+' option').remove();
	 		$('#'+id1).append($("<option></option>").attr("value",-1).text('Select Next Activity'));
	     	$(data).each(function(index)
	 		{
	 		  $('#'+id1).append($("<option></option>").attr("value",this.ID).text(this.name));
	 		});
	 	},
	 	   error: function() {
	             alert("error");
	         }
	 	 });
	 }
	 
		function viewOfferingDetails()
		{
			var id=$("#id").val();
			var pId=$("#patient_name").val();
			var offering=$("#subofferingname").val();
			alert(pId+":::::"+offering);
			$("#offeringlevel3").val(offering);
			$.ajax({
				type : "post",
				url  : "view/Over2Cloud/WFPM/Patient/fetchAllDetailsOffering.action?offering="+offering+"&pId="+pId+"&id="+id,
				async:false,		
				success : function(data){
					 var mytable ='<table style="margin:0px;padding:0px; width:98%;">';
					mytable+='<tr><td style=" background-color: #EFB8A4; padding:4px;margin:1px;border:1px solid #ccc;text-align:center; width: 18%;"><b>Activity</b></td><td style=" background-color: #EFB8A4; padding:4px;margin:1px;border:1px solid #ccc;text-align:center; width: 18%;"><b>Date</b></td><td style=" background-color: #EFB8A4;padding:4px;margin:1px;border:1px solid #ccc;text-align:center; width: 18%;"><b>Owner</b></td><td style=" background-color: #EFB8A4;padding:4px;margin:1px;border:1px solid #ccc;text-align:center; width: 18%;"><b>Status</b></td><td style=" background-color: #EFB8A4;padding:4px;margin:1px;border:1px solid #ccc;text-align:center; width: 18%;"><b>Comment</b></td></tr>';
					var i=0;
					var oid=null;
					var stge=null;
					$(data).each(function(index)
					{
						if(i=='0')
						{
							oid=data[index].id;
							stge=data[index].stage;
						}
						if(i%2==0)
						{
							mytable+='<tr><td style="text-align:center; width: 10%;"><a href="#" onclick="viewActivityDetails('+data[index].id+')"><u id="id'+data[index].id+'">'+data[index].activity+'</u></a></td><td style="text-align:center; width: 10%;">'+data[index].scheduledate+'</td><td style="padding:4px;text-align:center; width: 10%;">'+data[index].name+'</td><td style="padding:4px;text-align:center; width: 10%;">'+data[index].status+'</td><td  style="padding:11px;text-align:center; width: 50px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;position: absolute;" title="'+data[index].comment+'">'+data[index].comment+'</td></tr>';
						}	
						else
						{
							mytable+='<tr style=" background-color: #D8DCDE;"><td style="text-align:center; width: 10%;"><a href="#" onclick="viewActivityDetails('+data[index].id+')"><u id="id'+data[index].id+'">'+data[index].activity+'</u></a></td><td style="text-align:center; width: 10%;">'+data[index].scheduledate+'</td><td style="padding:4px;text-align:center; width: 10%;">'+data[index].name+'</td><td style="padding:4px;text-align:center; width: 10%;">'+data[index].status+'</td><td  style="padding:11px;text-align:center; width: 50px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;position: absolute;" title="'+data[index].comment+'">'+data[index].comment+'</td></tr>';
						}	
						i++;
					});
					if($("#laststatus").val()=='1')
					{
						
					}	
					else
					{
						$("#id").val(oid);
					}
					if(stge!=null)
					{
						$("#stage").val(stge);
					}	
					
					mytable+='</table>';
					$("#offeringDetails").html("");
					$("#offeringDetails").html(mytable);
					$('#id'+$('#id').val()).css('color', '#1CCCEA');
					selectActionOnDiv(stge);
				},
				error   : function(){
					alert("error");
				}
			});
			
		}
	
	function viewActivityDetails(id)
	{
		$("#takeActionGrid").dialog({title: 'Activity History',width: 600,height: 300});
		$("#takeActionGrid").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$("#takeActionGrid").dialog('open');
		$.ajax({
			type : "post",
			url  : "view/Over2Cloud/WFPM/Patient/fetchAllActivityDetails.action?id="+id,
			success : function(data)
			{
				$("#takeActionGrid").html(data);
			},
			error   : function(){
				alert("error");
			}
		});	
	}
		
		
	function selectAction(value)
	{
		if(value=='Completed')
		{
			showHideDiv('completedDiv','snoozeDiv','reassignDiv','seekDiv');
		}
		else if(value=='Snooze')
		{
			showHideDiv('snoozeDiv','completedDiv','reassignDiv','seekDiv');
		}
		else if(value=='Re-assign')
		{
			showHideDiv('reassignDiv','snoozeDiv','completedDiv','seekDiv');
		}
		
		else if(value=='Seek Approval')
		{
			showHideDiv('seekDiv','snoozeDiv','reassignDiv','completedDiv');
		}
		
		else
		{
			$('#seekDiv').hide();
			$('#snoozeDiv').hide();
			$('#reassignDiv').hide();
			$('#completedDiv').hide();
		}	
	}
	
	function showHideDiv(showDiv,hideDiv1,hideDiv2,hideDiv3)
	{
		$('#'+showDiv).show();
		$('#'+hideDiv1).hide();
		$('#'+hideDiv2).hide();
		$('#'+hideDiv3).hide();
	}
	
	function selectActionOnDiv(val)
	{
		value=$("#stage").val();
		
		if(value=='1')
		{
			showHideDiv1('pDiv','nDiv','closeDiv','lostDiv');
			$('#nDiv').show();
		}
		/* else if(value=='2')
		{
			showHideDiv1('prcDiv','pDiv','closeDiv','nDiv');
		} */
		else if(value=='3')
		{
			showHideDiv1('lostDiv','pDiv','closeDiv','nDiv');
		}
		
		else if(value=='4')
		{
			showHideDiv1('closeDiv','pDiv','nDiv','lostDiv');
			$('#nDiv').show();
			$('#pDiv').show();
			if(val=='4')
			{
				$("#closeDiv").hide();
			}
		}
		
	}
	
	function showHideDiv1(showDiv,hideDiv1,hideDiv2,hideDiv3)
	{
		$('#'+showDiv).show();
		$('#'+hideDiv1).hide();
		$('#'+hideDiv2).hide();
		$('#'+hideDiv3).hide();
	}
	
	 function changeActDivView(value,div1)
	 {
			if(value=='2')
			{
				$('#'+div1).hide();
			}	
			else
			{
				$('#'+div1).show();
			}	
	 }
	
		viewOfferingDetails();
		function fetchAllData(value)
		{
			if(value=='Add New')
			{
				 var row=$("#patient_name").val();
				 var first_name=$("#first_name").val();
				 var mobile=$("#mobile").val();
				 var crm_id=$("#crm_id").val();
				 var patient_type=$("#patient_type").val();
				 $("#dataPartVisit").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/></center>");
				 $.ajax({
					type : "post",
					url : "view/Over2Cloud/WFPM/Patient/addNewOffering.action?rowid="+row+"&stage=1&first_name="+first_name+"&mobile="+mobile+"&crm_id="+crm_id+"&patient_type="+patient_type,
					success : function(subdeptdata)
					{
						$("#"+"dataPartVisit").html(subdeptdata);
					},
					error: function()
					{
						alert("error");
					}
				});     
			}	
			else
			{
				viewOfferingDetails();
				fetchSubOfferingData(value,'next_activity');
				//fetchAllOfferingDetails(value);
				getDocList(value,'dr','re_dr');
			}	
		}
		
		function getDocList(val,targetid,targetid1){
			 $.ajax({
				    type : "post",
				    url : "view/Over2Cloud/patientActivity/getDoctorList.action?offeringId="+val,
				    success : function(data) 
				    {
				    	//allDataList=data;
				  		$('#'+targetid+' option').remove();
				  		$('#'+targetid1+' option').remove();
						$('#'+targetid).append($("<option></option>").attr("value",-1).text("--Select Doctor Name--"));
						$('#'+targetid1).append($("<option></option>").attr("value",-1).text("--Select Doctor Name--"));
				    	$(data).each(function(index)
						{
						   $('#'+targetid).append($("<option></option>").attr("value",this.ID).text(this.NAME));
						   $('#'+targetid1).append($("<option></option>").attr("value",this.ID).text(this.NAME));
						});
				    },
				   error: function() {
				        alert("error");
				    }
				 });
			// setOwnerAndBuddy();
			}
		
		//selectActionOnDiv('');
	</script>
</head>
<body>
<sj:dialog
          id="takeActionGrid"
          showEffect="blind" 
          hideEffect="clip" 
          autoOpen="false"
          modal="true"
          width="1000"
          height="350"
          draggable="true"
          resizable="true"
          position="center"
          >
</sj:dialog>
	<div id="dataPartVisit" >
	<div class="clear"></div>
	<div class="list-icon">
	<s:hidden id="first_name" value="%{first_name}"/>
	<s:hidden id="mobile" value="%{mobile}"/>
	<s:hidden id="crm_id" value="%{crm_id}"/>
	<s:hidden id="patient_type"  value="%{patient_type}"></s:hidden>
	<s:hidden id="laststatus"  value="%{laststatus}"></s:hidden>
	<div class="head">Patient</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:60%; float: left;"></div><div class="head" id="">Activity of <s:property value="%{first_name}"/>, <s:property value="%{mobile}"/>, <s:property value="%{crm_id}"/> for </div>
	<div class="head"><s:select 
		                              id="subofferingname"
		                              name="subofferingname" 
		                              list="offeringMap"
		                              cssClass="button"
		                              cssStyle="width:100%"
		                              onchange="fetchAllData(this.value)"
		                              value="%{offeringLevel}"
		                              theme="simple"
		                              >
		                  	</s:select></div>
	</div>
	<div class="clear"></div>
	<div class="border">
	<div class="container_block" style="height: 450px;">
	<div style="overflow-y:auto;border-radius:6px; width:34%; float:left;border: 1px solid #e4e4e5; border-right: 1px solid rgba(176, 165, 165, 0.54);">
		<div class="wrap">
    	<!-- <div  class="floatleft" id="offeringNames" style="border: 1"></div> -->
    	<div class="floatright" id="offeringDetails"></div>
	</div>
	</div>
	
	<div style="overflow-y:auto;border-radius:6px; height:100%; width:65%; float:right;border: 1px solid #e4e4e5; border-right: 1px solid rgba(176, 165, 165, 0.54);">
	<div style="float:right; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
             <div id="errZone" style="float:left; margin-left: 7px">
             </div> 
       	 </div>	
        	<br>
				<s:form id="formone" name="formone" namespace="/view/Over2Cloud/WFPM/Patient" action="addFirstActivity" theme="css_xhtml"  method="post" enctype="multipart/form-data">
				<s:hidden id="patient_name" name="patient_name" value="%{rowid}"></s:hidden>
				<s:hidden id="id" name="id" value="%{id}"></s:hidden>
				<s:hidden id="status" name="status" value="%{status}"></s:hidden>
				<s:hidden id="offeringlevel3" name="offeringlevel3" value="%{offeringLevel}"></s:hidden>
			<%-- 	<s:hidden id="stage" name="stage" value="%{stage}"></s:hidden> --%>
				<%-- <s:hidden id="current_activity" name="current_activity" value="%{traceid}"></s:hidden>
				<s:hidden id="curr_schedule_date" name="curr_schedule_date" value="%{date}"></s:hidden>
				<s:hidden id="comment1" name="comment1" value="%{nextComment}"></s:hidden> --%>
   
    
		<div class="newColumn">
			<div class="leftColumn1">Patient&nbsp;Relationship&nbsp;Stage:</div>
			<div class="rightColumn1">
			     <s:select
			      		   list="stageMap"
			     		   id="stage"
			     		   name="stage"	
			     		   cssClass="select"
			     		   value="{stage}"
			     		   onchange="selectActionOnDiv();">
			     	</s:select>
			</div>
		</div>
	
		<div id="closeDiv" style="display: none;">
		<div class="newColumn">
			<div class="leftColumn1">Current Activity:</div>
				<div class="rightColumn1">
						<s:textfield  id="curr_outcome" cssClass="textField" value="%{status_view}"  readonly="true" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;" theme="simple"/>
			</div>
		</div>
		
		<div class="newColumn">
			<div class="leftColumn1">UHID:</div>
				<div class="rightColumn1">
					<s:textfield  id="uhid" name="uhid" cssClass="textField"  placeholder="Enter UHID" cssStyle="margin:0px 0px 10px 0px;" theme="simple"/>
			</div>
		</div>
		
		<div class="newColumn">
			<div class="leftColumn1">Admitted On:</div>
				<div class="rightColumn1">
						<sj:datepicker name="adm_date" id="adm_date"
							cssStyle="width:125px;" Class="button" size="15"
							displayFormat="dd-mm-yy" readonly="true" showOn="focus"
							Placeholder="Select Date" />
				</div>
		</div>
		<div class="clear"></div>
		<div class="newColumn">
			<div class="leftColumn1">Under Doctor:</div>
				<div class="rightColumn1">
					<s:textfield  id="un_doc" name="un_doc" cssClass="textField"  placeholder="Enter Name" cssStyle="margin:0px 0px 10px 0px;" theme="simple"/>
			</div>
		</div>
		
		<div class="newColumn">
			<div class="leftColumn1">Package:</div>
				<div class="rightColumn1">
						<s:select 
		                  id="package" 
		                  list="{'No Data'}"
		                  cssClass="select"
		                  cssStyle="width:82%"
		                  headerKey="-1"
		                  headerValue="Select Package"
		                  theme="simple"
		        		>
				</s:select>
				</div>
		</div>
		<div class="clear"></div>
		<div class="newColumn">
			<div class="leftColumn1">Remarks:</div>
				<div class="rightColumn1">
						<s:textarea  id="cl_reason" name="cl_reason" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;" theme="simple"/>
				</div>
		</div>
	</div>
	
	<div id="lostDiv" style="display: none;">
		<div class="newColumn">
			<div class="leftColumn1">Current Activity:</div>
				<div class="rightColumn1">
						<s:textfield  id="curr_outcome" cssClass="textField" value="%{status_view}"  readonly="true" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;" theme="simple"/>
			</div>
		</div>
		
		<div class="newColumn">
			<div class="leftColumn1">Reason:</div>
				<div class="rightColumn1">
					<s:select 
		                  id="lost_reason"
		                  name="lost_reason" 
		                  list="sourceMap"
		                  cssClass="select"
		                  cssStyle="width:82%"
		                  headerKey="-1"
		                  headerValue="Select Reason"
		                  theme="simple"
		        >
				</s:select>
			</div>
		</div>
		<div class="newColumn">
			<div class="leftColumn1">RCA:</div>
				<div class="rightColumn1">
						<s:textfield  id="rca" name="rca" cssClass="textField" placeholder="Enter RCA" cssStyle="margin:0px 0px 10px 0px;" theme="simple"/>
				</div>
		</div>
		<div class="newColumn">
			<div class="leftColumn1">Suggested CAPA:</div>
				<div class="rightColumn1">
						<s:textfield  id="capa" name="capa" cssClass="textField" placeholder="Enter CAPA" cssStyle="margin:0px 0px 10px 0px;" theme="simple"/>
				</div>
		</div>
	</div>
	
	 <div id="pDiv">
	 <div class="clear"></div>
		<hr style="width: 96%;margin-left: 16px;">
	  <div class="newColumn">
			<div class="leftColumn1">Action&nbsp;On&nbsp;Current&nbsp;Activity:</div>
			<div class="rightColumn1">
			     <s:select
			      list="#{'Completed':'Completed','Snooze':'Snooze','Re-assign':'Re-assign','Seek Approval':'Seek Approval'}"
			     		   id="statusActivity"
			     		   name="statusActivity"	
			     		   cssClass="select"
			     		   headerKey="-1"			     	
			     		   headerValue="Select Action"
			     		   onchange="selectAction(this.value);">
			     	</s:select>
			</div>
		</div>   
		
	<div id="completedDiv" style="display: none;">		
		<div class="newColumn">
				<div class="leftColumn1">Current Activity Outcome:</div>
				<div class="rightColumn1">
						<s:textfield name="curr_outcome" id="curr_outcome" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;" theme="simple"/>
				</div>
		</div>
	</div>
	
	<div id="snoozeDiv" style="display: none;">
		<div class="newColumn">
			<div class="leftColumn1">Current Activity:</div>
				<div class="rightColumn1">
						<s:textfield  id="curr_act" cssClass="textField" value="%{status_view}" readonly="true" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;" theme="simple"/>
				</div>
		</div>
		
		<div class="newColumn">
			<div class="leftColumn1">Snooze Date:</div>
				<div class="rightColumn1">
						<sj:datepicker name="sn_upto_date" id="sn_upto_date"
							cssStyle="width:125px;" Class="button" size="15"
							displayFormat="dd-mm-yy" readonly="true" showOn="focus"
							Placeholder="Select Snooze Till Date" minDate="0"/>
				</div>
		</div>
		
		<div class="newColumn">
			<div class="leftColumn1">Snooze Time:</div>
				<div class="rightColumn1">
						<sj:datepicker name="sn_upto_time" id="sn_upto_time"
							cssStyle="width:125px;" timepickerOnly="false" timepicker="true"
							timepickerOnly="true" timepickerAmPm="false" Class="button"
							size="15" displayFormat="dd-mm-yy" readonly="true" showOn="focus"
							Placeholder="Select Snooze Till Time" />
				</div>
		</div>
		
		<div class="newColumn">
			<div class="leftColumn1">Reason:</div>
				<div class="rightColumn1">
						<s:textarea  id="sn_reason" name="sn_reason" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;" theme="simple"/>
				</div>
		</div>
	
	</div>
	
	<div id="reassignDiv" style="display: none;">
		
		<div class="newColumn">
			<div class="leftColumn1">Current Activity:</div>
				<div class="rightColumn1">
						<s:textfield  id="curr_act" cssClass="textField" value="%{status_view}"  readonly="true" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;" theme="simple"/>
			</div>
		</div>
		<div class="newColumn">
			<div class="leftColumn1">Reassign&nbsp;To:</div>
			<div class="rightColumn1">
					<s:radio label="re_owner" name="re_owner" cssClass="radio" list="#{'1':'Doctor','2':'Buddy'}" value="1" theme="simple"  onchange="changeDivView(this.value,'docReDiv','buddyReDiv')"/>
			</div>
		</div>
		<div id="docReDiv">
			<div class="newColumn">
			<div class="leftColumn1">Owner:</div>
			<div class="rightColumn1">
					<s:select 
		                  name="re_dr" 
		                  id="re_dr" 
		                  list="deptMap"
		                  cssClass="select"
		                  cssStyle="width:82%"
		                  headerKey="-1"
		                  headerValue="Select Doctor Name"
		                  theme="simple"
		        >
				</s:select>
			</div>
			</div>
		</div>
		<div id="buddyReDiv" style="display:none;">
			<div class="newColumn">
			<div class="leftColumn1">Owner:</div>
			<div class="rightColumn1">
					<s:select 
		                  name="re_relmgr" 
		                  id="re_relmgr" 
		                  list="relManagerMap"
		                  cssClass="select"
		                  cssStyle="width:82%"
		                  headerKey="-1"
		                  headerValue="Select Buddy Name"
		                  theme="simple"
		        >
				</s:select>
			</div>
		</div>
		</div>
		
		<div class="newColumn">
			<div class="leftColumn1">Reason:</div>
				<div class="rightColumn1">
						<s:textarea  id="re_reason" name="re_reason" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;" theme="simple"/>
				</div>
		</div>
	
	</div>
	
	<div id="seekDiv" style="display: none;">
		<div class="newColumn">
			<div class="leftColumn1">Current Activity:</div>
				<div class="rightColumn1">
						<s:textfield  id="curr_outcome" cssClass="textField" value="%{status_view}"  readonly="true" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;" theme="simple"/>
			</div>
		</div>
		
		<div class="newColumn">
			<div class="leftColumn1">Seek Approval By:</div>
				<div class="rightColumn1">
					<s:select 
		                  name="seek_by" 
		                  id="seek_by" 
		                  list="empMap"
		                  cssClass="select"
		                  cssStyle="width:82%"
		                  headerKey="-1"
		                  headerValue="Seek Approval By"
		                  theme="simple"
		        >
				</s:select>
			</div>
		</div>
		<div class="newColumn">
			<div class="leftColumn1">Reason:</div>
				<div class="rightColumn1">
						<s:textarea  id="se_reason" name="se_reason" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;" theme="simple"/>
				</div>
		</div>
	</div>

	<!-- <div class="newColumn">
			<div class="leftColumn1">&nbsp;</div>
			<div class="rightColumn1">
			</div>
	</div> -->	
	</div>
	
	<div style="width: 98%;" id="nDiv">
	
	<div class="clear"></div>
	<hr style="width: 98%;margin-left: 16px;">
	<div class="newColumn">
			<div class="leftColumn1">New&nbsp;Activity&nbsp;Required?</div>
			<div class="rightColumn1">
					<s:radio label="new_act" name="new_act" cssClass="radio" list="#{'1':'Yes','2':'No'}" value="2" theme="simple"  onchange="changeActDivView(this.value,'newActivity')"/>
			</div>
	</div>		
	<div id="newActivity" style="display: none;width: 98%;">
	<div class="clear"></div>
	<div class="newColumn">
			<div class="leftColumn1">Owner&nbsp;Type:</div>
			<div class="rightColumn1">
					<s:radio label="ownerType" name="ownerType" cssClass="radio" list="#{'1':'Doctor','2':'Buddy'}" value="1" theme="simple"  onchange="changeDivView(this.value,'docDiv','buddyDiv')"/>
			</div>
		</div>
		<div id="docDiv">
			<div class="newColumn">
			<div class="leftColumn1">Next&nbsp;Activity&nbsp;Owner:</div>
			<div class="rightColumn1">
					<s:select 
		                  name="dr" 
		                  id="dr" 
		                  list="deptMap"
		                  cssClass="select"
		                  cssStyle="width:82%"
		                  headerKey="-1"
		                  headerValue="Select Doctor Name"
		                  theme="simple"
		        >
				</s:select>
			</div>
			</div>
		</div>
		<div id="buddyDiv" style="display:none;">
			<div class="newColumn">
			<div class="leftColumn1">Next&nbsp;Activity&nbsp;Owner:</div>
			<div class="rightColumn1">
					<s:select 
		                  name="relationship_mgr" 
		                  id="relationship_mgr" 
		                  list="relManagerMap"
		                  cssClass="select"
		                  cssStyle="width:82%"
		                  headerKey="-1"
		                  headerValue="Select Buddy Name"
		                  theme="simple"
		        >
				</s:select>
			</div>
		</div>
		</div>	
		
		<!-- <div class="newColumn">
			<div class="leftColumn1">&nbsp;</div>
			<div class="rightColumn1">
			</div>
		</div>
		 -->
	<s:iterator value="patientTextBox" status="counter">
	
	<s:if test="%{key == 'maxDateTime'}">
		<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{value}"/>:</div>
			<div class="rightColumn1">
				<s:if test="%{mandatory}">
					<span id="mandatoryField" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
					<span class="needed"></span>
				</s:if>
					<sj:datepicker id="%{key}" name="%{key}" readonly="true"  placeholder="Enter Data" showOn="focus" timepicker="true"  cssClass="textField"
						changeMonth="true" changeYear="true" yearRange="2015:2020" displayFormat="dd-mm-yy" value="today" minDate="0"/>
			</div>
		</div>
		
	</s:if>
	
	<s:elseif test="%{key == 'next_activity'}">
		<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{value}"/>:</div>
			<div class="rightColumn1">
				<s:if test="%{mandatory}">
					<span id="mandatoryField" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
					<span class="needed"></span>
				</s:if>
							<s:select 
		                             name="%{key}" id="%{key}" 
		                              cssClass="select"
		                              headerKey="-1"
		                              headerValue="Select Next Activity"
		                              cssStyle="width:82%"
		                              list="rhTypeMap"
		                              theme="simple"
		                              >
		                  	</s:select>
		     </div>
		 </div>                 	
	</s:elseif>
	
	<s:elseif test="%{key == 'comment2'}">
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{value}"/>:</div>
			<div class="rightColumn1">
				<s:if test="%{mandatory}">
					<span id="mandatoryField" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
					<span class="needed"></span>
				</s:if>
					<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;" theme="simple"/>
			</div>
		</div>			
	</s:elseif>
   </s:iterator>
   <s:iterator value="patientTextBox" status="counter">
   	<s:if test="%{key == 'sent_questionair'}">
			<div class="newColumn">
			<div class="leftColumn1">Communication:</div>
			<div class="rightColumn1" style="padding: 3px;">
					<s:checkbox name="sendEmail"  id="sendEmail" theme="simple">Email </s:checkbox>
					<s:checkbox name="sendSMS"  id="sendSMS" theme="simple">SMS </s:checkbox>
					<s:checkbox name="sendQuestion"  id="sendQuestion" theme="simple">Questionnaire </s:checkbox>
			</div>
		</div>			
	</s:if>
	
	<s:elseif test="%{key == 'attachment'}">
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{value}"/>:</div>
			<div class="rightColumn1">
				<s:if test="%{mandatory}">
					<span id="mandatoryField" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
					<span class="needed"></span>
				</s:if>
		                <s:file id="%{key}" value="%{key}" theme="simple" ></s:file>
			</div>
		</div>	
		<div class="clear"></div>		
	</s:elseif>
   </s:iterator> 
   </div> 
   </div>    
	<!-- </div> -->
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
            onCompleteTopics="level2"
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
					cssStyle="margin-left: 200px;margin-top: -43px;"
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
					onclick="backPatientBasic()"
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
	</div>
</body>
</html>