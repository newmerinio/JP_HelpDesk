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

function addPurpose()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><center><img src=images/ajax-loaderNew.gif></center>");
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
<div class="list-icon"><div class="clear"></div><div class="head"><h4>Purpose >> View</h4><s:property value=""/></div></div>
<div class="clear"></div>
<div class="rightHeaderButton">
<sj:a cssClass="btn createNewBtn"  onclick="addPurpose()" button="true" buttonIcon="ui-icon-plus"> Create New Purpose </sj:a>
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
</tr></tbody></table></div></div></div>
<s:url id="viewParticulars" action="particularsViewInGrid"/>
<sjg:grid 
		  id="gridtable"
          href="%{viewParticulars}"
          gridModel="gridModel"
          navigator="true"
          dataType="json"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="false"
          rowList="10,15,20,25"
          viewrecords="true"       
          pager="true"
          loadingText="Requesting Data..."  
          rowNum="15"
          shrinkToFit="true"
          autowidth="true"
          loadonce="true"
          rownumbers="true"
          >
		
<s:iterator value="masterViewProp" id="masterView" >  
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
	</div>
	<div>
	<br>
	<br>
</div>
</body>
</html>