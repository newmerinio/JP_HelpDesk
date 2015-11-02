<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<% String userRights=session.getAttribute("userRights")== null ? "" : session.getAttribute("userRights").toString();%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/feedback/bedMapping.js"/>"></script>
<script type="text/javascript">
var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});	

$.subscribe('editRow', function(event,data)
{
  if(row==0)
	{
		alert("Please select atleast one row.");
	}
	else
	{
		var id   = jQuery("#gridBedMapping").jqGrid('getGridParam', 'selrow');
		var levelName = jQuery("#gridBedMapping").jqGrid('getCell',id,'emp_name');

		$.ajax({
			type :"post",
			url :"view/Over2Cloud/feedback/bedMapping/getMappedEmpList.action?id="+id,
			success : function(empData){
			//alert("Inside Success");
			var targetid='empName';
			$('#'+targetid+' option').remove();
	    	$(empData).each(function(index)
			{
	    		//alert("Id  "+this.id+"  Value"+this.name);
	    		
			   $('#'+targetid).append($("<option></option>").attr("value",this.id).text(this.name));
			});
		    }
		});
		jQuery("#gridBedMapping").jqGrid('editGridRow', row ,{height:200, width:300,reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:100,modal:true});
		}
});


function deleteRow()
{
	if(row==0)
	{
		alert("Please select atleast one row.");
	}
	else
	{
		$("#gridBedMapping").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:true});
	}
}
function reloadRow()
{
	var grid = $("#gridBedMapping");
	grid.trigger("reloadGrid",[{current:true}]);
}
</script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<!-- Header Strip Div Start -->
<div class="list-icon">
	 <div class="head">Configure Bed Mapping</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">View</div> 
</div>
<div class="clear"></div>
<div class="listviewBorder"  style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
<!-- Code For Header Strip -->
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <!-- Block for insert Left Hand Side Button -->
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr><td class="pL10">
					    <sj:a id="editButton111" title="Edit" buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px"  cssClass="button" button="true" onClickTopics="editRow"></sj:a>
					    <sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px"  cssClass="button" button="true"  onclick="deleteRow()"></sj:a>
						<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px"  cssClass="button" button="true"  onclick="reloadRow()"></sj:a>
					    </td></tr></tbody>
					  </table>
				  </td>
				  
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle"><!--
				      <sj:a id="downloadButton"  button="true"  buttonIcon="ui-icon-arrowstop-1-s" cssClass="button" cssStyle="height:25px; width:32px" title="Download" onclick="downloadFeedback('dataFor');" ></sj:a>
                      --><sj:a id="uploadButton"    button="true"  buttonIcon="ui-icon-arrowstop-1-n" cssClass="button" cssStyle="height:25px; width:32px" title="Upload"  onclick="uploadFeedback('dataFor');"></sj:a>
				      <%if(true){ %>
				          <sj:a id="addButton"  button="true"  cssClass="button" cssStyle="height:25px;" title="Add" buttonIcon="ui-icon-plus" onclick="addBedEntry();">Add</sj:a>
				      <%}%>
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<!-- Code End for Header Strip -->

<div style="overflow: scroll; height: 430px;">
<s:url id="bedMapping_URL" action="getBedMappingData"></s:url>
<div id="time_picker_div" style="display:none">
     <sj:datepicker id="res_Time_pick" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" />
</div>
<s:url id="modifyBedMapping_URL" action="modifyBedMapping"></s:url>
        <sjg:grid 
                    id="gridBedMapping"
                    gridModel="bedmappingData"
        			href="%{bedMapping_URL}"
       			    groupField="['contact_subtype_name']"
    	            groupColumnShow="[false]"
    	            groupCollapse="true"
    	            groupText="['<b>{0} - {1} Beds</b>']"
        			navigator="true"
        			navigatorAdd="false"
        			navigatorView="false"
        			navigatorDelete="false"
        			navigatorEdit="true"
        			navigatorSearch="true"
        			navigatorEditOptions="true"
        			pager="true"
        			pagerButtons="true"
        			pagerInput="false"   
        			altRows="true"
        			rowList="1500,2500,4000"
        			rowNum="1500"
        			rownumbers="-1"
        			viewrecords="true"       
        			autowidth="true"
        			multiselect="true"  
        			loadingText="Requesting Data..."
        			editinline="false"  
                    navigatorSearchOptions="{sopt:['cn','eq']}"
                    editurl="%{modifyBedMapping_URL}"
                    onSelectRowTopics="rowselect"
                    onEditInlineAfterSaveTopics="oneditsuccess"
                    shrinkToFit="false"
                  >
                  
			        <sjg:gridColumn 
								 name="contact_subtype_name"
								 index="contact_subtype_name"
								 title="Department"
								 width="400"
								 align="center"
								 editable="false"
			    				 search="false"
				                />
				    <sjg:gridColumn 
								 name="emp_name"
								 index="emp_name"
								 title="Employee"
								 width="650"
								 align="center"
								 editable="false"
			    				 search="false"
			    				 
				                />
				    <sjg:gridColumn 
								 name="bed_no"
								 index="bed_no"
								 title="Bed No"
								 width="645"
								 align="center"
								 editable="true"
			    				 search="false"
				                />
     </sjg:grid>
          	
</div>
</div>
</div>
</body>
</html>