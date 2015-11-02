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
$.subscribe('oneditsuccess', function(event, data){
	var message = event.originalEvent.response.statusText;
	$("#gridinfo").html('<p>Status: ' + message + '</p>');
});

$.subscribe('rowadd', function(event,data) {
    $("#gridedittable113454").jqGrid('editGridRow',"new",{height:280,reloadAfterSubmit:false});
	});
$.subscribe('searchgrid', function(event,data) {
    $("#gridedittable113454").jqGrid('searchGrid', {sopt:['cn','bw','eq','ne','lt','gt','ew']} );
	});
$.subscribe('showcolumns', function(event,data) {
    $("#gridedittable113454").jqGrid('setColumns',{});
	});

function addParticulars()
{
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
	var conP = "<%=request.getContextPath()%>";
	$.ajax({
	    type : "post",
	    url : conP+"/view/Over2Cloud/Reimbursement/beforeAddPartculars.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}
</SCRIPT>
</head>
<body>
<div class="list-icon"><div class="clear"></div><div class="head"><h4>Reimbursement Sheet >>Add</h4><s:property value="%{mainHeaderName}"/></div></div>
<div class="clear"></div>
<div class="rightHeaderButton">
 </div>
<br><div style=" float:left; padding:1px 1%; width:98%; height: 350px;">
<div class="listviewBorder">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
<tbody><tr></tr><tr><td></td></tr><tr><td> 
<table class="floatL" border="0" cellpadding="0" cellspacing="0">
<tbody><tr><td class="pL10"> 
</td></tr></tbody></table>
</td>
<td class="alignright printTitle">

<sj:a id="downloadExceProc"  button="true" buttonIcon="ui-icon-arrowreturnthick-1-s" cssClass="button" onclick="getCurrentColumn('downloadAllData','allModuleDetail','downloadColumnAllModDialog','downloadAllModColumnDiv')" >Download</sj:a>	
<sj:a  button="true" cssClass="button" buttonIcon="ui-icon-arrowreturnthick-1-n" onclick="importAsset();">Import</sj:a>
<sj:a  button="true" cssClass="button" buttonIcon="ui-icon-print" >Print</sj:a>
</td> 
</tr></tbody></table><div class="clear"></div>
</div>
</div>
<div>
<s:url id="viewReimbursmentInGrid_url" action="viewReimbursementInGrid" namespace="/view/Over2Cloud/Reimbursement"/>
<s:url id="editReimbursement_url" action="addDataEntryssssssss12345" namespace="/view/Over2Cloud/Reimbursement"/>
<sjg:grid 
        id="gridedittable113454"
    	dataType="json"
    	href="%{viewReimbursmentInGrid_url}"
    	editurl="%{editReimbursement_url}"
    	pager="true"
    	navigator="true"
    	navigatorSearchOptions="{sopt:['eq','ne','lt','gt']}"
    	navigatorAddOptions="{height:280,reloadAfterSubmit:true}"
    	navigatorEditOptions="{height:280,reloadAfterSubmit:false}"
    	navigatorEdit="false"
    	navigatorView="false"
    	navigatorDelete="true"
    	navigatorDeleteOptions="{height:280,reloadAfterSubmit:true}"
    	navigatorExtraButtons="{
    		seperator: { 
    			title : 'seperator'  
    		}, 
    		hide : { 
	    		title : 'Show/Hide', 
	    		icon: 'ui-icon-wrench', 
	    		topic: 'showcolumns'
    		},
    		add : { 
	    		title : 'Alert', 
	    		onclick: function(){ add('Grid Button clicked!') }
    		}
    	}"
    	gridModel="masterViewProp"
    	rowList="10,15,20"
    	editinline="true"
    	onSelectRowTopics="rowselect"
    	viewrecords="true"
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
		search="%{search}"
		hidden="%{hideOrShow}"
		/>
 </s:iterator>  
</sjg:grid>
</div></div>
<br>
<sj:submit id="grid_edit_addbutton" value="Add Row" onClickTopics="rowadd" button="true"/>
</div>
</body>
</html>