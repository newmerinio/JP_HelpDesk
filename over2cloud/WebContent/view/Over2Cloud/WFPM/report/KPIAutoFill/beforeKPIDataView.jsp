<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
function getParticularClientDetail(clientId)
{
	
	var valuepassed=$("#kpiId").val();
	var colName=$("#dateValue").val();
	var currentMonthValue = $("#currentMonthValue").val();
	$.ajax
	({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/report/DSR/beforeKPIDataView.action",
	    data : "currentMonthValue="+currentMonthValue+"&kpiId="+valuepassed+"&dateValue="+colName+"&clientId="+clientId,
	    success : function(data) 
	    {
 			$("#result").html(data);
		},
	    error: function() 
	    {
	        alert("error");
	    }
	 });
}
</script>
</head>
<body>
<div id="result">
	<div class="clear"></div>
	<div class="listviewBorder" style="margin-top: 0px;width: 100%;margin-left: 0px;" align="center">
	 		<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
				<div class="pwie"><table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr></tr><tr><td></td></tr><tr><td> <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10">
			         <s:select  
                             	id					=		"clientName"
                             	name				=		"clientName"
                             	list				=		"clientMap"
								cssClass			=		"button"
                            	theme				=		"simple"
                            	onchange			=		"getParticularClientDetail(this.value);"
                            	style="width:200px;"
                            	value="%{clientId}"
                             >
                    </s:select>
			<%-- <sj:datepicker cssStyle="height: 19px; width: 93px;" cssClass="button" id="toDate" name="toDate" size="20"   yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Date"/> --%>
					</td></tr></tbody></table>
				</td>
				<td class="alignright printTitle">
				</td>   
					</tr></tbody></table>
			  </div>
			</div>
	<div style="overflow: scroll; height: 430px;">
	<s:hidden id="kpiId"  value="%{kpiId}"  />
	<s:hidden id="dateValue"  value="%{dateValue}"  />
	<s:hidden id="currentMonthValue"  value="%{currentMonthValue}"  />
	
	<s:url id="kpiRecordView" action="kpiRecordViewData" escapeAmp="false">
		<s:param name="kpiId"  value="%{kpiId}" />
		<s:param name="currentMonthValue"  value="%{currentMonthValue}" />
		<s:param name="dateValue"  value="%{dateValue}" />
		<s:param name="clientId"  value="%{clientId}" />
	</s:url>
	<sjg:grid
			id="gridKPIDataView"
			dataType="json"
			href="%{kpiRecordView}" 
			groupField="['currentmonth']"
            groupColumnShow="[true]"
            groupCollapse="false"
            groupText="['<b>{0} - {1} Activity</b>']"
			pager="true" 
			navigator="true" 
			navigatorView="true" 
			navigatorAdd="false"
			navigatorDelete="false"
			navigatorEdit="false"
			navigatorSearch="false"
			editinline="false"
			gridModel="gridDataModelList"
			rowList="15,20,25,30" 
			rowNum="15" 
			multiselect="true"
			draggable="true" 
			loadingText="Requesting Data..." 
			shrinkToFit="false"
			autowidth="true"
			pagerButtons="true"
          	pagerInput="true"
			>

			<sjg:gridColumn
					name="id" index="id" title="ID" formatter="integer" editable="true" edittype="text" sortable="true" 
					search="false" hidden="true" />
    		<sjg:gridColumn 
					name="clientName" index="clientName" title="Client Name" frozen="true" editable="true" edittype="text" sortable="true" search="false" width="140"
					 align="center"/>
    		<sjg:gridColumn 
					name="contactName" index="contactName" frozen="true" title="Contact Person" editable="true" edittype="text" sortable="true" search="false" align="center" width="105"/>
    		
    		<sjg:gridColumn 
					name="mobileNo" index="mobileNo" frozen="true" title="Mobile No." editable="true" edittype="text" sortable="true" search="false" align="center" width="94"/>
    		
    		<sjg:gridColumn 
    				name="designation" index="designation" frozen="true" title="Designation" editable="true" edittype="text" sortable="true" align="center" search="false"  width="94"/>
    	   
    	    <sjg:gridColumn 
    				name="offering_map" frozen="offering_map"  index="Offering Map" title="Offering Mapped" align="center" width="120"
    				editable="true" edittype="text" sortable="true" search="false" />
    	   
    	    <sjg:gridColumn 
    				name="last_action"  index="last_action" frozen="true" title="Last Action Status" align="center" width="100"
    				editable="true" edittype="text" sortable="true" search="false" />
		<sjg:gridColumn 
			name="currentmonth"
			index="currentmonth"
			title="Activity date"
			align="center"
			editable="true"
			search="true"
			hidden="false"
			width="80"
		/>
		<sjg:gridColumn 
			name="current_status"
			index="current_status"
			title="Current Status "
			align="center"
			editable="true"
			search="true"
			hidden="false"
			width="130"
		/>
	</sjg:grid>
	</div>
</div>
</div>
</body>
<script type="text/javascript">
/* 	$(document).ready( function() {
		jQuery("#gridedittable").jqGrid('setGroupHeaders', {
			useColSpanStyle :false,
			groupHeaders : [
			                {
				startColumnName :'targetvalue',
				numberOfColumns :4,
				titleText :'Target Details '
			},{
				startColumnName :'one',
				numberOfColumns :7,
				titleText :'First - Week'
			}, {
				startColumnName :'eight',
				numberOfColumns :7,
				titleText :'<em>Second - Week</em>'
			}, {
				startColumnName :'fifteen',
				numberOfColumns :7,
				titleText :'<em>Third - Week</em>'
			}, {
				startColumnName :'twentytwo',
				numberOfColumns :10,
				titleText :'<em>Fourth - Week</em>'
			}, ]
		});
	}); */

</script>
</html>