<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="<s:url value="/js/buddy/buddy.js"/>"></script>
</head>
<body>
<s:url id="viewBuddy" action="viewBuddyData">
<s:param name="id" value="%{id}" />
</s:url>
<s:url id="modifybuddy" action="modifybuddyData" />
<div class="list-icon">
<div class="clear"></div>
<div class="head"><s:property value="%{mainHeaderName}"/></div></div>
<div class="rightHeaderButton">
	<sj:a  button="true" onclick="createBuddy();"  buttonIcon="ui-icon-plus">Add Buddy</sj:a>
</div>
<div class="border" style="height: 420px; overflow: auto;">
<div class="listviewBorder">
	<div class="listviewButtonLayer" id="listviewButtonLayerDiv">
	<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
	<tbody><tr></tr><tr><td></td></tr><tr><td> 
	<table class="floatL" border="0" cellpadding="0" cellspacing="0" height="3px">
	<tbody><tr>
	<td class="pL10"  align="center"> 
	<sj:a id="editButton" buttonIcon="ui-icon-pencil" cssClass="button" button="true" onclick="editRow();">Edit</sj:a>	
	<sj:a id="delButton" buttonIcon="ui-icon-trash" cssClass="button" button="true"  onclick="deleteRow();">Delete</sj:a>
	</td></tr></tbody></table>
	</td>
	<td class="alignright printTitle">
	<sj:a  button="true" cssClass="button" buttonIcon="ui-icon-arrowreturnthick-1-n" onclick="importBuddy();">Import</sj:a>
	</td> 
	</tr></tbody></table></div></div></div>
<sjg:grid 
		  id="buddyGrid"
		  groupField="['floorcode']"
          groupColumnShow="[false]"
          groupCollapse="true"
          groupText="['<b>{0} - {1} Floor Codes</b>']"
          href="%{viewBuddy}"
          gridModel="masterViewList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorEdit="false"
          navigatorSearch="false"
          navigatorDelete="false"
          rowList="10,15,20,25"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          loadingText="Requesting Data..."  
          navigatorEditOptions="{height:230,width:400}"
          editurl="%{modifybuddy}"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          navigatorEditOptions="{reloadAfterSubmit:true,closeAfterEdit:true}"
          shrinkToFit="false"
          autowidth="true"
          onSelectRowTopics="rowselect"
          >
		<s:iterator value="masterViewProp" id="masterViewProp" status="test"> 
	
			<sjg:gridColumn 
			name="%{colomnName}"
			index="%{colomnName}"
			title="%{headingName}"
			width="%{width}"
			align="%{align}"
			editable="%{editable}"
			formatter="%{formatter}"
			search="%{search}"
			hidden="%{hideOrShow}"
			/>
			
		</s:iterator>  
</sjg:grid>
</div>	               		
</body>
<script type="text/javascript">
var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});	
function editRow()
{
	jQuery("#buddyGrid").jqGrid('editGridRow', row ,{height:400, width:425,reloadAfterSubmit:true,closeAfterEdit:true,top:0,left:350,modal:true});

}

function deleteRow()
{
	$("#buddyGrid").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:true,top:0,left:350,modal:true});
}
</script>
</html>