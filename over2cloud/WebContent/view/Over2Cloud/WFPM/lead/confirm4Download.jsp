<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
.sortable {
height:15px;
margin:5px;
padding:10px;
text-align: left;
}
</style>
<script type="text/javascript">
checked = false;
function checkedAll()
{
	if (checked == false)
	{
		checked = true;
	}
	else
	{
		checked = false;
	}
	for (var i = 0; i < document.getElementById('download').elements.length; i++)
	{
		document.getElementById('download').elements[i].checked = checked;
	}
}

function downloadExcel(){
	var imgs = $('input[type="checkbox"][name="columnNames"]:checked').map(function() { return this.value; }).get();
	//alert(imgs);
	//var colArray=imgs.split(",");
	if(imgs.length<=0){
		alert(" select atleast one checkboxe ");
	}else{
		$("#download").submit();
		/*   $("#sortable").animate({
	            height: 'toggle'
	        },'slow'); */
		$("#downloadColumnAllModDialog").dialog('close');
	}
}




</script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<sj:a 
             buttonIcon="ui-icon-check"
             onclick="downloadExcel();"
            	button="true"
             >Ok
    </sj:a>
    <s:form action="downloadAction" id="download" theme="css_xhtml">
	<s:hidden name="id" value="%{id}" />
	<s:hidden name="tableName" value="%{tableName}" />
	<s:hidden name="tableAllis" value="%{tableAllis}" />
	<s:hidden name="excelName" value="%{excelName}" />
	<s:hidden name="downloadType" value="%{downloadType}" />
	<s:hidden name="download4" value="%{download4}" />


	<s:hidden name="status" value="%{status}"></s:hidden>
	<s:hidden name="industry" value="%{industry}"></s:hidden>
	<s:hidden name="subIndustry" value="%{subIndustry}"></s:hidden>
	<s:hidden name="starRating" value="%{starRating}"></s:hidden>
	<s:hidden name="sourceName" value="%{sourceName}"></s:hidden>
	<s:hidden name="location" value="%{location}"></s:hidden>
	<s:hidden name="lead_name_wild" value="%{lead_name_wild}"></s:hidden>

	<s:hidden name="associate_Name" value="%{associate_Name}"></s:hidden>
	<s:hidden name="associateCategory" value="%{associateCategory}"></s:hidden>
	<s:hidden name="associateType" value="%{associateType}"></s:hidden>
	<s:hidden name="isExistingAssociate" value="%{isExistingAssociate}"></s:hidden>

	<s:hidden name="isExistingClient" value="%{isExistingClient}" />
	<s:hidden name="client_Name" value="%{client_Name}" />
	<s:hidden name="clientstatus" value="%{clientstatus}" />
	<s:hidden name="opportunity_name" value="%{opportunity_name}" />
	<s:hidden name="acManager" value="%{acManager}" />
	<s:hidden name="salesstages" value="%{salesstages}" />
	<s:hidden name="forecastCategary" value="%{forecastCategary}" />
	<s:hidden name="fromDateSearch" value="%{fromDateSearch}" />
	
	<sj:div id="sortable" sortable="true" sortableOpacity="0.1" sortablePlaceholder="ui-state-highlight" sortableForcePlaceholderSize="true" cssStyle="width: 200px;">
	 		<div id=""  class="sortable ui-widget-content ui-corner-all">
	 			<s:checkbox labelposition="right" name="checkall" label="Select All"  onclick='checkedAll();'/>
	 		</div>
	 		<s:iterator value="columnMap" status="rowstatus">
		 		<div id="#rowstatus.index"  class="sortable ui-widget-content ui-corner-all">
		 			<s:checkbox  labelposition="right" label="%{value}" name="columnNames" fieldValue="%{key}"/>
		 		</div>
			</s:iterator>
    </sj:div>

</s:form>
</body>
</html>