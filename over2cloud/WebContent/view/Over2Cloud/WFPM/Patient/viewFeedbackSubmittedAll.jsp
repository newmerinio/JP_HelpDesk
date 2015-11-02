<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<style type="text/css">
/* #showFeedbackData{
display: block;
  width: 416px;
  min-height: 0px;
  height: 291px;
} */

</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script type="text/javascript">
//<a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b>

function showHistory(cellvalue,options,rowObject){
	return "<a style='cursor: pointer;text-decoration: none;'  href='#' onclick='showOfferingHistoy("+rowObject.id+");'><font color='blue'>"+cellvalue+"</font></a>";
}

function showOfferingHistoy(empId){
	var pid=jQuery("#feedbackSubmittedDataTable").jqGrid('getCell',empId,'empid');
	var dat=jQuery("#feedbackSubmittedDataTable").jqGrid('getCell',empId,'pdate');
	 
	$.ajax({
		type : "post",
		url  : "view/Over2Cloud/WFPM/Patient/fetchVisitHistoryData.action",
		data : "id="+pid+"&date="+dat,
		success : function(data){
			var mytable = $('<table style="margin:0px;padding:0px; width:98%;"></table>').attr({ id: "basicTable4" });
			$('<tr><td style=" background-color: #9fd7fb; padding:4px;margin:1px;border:1px solid #ccc;text-align:center; width: 18%;"><b>Activity</b></td><td style=" background-color: #9fd7fb; padding:4px;margin:1px;border:1px solid #ccc;text-align:center; width: 18%;"><b>Doctor</b></td><td style=" background-color: #9fd7fb; padding:4px;margin:1px;border:1px solid #ccc;text-align:center; width: 18%;"><b>Offering</b></td><td style=" background-color: #9fd7fb;padding:4px;margin:1px;border:1px solid #ccc;text-align:center; width: 18%;"><b>Date</b></td></tr>').appendTo(mytable);
			$(data.dataArray).each(function(index)
			{
				var tdValues = this.VALUE.split("#");
				var row = $('<tr></tr>').appendTo(mytable);
				for (var j = 0; j < tdValues.length; j++) 
				{
					if(index===(data.dataArray.length-1)){ 
				$('<td style="background-color: #c3e6fc; padding:4px;margin:1px;border:1px solid #ccc;text-align:center; width: 10%;"></td>').text(tdValues[j].trim()).appendTo(row);	
					}else
					$('<td style="background-color: #c3e6fc; padding:4px;margin:1px;border:1px solid #ccc;text-align:center; width: 10%;"></td>').html(""+tdValues[j]+"").appendTo(row);
				}
			});
			
			$("#activityHistoryData3").html("");
			mytable.appendTo("#activityHistoryData3");
			$("#patientActivityHistory4").dialog({title: 'Patient Activity History', width:'900', height:'500'});
			$("#patientActivityHistory4").dialog("open");
			
		},
		error   : function(){
			alert("error");
		}
	});
}

function show(cellvalue,options,rowObject){
	//alert("chk");
	return "<a style='cursor: pointer;text-decoration: none;'  href='#' onclick='viewIndividual("+rowObject.id+");'><font color='blue'>"+cellvalue+"</font></a>";
}

function viewIndividual(empId){
	//alert(empId+" ale  "+login +"   rt !!" +eid);
	  var name  = jQuery("#feedbackSubmittedDataTable").jqGrid('getCell',empId,'pname');
	  	//alert('name'+name);
	  var dat=jQuery("#feedbackSubmittedDataTable").jqGrid('getCell',empId,'pdate');
	/*   //alert(dat);
	  var dat2=dat.split('"red">');
	  //alert(dat2[1]);
	  var dat3=dat2[1].split('</font>');
	  //alert(dat3[0]);
	  var dat4=dat3[0];
	 // alert("f "+dat4); */
	  var pid=jQuery("#feedbackSubmittedDataTable").jqGrid('getCell',empId,'empid');
	  var ptime=jQuery("#feedbackSubmittedDataTable").jqGrid('getCell',empId,'ptime');
	  $.ajax({
		    type : "post",
		    url : "view/Over2Cloud/questionairOver2Cloud/createAnswerConf.action",
		    data:"pdate="+dat+"&id="+pid+"&ptime="+ptime+"&pageFor=history",
		    success : function(subdeptdata) {
		    	 $("#showFeedbackData").dialog({title:'Submitted Feedback Details: '+name+" on "+dat,width: 590,height: 430});
		    	 $("#showDetails").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		    	 $("#showFeedbackData").dialog('open');
	       $("#showDetails").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
}

function closedownload(){
	$("#showFeedbackData").dialog('close');
}
</script>


</head>
<body>
   <sj:dialog 
	id="showFeedbackData"
	buttons="{'Cancel':function() { closedownload(); },}"
	showEffect="slide" 
	hideEffect="explode" 
	autoOpen="false" 
	modal="true"
	title="View History" 
	openTopics="openEffectDialog"
	closeTopics="closeEffectDialog"
	>
	<div id="showDetails"></div>
	</sj:dialog>
	 
<div style="overflow: scroll;  height: 400px;">
<s:url id="getData" action="viewFeedbackSubmittedAll" namespace="view/Over2Cloud/patientActivity" escapeAmp="false">
 <s:param name="pid" value="%{pid}" />
 <s:param name="pdate" value="%{pdate}" />
  <s:param name="date" value="%{date}" /> 
</s:url>


<sjg:grid 
		  id="feedbackSubmittedDataTable"
          gridModel="viewList"
          href="%{getData}"
          groupField="['pname']"
          groupColumnShow="[true]"
          groupCollapse="true"
          groupText="['<b>{0} : {1}</b>']"
          navigator="true"
          navigatorAdd="false"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="false"
          rowList="50,100,200,500"
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
          loadingText="Requesting..."  
          shrinkToFit="true"
          autowidth="true"
          loadonce="false"
          rowNum="10"
          rownumbers="true"
          viewrecords="true" 
          >
			<sjg:gridColumn 
			name="id"
			id="id"
			index="id"
			title="id"
		
			align="center"
			hidden="true"
			/>
			
			<sjg:gridColumn 
			name="patient_id"
			id="patient_id"
			index="id"
			title="Patient ID"
			align="center"
			hidden="false"
			formatter="showHistory"
			/>
		
		
			<sjg:gridColumn 
			name="pname"
			id="pname"
			index="pname"
			title="Patient Name"
			align="center"
			hidden="false"
			/>
		
			
			
			<sjg:gridColumn 
			name="pdate"
			id="pdate"
			index="pdate"
			title="Date"
			align="center"
			hidden="false"
			
			/>
			
				<sjg:gridColumn 
			name="empid"
			id="empid"
			index="empid"
			title="empid"
			align="center"
			hidden="true"
			/>

				<sjg:gridColumn 
			name="ptime"
			id="ptime"
			index="ptime"
			title="ptime"
			align="center"
			hidden="true"
			/>
			
				<sjg:gridColumn 
			name="qset"
			id="qset"
			index="ptime"
			title="Question Set"
			align="center"
			hidden="false"
			formatter="show"
			/>

		
			
 </sjg:grid>
</div>

<sj:dialog
	id="patientActivityHistory4"
	autoOpen="false"
	modal="true"
	showEffect="slide" 
   	hideEffect="explode" 
    openTopics="openEffectDialog"
    closeTopics="closeEffectDialog"
>
<div style="width: 104%;">
<div id="activityHistoryData3"  style="   margin-left: 4px;
  padding: 0px;
  overflow-y: auto;
  width: 97%;"></div>
</div>
</sj:dialog>



</body>
</html>