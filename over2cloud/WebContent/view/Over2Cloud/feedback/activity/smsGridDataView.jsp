<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%
	String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();
%>
<html>
<head>

<script type="text/javascript">
var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});	

function takeAction(cellvalue, options, rowObject) 
{
	var context_path = '<%=request.getContextPath()%>';
	return "<a href='#'><img title='Take Action' src='"+ context_path +"/images/action.jpg' height='20' width='20' onClick='takeActionOnClick("+rowObject.id+")'> </a>";
}

function takeActionOnClick(id) 
{
	$("#confirmationDialog").dialog({title : 'Take Action',width : 400,height : 110});
	$("#confirmationDialog").dialog('open');
	$("#messageDiv111").text("Are you sure to take this action?");
	var patientId  = jQuery("#sparecGrid").jqGrid('getCell',id,'patientId');
	var patientType  = jQuery("#sparecGrid").jqGrid('getCell',id,'patient_type');
	var station  = jQuery("#sparecGrid").jqGrid('getCell',id,'station');
	var admDate  = jQuery("#sparecGrid").jqGrid('getCell',id,'admission_time');
	var disDate  = jQuery("#sparecGrid").jqGrid('getCell',id,'discharge_datetime');
	var serviceBy  = jQuery("#sparecGrid").jqGrid('getCell',id,'serviceBy');
	var companytype  = jQuery("#sparecGrid").jqGrid('getCell',id,'companytype');
	$("#dataDiv1").text(patientId+"# "+patientType+"# "+station+"# "+admDate+"# "+disDate+"# "+serviceBy+"# "+companytype);
}

function saveButton(id)
{
	id=$("#feedbackDataId").val();
	var data=$("#dataDiv1").text().split("# ");
	 $.ajax({
		      type: "POST",
		      url :  "/over2cloud/view/Over2Cloud/feedback/feedback_Activity/takeAction.action?id="+id+"&patientId="+data[0]+"&compType="+data[1]+"&centerCode="+data[2]+"&admission_time="+data[3]+"&discharge_datetime="+data[4]+"&serviceBy="+data[5]+"&companytype="+data[6],
		      success: function(data) 
		      {
		    	  $("#messageDiv111").text("Action Taken Successfully!!!");
		      },
		      error: function(data)
		      {
		    	  $("#messageDiv111").text("Oops!!! There is some error in data.");
		      }
		 }); 
	 setTimeout(function(){ $("#messageDiv111").fadeIn(); }, 10);
	 setTimeout(function(){ $("#messageDiv111").fadeOut();$('#confirmationDialog').dialog('close');$('#mybuttondialog').dialog('close');viewSearchdData(); }, 2000);
}

function closeButton(id)
{
	$('#'+id).dialog('close');
}

</script>
</head>
<body>
<sj:dialog
          id="confirmationDialog"
          showEffect="slide"
          targets="resultComes" 
          hideEffect="explode"
          autoOpen="false"
          modal="true"
          closeTopics="closeEffectDialog"
          closeOnEscape="true"
          onCompleteTopics="makeEffect"
          buttons="{ 'Yes':function() 
          {
           		saveButton(this.id);
          },
          'No':function()
           { 
           		closeButton(this.id); }
           }"
          >
          <div id="messageDiv111"></div>
           <div id="dataDiv1" hidden="true"></div>
	</sj:dialog>
	
<div class="clear"></div>
<div class="middle-content">
<div class="rightHeaderButton"></div>
<div class="clear"></div>
<div>
<div id="alphabeticalLinks"></div>
<!-- Code For Header Strip -->
<div class="listviewBorder" style="width: 97%;margin-left: 20px;" align="center">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr><td class="pL10">   
					     	
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
<s:hidden name="feedbackDataId" value="%{feedbackDataId}"></s:hidden>
<div style="overflow: scroll; height: 400px;">
<s:url id="viewPatientInfo" action="viewPatientInfo" namespace="/view/Over2Cloud/feedback/feedback_Activity">
<s:param name="clientId" value="%{clientId}"></s:param>

</s:url>
<sjg:grid 
		 
          id="sparecGrid"
          href="%{viewPatientInfo}"
          gridModel="masterViewList"
          navigator="false"
          navigatorAdd="false"
          navigatorView="false"
          navigatorSearch="true"
          navigatorDelete="false"
          editinline="false"
          rowList="15,50,100"
          rowNum="15"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          rownumbers="-1"
          pagerInput="true"   
          multiselect="false"
          navigatorSearchOptions="{sopt:['eq','cn']}"  
          loadingText="Requesting Data..."  
          navigatorEditOptions="{height:230,width:400}"
          shrinkToFit="true"
          autowidth="true"
          loadonce="true"
          
          >
        <sjg:gridColumn 
		name="id"
		index="id"
		title="Id"
		width="165"
		align="left"
		editable="false"
		hidden="true"
		/>
		
		 <sjg:gridColumn 
		name="action"
		index="action"
		title="Action"
		width="25"
		align="center"
		formatter="takeAction"
		/>
		<sjg:gridColumn 
		name="clientId"
		index="clientId"
		title="Client Id"
		width="140"
		align="center"
		editable="false"
		hidden="true"
		/>
		
		<sjg:gridColumn 
		name="patientId"
		index="patientId"
		title="Patient Id"
		width="80"
		align="center"
		editable="false"
		/>
		
		<sjg:gridColumn 
		name="station"
		index="station"
		title="Room No"
		width="60"
		align="center"
		editable="false"
		/>
		
		<sjg:gridColumn 
		name="patient_type"
		index="patient_type"
		title="Patient Type"
		width="40"
		align="center"
		editable="false"
		/>
		
		<sjg:gridColumn 
		name="admission_time"
		index="admission_time"
		title="Admission Date Time"
		width="100"
		align="center"
		editable="false"
		/>
		 
		<sjg:gridColumn 
		name="discharge_datetime"
		index="discharge_datetime"
		title="Discharge Date Time"
		width="100"
		align="center"
		editable="false"
		/>
		<sjg:gridColumn 
		name="serviceBy"
		index="serviceBy"
		title="Service By"
		width="100"
		align="center"
		editable="false"
		/>
		<sjg:gridColumn 
		name="companytype"
		index="companytype"
		title="Company Type"
		width="50"
		align="center"
		editable="false"
		/>
		
</sjg:grid>
</div>
</div>
</div>
</div>
</body>

</html>