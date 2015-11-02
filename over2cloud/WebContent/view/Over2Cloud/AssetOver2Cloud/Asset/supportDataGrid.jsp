<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
function supportAction()
{
	var valuepassed;
	valuepassed = $("#totalSupprt").jqGrid('getGridParam','selarrrow');
	if(valuepassed=="")
	{
	     alert("Please select atleast one check box...");        
	     return false;
	}
	$("#takeActionGrid").load("<%=request.getContextPath()%>/view/Over2Cloud/AssetOver2Cloud/Asset/beforeTakeAction?assetReminderID="+valuepassed);
	$("#takeActionGrid").dialog('open');
	
	   return false;
}
</script>
</head>
<body>
<div class="listviewBorder" style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr><td class="pL10">   
								<sj:a id="action" cssClass="button" button="true" title="Action" onclick="supportAction();">Action</sj:a>
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
<div style="overflow: scroll; height: 300px;">
<s:url id="viewTotalSupportURL" action="viewTotalSupportData" escapeAmp="false">
<s:param name="reminderId" value="%{reminderId}" />
<s:param name="dataFor" value="%{dataFor}" />
<s:param name="moduleName" value="%{moduleName}" />
</s:url>
<sjg:grid 
		  id="totalSupprt"
          href="%{viewTotalSupportURL}"
          gridModel="masterViewList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="false"
          rowList="10,15,20,25"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          loadingText="Requesting Data..."  
          shrinkToFit="false"
          multiselect="true"
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
</div>
<sj:dialog
          id="takeActionGrid"
          showEffect="slide" 
    	  hideEffect="explode" 
    	  openTopics="openEffectDialog"
    	  closeTopics="closeEffectDialog"
    	  onCloseTopics="closeDialog"
          autoOpen="false"
          title="Asset Action"
          modal="true"
          width="1000"
          height="350"
          draggable="true"
    	  resizable="true"
          >
</sj:dialog>
</body>
</html>