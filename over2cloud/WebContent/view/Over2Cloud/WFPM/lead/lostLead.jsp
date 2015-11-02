<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<SCRIPT type="text/javascript">
var idLost;
function newLeadOkButton()
{
	alert(idLost);
	
	$.ajax({
	    type : "post",
		url : '<%=request.getContextPath()%>/view/Over2Cloud/WFPM/Lead/updateLostLead.action?idLost='+idLost
	 });
	$('#leadLostDialog').dialog('close');
}
function NewLeadCancelButton1() {
	$('#leadLostDialog').dialog('close');
};
function formatLink(cellvalue, options, row) {

	return "<a href='#' onClick='javascript:openDialog(" + row.id
			+ ")'>" + cellvalue + "</a>";
}

function openDialog(userId) {
	idLost=userId;
	$("#leadLostDialog").dialog("open");
}


</SCRIPT>
</head>
<body>
<sj:dialog
						modal="true"
						draggable="true"
						resizable="true"
						autoOpen="false" 
						width="500"
						showEffect="slide" 
                        hideEffect="explode"
						height="150"
						onCloseTopics="modclosed"
						onBeforeTopics="openingmodel"
						autoOpen="false"
						id="leadLostDialog"
						formIds="frm"
						title="Lead >> Lost Lead"
						buttons="{ 
							'Submit':function() { newLeadOkButton(); },
							'Cancel':function() { NewLeadCancelButton1(); } 
							}"
					>
					Click Submit button to convert this Lead to New Lead.
					</sj:dialog>

<div class="page_title"><h1><s:property value="%{mainHeaderName}"/> </h1></div>
<div class="container_block"><div style=" float:left; padding:20px 5%; width:90%;">
<center>
<div class="form_inner" id="form_reg">
<div class="page_form">

<!-- Level 1 to 5 Data VIEW URL CALLING Part Starts HERE -->
<s:url id="viewLostLead" action="viewLostLead" />
<!-- Level 1 to 5 Data VIEW URL CALLING Part ENDS HERE -->

<s:url id="deleteLeadGridOper" action="deleteLeadGridOper" />


<center>
<sjg:grid 
		  id="gridedittable"
          caption="%{mainHeaderName}"
          href="%{viewLostLead}"
          gridModel="masterViewList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="%{deleteFlag}"
          navigatorEdit="%{modifyFlag}"
          navigatorSearch="false"
          rowList="10,15,20,25"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          rowNum="5"
          navigatorEditOptions="{height:230,width:400}"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          editurl="%{deleteLeadGridOper}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          width="900"
          shrinkToFit="true"
          >
		<s:iterator value="masterViewProp" id="masterViewProp" >  
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
</center>
</div>
</div>
</center>
</div>
</div>
</body>
</html>