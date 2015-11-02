<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<div class="clear"></div>
<div class="clear"></div>

<s:url id="viewAbsent" action="viewAbsent">
<s:param name="id" value="%{id}" />
</s:url>
<div class="clear"></div>

<sjg:grid 
		  id="procumntGrid"
          href="%{viewAbsent}"
          gridModel="gridModel"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorSearch="false"
          navigatorEdit="false"
          navigatorDelete="false"
          rowList="10,15,20,25"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          loadingText="Requesting Data..."  
          navigatorEditOptions="{height:230,width:400}"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          navigatorEditOptions="{reloadAfterSubmit:true,closeAfterEdit:true}"
          shrinkToFit="false"
          onSelectRowTopics="rowselect"
          width="460"
          >
	
			<sjg:gridColumn 
			name="date"
			index="date"
			title="Date"
			width="150"
			align="center"
			editable="true"
			search="true"
			/>
			
			<sjg:gridColumn 
			name="day"
			index="day"
			title="Day"
			width="150"
			align="center"
			editable="true"
			search="true"
			/>
			
			<sjg:gridColumn 
			name="lupdate"
			index="lupdate"
			title="Via Update"
			width="150"
			align="center"
			editable="true"
			search="true"
			/>
	 
</sjg:grid>
</body>
<script type="text/javascript">
var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});	
function editRow()
{
	jQuery("#procumntGrid").jqGrid('editGridRow', row ,{height:400, width:425,reloadAfterSubmit:true,closeAfterEdit:true,top:0,left:350,modal:true});

}

function deleteRow()
{
	$("#gridedittable").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:true,top:0,left:350,modal:true});
}
</script>
</html>